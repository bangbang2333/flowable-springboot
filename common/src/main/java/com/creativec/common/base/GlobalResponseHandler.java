package com.creativec.common.base;

import com.creativec.common.exception.BusinessException;
import com.creativec.common.tools.JsonHelper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.stream.Collectors;

/**
 * @author ZSX
 */
@Slf4j
@ControllerAdvice(annotations = {ResponseBody.class})
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        final String returnTypeName = returnType.getParameterType().getName();
        return !"com.creativec.common.base.GlobalResponse".equals(returnTypeName);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        final String returnTypeName = returnType.getParameterType().getName();
        if (body instanceof String) {
            return JsonHelper.toJson(GlobalResponse.success(body));
        } else if (body instanceof Boolean) {
            return GlobalResponse.successOrFail((Boolean) body);
        } else if ("void".equals(returnTypeName)) {
            return GlobalResponse.success(null);
        }
        return GlobalResponse.success(body);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BusinessException.class})
    public <T> GlobalResponse<T> handleException(BusinessException e) {
        log.error(e.getAlertMsg());
        if (e.getStatusCode() != null) {
            return GlobalResponse.exception(e.getAlertMsg(), e.getStatusCode());
        }
        return GlobalResponse.exception(e.getAlertMsg());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Throwable.class})
    public <T> GlobalResponse<T> handleThrowable(Throwable e) {
        log.error(Throwables.getStackTraceAsString(e));
        return GlobalResponse.exception(null);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public <T> GlobalResponse<T> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage());
        return GlobalResponse.fail(e.getParameterName() + "不能为空", 500);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class})
    public <T> GlobalResponse<T> handleBindException(BindException e) {
        log.error(e.getMessage());
        return GlobalResponse.fail(postBindingResult(e.getBindingResult()), 500);
    }

    private String postBindingResult(BindingResult result) {
        return result.getFieldErrors().stream().map(FieldError::getDefaultMessage).collect(Collectors.joining("; "));
    }

    /**
     * 方法参数校验
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GlobalResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage());
        return GlobalResponse.fail(postBindingResult(e.getBindingResult()), 500);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(DuplicateKeyException.class)
    public GlobalResponse handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return GlobalResponse.fail("数据重复，请检查后提交", 500);
    }

}

package com.creativec.common.base;

import com.creativec.common.exception.BusinessException;
import com.creativec.common.tools.JsonHelper;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

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
        log.error(Throwables.getStackTraceAsString(e));
        if (e.getStatusCode() != null) {
            return GlobalResponse.exception(e, e.getAlertMsg(), e.getStatusCode());
        }
        return GlobalResponse.exception(e, e.getAlertMsg());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Throwable.class})
    public <T> GlobalResponse<T> handleThrowable(Throwable e) {
        log.error(Throwables.getStackTraceAsString(e));
        return GlobalResponse.exception(e, null);
    }
}

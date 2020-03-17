package com.creativec.common.base;

import com.creativec.common.exception.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.annotation.Resource;

@Slf4j
@ControllerAdvice(annotations = {ResponseBody.class})
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Resource private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> aClass) {
        final String returnTypeName = returnType.getParameterType().getName();
        return !"com.google.common.collect.RegularImmutableMap".equals(returnTypeName)
                && !"com.creativec.common.base.GlobalResponse".equals(returnTypeName);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType
            , MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType
            , ServerHttpRequest request, ServerHttpResponse response) {
        final String returnTypeName = returnType.getParameterType().getName();
        if ("void".equals(returnTypeName)) {
            return GlobalResponse.success(null);
        } else if (body instanceof String) {
            try {
                return objectMapper.writeValueAsString(GlobalResponse.success(body));
            } catch (JsonProcessingException e) {
                log.error("String类型转换错误");
                e.printStackTrace();
            }
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

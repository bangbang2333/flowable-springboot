package com.creativec.config;


import cn.hutool.json.JSONUtil;
import com.creativec.base.AuthDataHolder;
import com.creativec.base.SysLog;
import com.creativec.entity.SysOperationLog;
import com.creativec.mapper.SysOperationLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;


/**
 * @author ZSX
 */
@Aspect
@Component
public class SysLogAspect {

    @Resource private SysOperationLogMapper sysOperationLogMapper;

    /**
     * 定义切点 @Pointcut
     * 在注解的位置切入代码
     */
    @Pointcut("@annotation(com.creativec.base.SysLog)")
    public void logPoinCut() {

    }


    @AfterReturning(pointcut = "logPoinCut()", returning = "value")
    public void saveSysLog(JoinPoint joinPoint, Object value) {
        //保存日志
        SysOperationLog sysLog = new SysOperationLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //将参数所在的数组转换成json
        sysLog.setParams(JSONUtil.toJsonStr(joinPoint.getArgs()));
        //获取操作
        SysLog myLog = method.getAnnotation(SysLog.class);
        if (myLog != null) {
            sysLog.setLogName(myLog.logname());
            sysLog.setLogType(myLog.logtype());
        }
        //获取请求的方法名
        sysLog.setMethod(method.getName());
        sysLog.setCreatedAt(LocalDateTime.now());
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        sysLog.setCreatedIp(request.getRemoteAddr());
        sysLog.setCreatedBy(AuthDataHolder.get().getId());
        //将参数所在的数组转换成json
        sysLog.setReturnValue(JSONUtil.toJsonStr(value));
        sysOperationLogMapper.insert(sysLog);
    }
}

package com.creativec.common.config;


import com.creativec.common.base.SysLog;
import com.creativec.common.base.SysOperationLog;
import com.creativec.common.tools.JsonHelper;
import com.creativec.common.mapper.SysOperationLogMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;


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
    @Pointcut("@annotation(com.creativec.common.base.SysLog)")
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
        sysLog.setParams(JsonHelper.toJson(joinPoint.getArgs()));
        //获取操作
        SysLog myLog = method.getAnnotation(SysLog.class);
        if (myLog != null) {
            sysLog.setLogname(myLog.logname());
            sysLog.setLogtype(myLog.logtype());
        }
        //获取请求的方法名
        sysLog.setMethod(method.getName());
        //将参数所在的数组转换成json
        sysLog.setReturnValue(JsonHelper.toJson(value));
        sysOperationLogMapper.insert(sysLog);
    }
}

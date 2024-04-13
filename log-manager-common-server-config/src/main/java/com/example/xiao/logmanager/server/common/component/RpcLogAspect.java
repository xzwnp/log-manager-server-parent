package com.example.xiao.logmanager.server.common.component;

import com.example.xiao.logmanager.server.common.entity.resp.R;
import com.example.xiao.logmanager.server.common.exception.BizException;
import com.example.xiao.logmanager.server.common.exception.RpcException;
import com.example.xiao.logmanager.server.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@EnableAspectJAutoProxy
@Component
@Aspect
@Slf4j
public class RpcLogAspect {
    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        Exception exception = null;
        // 获取目标方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获取目标方法
        String className = signature.getDeclaringType().getSimpleName();
        String methodName = signature.getName();
        try {
            // 获取方法参数名
            String[] parameterNames = signature.getParameterNames();
            // 获取方法参数值
            Object[] args = joinPoint.getArgs();

            // 获取方法参数名和参数值
            Map<String, Object> paramMap = new HashMap<>();
            for (int i = 0; i < parameterNames.length; i++) {
                paramMap.put(parameterNames[i], args[i]);
            }

            log.info("rpc call {}#{}, args:{}", className, methodName, paramMap);

            // 调用目标方法
            try {
                result = joinPoint.proceed();
            } catch (Exception e) {
                exception = e;
            }

        } catch (Exception e) {
            log.error("RpcLogAspect error", e);
        }
        if (result instanceof R r) {
            if (r.isSuccess()) {
                log.info("rpc call {}#{} success,resp:{}", className, methodName, JsonUtil.toJson(r));
            } else {
                String message = String.format("rpc call %s#%s fail,resp:%s", className, methodName, JsonUtil.toJson(r));
                log.error(message);
                throw new RpcException(message);
            }
        }

        //目标方法执行异常,抛出
        if (exception != null) {
            throw exception;
        }
        //正常返回目标方法执行结果
        return result;
    }
}

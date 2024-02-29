package com.example.xiao.logmanager.server.common.component;

import com.example.xiao.logmanager.server.common.util.UserThreadLocalUtil;
import com.example.xiao.logmanager.server.common.util.jwt.JwtEntity;
import com.example.xiao.logmanager.server.common.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(UserInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("UserInfoInterceptor.preHandle: 请求路径 [{}]", request.getRequestURI());
        String token = request.getHeader(JwtUtil.TOKEN_HEADER);
        JwtEntity userInfo = null;
        try {
            userInfo = JwtUtil.getUserInfo(token);
        } catch (Exception e) {
            //token为null
            return true;
        }
        UserThreadLocalUtil.setUserInfo(userInfo);
        return true; // 返回true表示继续执行后续处理器；返回false则不执行并直接返回响应
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 请求处理后，视图渲染之前执行，可以修改模型数据
        log.debug("UserInfoInterceptor.postHandle: 执行完毕，准备渲染视图");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 整个请求处理完毕（包括视图渲染）后执行，通常用于资源清理工作
        log.debug("UserInfoInterceptor.afterCompletion: 请求处理完成");
        UserThreadLocalUtil.removeUserInfo();
    }
}

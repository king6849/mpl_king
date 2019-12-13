package com.king.mpl.interceptors;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mai
 */
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {
    @Value("${header.origin}")
    private String origin;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //获得请求页面的Url
        String referer = request.getHeader("referer");
        if (referer != null && !referer.equals("")) {
            if (referer.startsWith(origin)) {
                response.setHeader("Access-control-Allow-Origin", origin);
            } else {
                response.setHeader("Access-control-Allow-origin", request.getHeader("Origin"));
            }
        } else {
            response.setHeader("Access-control-Allow-origin", request.getHeader("Origin"));
        }
        String authorization = request.getHeader("authorization");
        JWTVerifier jwtVerify = JWT.require(Algorithm.HMAC256("13124995836mxy")).build();
        if (authorization != null) {
            try{
                jwtVerify.verify(authorization);
            }catch (JWTDecodeException e){
                throw new JWTDecodeException("无效的authorization");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

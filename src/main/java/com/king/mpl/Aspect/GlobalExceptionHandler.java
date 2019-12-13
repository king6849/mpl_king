package com.king.mpl.Aspect;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.king.mpl.exception.CustomizeException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Map<String, Object> handleUnKnownException(RuntimeException e) {
        e.printStackTrace();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 500);
        res.put("msg", "系统繁忙");
        return res;
    }

    @ExceptionHandler(value = CustomizeException.class)
    public Map<String, Object> handleCustomizeException(CustomizeException e) {
        e.printStackTrace();
        Map<String, Object> res = new HashMap<>();
        res.put("code", e.getCode());
        res.put("msg", e.getMsg());
        return res;
    }

    @ExceptionHandler(value = JWTDecodeException.class)
    public Map<String,Object> handleJWTDecodeException(JWTDecodeException e){
        e.printStackTrace();
        Map<String, Object> res = new HashMap<>();
        res.put("msg", e.getMessage());
        return res;
    }
}

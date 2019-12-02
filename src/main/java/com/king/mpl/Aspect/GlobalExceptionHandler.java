package com.king.mpl.Aspect;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public Map<String, Object> handleUnKnownException(Exception e) {
        e.printStackTrace();
        Map<String, Object> res = new HashMap<>();
        res.put("code", 500);
        res.put("msg", "系统繁忙");
        return res;
    }
}

package com.king.mpl.Utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultVO<T> implements Serializable {


    private Integer code;

    private String msg;

    private String authorization;

    private T data;

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultVO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultVO(Integer code, String msg, String authorization) {
        this.code = code;
        this.msg = msg;
        this.authorization = authorization;
    }

    /**
     * 成功的ResultVO格式
     **/
    public static <T> ResultVO getSuccess(String msg) {
        return new ResultVO(100, msg);
    }

    /**
     * 成功的带Data的ResultVO格式
     **/
    public static <T> ResultVO getSuccessWithData(String msg, T data) {
        return new ResultVO(100, msg, data);
    }

    /**
     * 成功的带Data的ResultVO格式
     **/
    public static <T> ResultVO getSuccessWithAuthorization(String msg, String authorization) {
        return new ResultVO(100, msg, authorization);
    }

    /**
     * 失败的ResultVO格式
     **/
    public static <T> ResultVO getFailed(String msg) {
        return new ResultVO(101, msg);
    }

    /**
     * 用户未登陆的ResultVO格式
     */
    public static <T> ResultVO getNotAuth() {
        return new ResultVO(103, "用户未登陆");
    }

    /**
     * 自动义的ResultVO格式C
     */
    public static <T> ResultVO getcustomizeResultVO(Integer code, String msg) {
        return new ResultVO(code, msg);
    }


}

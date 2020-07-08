package com.creativec.base;


import com.google.common.base.Strings;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GlobalResponse<T> {


    protected boolean success = true;

    private T data;
    private int statusCode;
    private String alertMsg;


    public GlobalResponse() {

    }

    public GlobalResponse(T data) {
        this.statusCode = GolbalResponseCodeEnum.SUCCESS.getCode();
        this.data = data;
        this.alertMsg = "操作成功";
    }

    public GlobalResponse(T data, String alertMsg) {
        this.statusCode = GolbalResponseCodeEnum.SUCCESS.getCode();
        this.data = data;
        this.alertMsg = alertMsg;
    }

    public static <T> GlobalResponse<T> success(T data, String alertMsg) {
        return new GlobalResponse<>(data, alertMsg);
    }

    public static <T> GlobalResponse<T> success(T data) {
        return new GlobalResponse<>(data);
    }


    public static <T> GlobalResponse<T> successOrFail(boolean isSuccess, T data) {
        return isSuccess ? GlobalResponse.success(data) : GlobalResponse.fail();
    }

    public static <T> GlobalResponse<T> successOrFail(boolean isSuccess) {
        return isSuccess ? GlobalResponse.success(null) : GlobalResponse.fail();
    }

    public static <T> GlobalResponse<T> successOrFail(boolean isSuccess, T data, String alertMsg) {
        return isSuccess ? GlobalResponse.success(data) : GlobalResponse.fail(alertMsg, GolbalResponseCodeEnum.FAIL.getCode());
    }

    public static <T> GlobalResponse<T> fail() {
        GlobalResponse<T> resp = new GlobalResponse<>();
        resp.setStatusCode(GolbalResponseCodeEnum.FAIL.getCode());
        resp.setSuccess(false);
        resp.setAlertMsg(GolbalResponseCodeEnum.FAIL.getDesc());
        return resp;
    }

    public static <T> GlobalResponse<T> fail(String alertMsg) {
        GlobalResponse<T> resp = new GlobalResponse<>();
        resp.setStatusCode(GolbalResponseCodeEnum.FAIL.getCode());
        resp.setSuccess(false);
        resp.setAlertMsg(alertMsg);
        return resp;
    }

    public static <T> GlobalResponse<T> fail(String alertMsg, int code) {
        GlobalResponse<T> resp = new GlobalResponse<>();
        resp.setStatusCode(code);
        resp.setSuccess(false);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg) ? GolbalResponseCodeEnum.FAIL.getDesc() : alertMsg);
        return resp;
    }

    public static <T> GlobalResponse<T> exception(String alertMsg) {
        GlobalResponse<T> resp = new GlobalResponse<>();
        resp.setStatusCode(GolbalResponseCodeEnum.EXCEPTION.getCode());
        resp.setSuccess(false);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg) ? GolbalResponseCodeEnum.EXCEPTION.getDesc() : alertMsg);
        return resp;
    }

    public static <T> GlobalResponse<T> exception(String alertMsg, int statusCode) {
        GlobalResponse<T> resp = new GlobalResponse<>();
        resp.setStatusCode(statusCode);
        resp.setSuccess(false);
        resp.setAlertMsg(Strings.isNullOrEmpty(alertMsg) ? GolbalResponseCodeEnum.EXCEPTION.getDesc() : alertMsg);
        return resp;
    }

}

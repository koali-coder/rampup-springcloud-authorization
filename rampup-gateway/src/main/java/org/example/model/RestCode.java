package org.example.model;

/**
 * @author zhouyw
 */
public enum RestCode {

    // 成功
    SUCCESS("0", "SUCCESS"),
    // 失败
    FAILED("1", "系统错误"),
    // token过期
    TOKEN_FAILED("401", "token过期"),
    // 授权异常
    DENY_FAILED("401", "授权异常"),
    // 未知异常
    UNKNOWN_EXCEPTION("500", "未知异常");

    /**
     * 返回码
     */
    private String code;
    /**
     * 信息
     */
    private String message;

    RestCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

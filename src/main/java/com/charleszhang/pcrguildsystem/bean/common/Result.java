package com.charleszhang.pcrguildsystem.bean.common;

/**
 * @author Charles Zhang
 */
public class Result {

    /**
     * Return Status
     */
    private boolean flag;

    /**
     * Return Status Code
     */
    private int code;

    /**
     * Return Message
     */
    private String message;

    /**
     * Retrun Data
     */
    private Object data;

    public Result(boolean flag, int code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, int code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result() {
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

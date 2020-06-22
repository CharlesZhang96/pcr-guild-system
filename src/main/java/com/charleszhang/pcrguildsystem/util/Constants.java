package com.charleszhang.pcrguildsystem.util;

/**
 * @author Charles Zhang
 */
public class Constants {

    /**
     * Status Code
     * 20000 - Success
     * 20001 - Error
     * 20002 - Username or Password Failed
     * 20003 - Permission Denied
     * 20004 - Remote Request Failed
     * 20005 - Repetitive Operation
     */
    public static final Integer SUCCESS = 20000;
    public static final Integer ERROR = 20001;
    public static final Integer USER_PASS_ERROR = 20002;
    public static final Integer ACCESS_ERROR = 20003;
    public static final Integer REMOTE_ERROR = 20004;
    public static final Integer REPEAT_ERROR = 20005;
}

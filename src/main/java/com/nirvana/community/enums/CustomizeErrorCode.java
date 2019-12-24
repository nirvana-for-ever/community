package com.nirvana.community.enums;

/**
 * 错误码以及错误码对应信息的枚举
 */

public enum CustomizeErrorCode {

    NO_LOGIN(1001,"当前操作需要登录，请先进行登录"),
    QUESTION_NOT_FOUND (1002,"qwq~ 这个问题已经消失了"),
    COMMENT_NOT_FOUND(1003,"该回复已消失，请刷新重试"),

    /**
     * 其他错误
     */
    OTHER_ERROR(3000,"当前服务器发烧中~~，请稍后重试"),

    /**
     * 代码或内部程序错误
     */
    INNER_ERROR(2000,"当前服务器繁忙，请稍后重试"),


    /**
     * 一切正常的状态码
     */
    OK(1000,"OK");
    ;

    private Integer code;
    private String msg;

    CustomizeErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

package com.nirvana.community.exception;

import com.nirvana.community.enums.CustomizeErrorCode;

/**
 * @program: community
 * @description: 自己的异常
 * @author: Nirvana
 * @create: 2019--12--19--12:20
 **/

public class CustomizeException extends RuntimeException {

    private Integer code;
    private String message;

    public CustomizeException(CustomizeErrorCode customizeErrorCode) {
        this.code = customizeErrorCode.getCode();
        this.message = customizeErrorCode.getMsg();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}

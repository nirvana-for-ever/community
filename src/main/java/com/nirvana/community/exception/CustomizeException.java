package com.nirvana.community.exception;

/**
 * @program: community
 * @description:
 * @author: Nirvana
 * @create: 2019--12--19--12:20
 **/

public class CustomizeException extends RuntimeException {

    private String message;

    public CustomizeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

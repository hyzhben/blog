package com.blog.core.exception;

public class ClaimTokenException extends BaseException{
    public ClaimTokenException() {}

    public ClaimTokenException(String message) {
        super(message);
    }

    public ClaimTokenException(String code, String message) {
        super(code, message);
    }
}

package com.blog.core.exception;

public class UpdateFailedException extends BaseException{
    private static final long serialVersionUID = 1L;
    public UpdateFailedException(){}

    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException(String code, String message) {
        super(code, message);
    }
}

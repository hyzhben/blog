package com.blog.core.exception;

/**
 * Service层异常
 *
 * @version 1.0
 */
public class ServiceException extends BaseException {
    private static final long serialVersionUID = 1L
            ;

    public ServiceException() {}

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(code, message);
    }

}

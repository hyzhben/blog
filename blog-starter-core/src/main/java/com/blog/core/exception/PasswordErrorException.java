package com.blog.core.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 密码错误异常
 *
 */
public class PasswordErrorException extends BaseException {
    public PasswordErrorException() {}

    public PasswordErrorException(String message) {
        super(message);
    }

    public PasswordErrorException(String code, String message) {
        super(code, message);
    }

}

package com.blog.core.exception;

import org.springframework.security.authentication.InternalAuthenticationServiceException;

/**
 * 账户不存在异常
 *
 */
public class AccountNotExistsException extends InternalAuthenticationServiceException {

    public AccountNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistsException(String message) {
        super(message);
    }

}

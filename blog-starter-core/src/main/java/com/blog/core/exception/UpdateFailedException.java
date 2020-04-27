package com.blog.core.exception;

import com.blog.core.constants.BaseEnums;

public class UpdateFailedException extends BaseException{
    private static final long serialVersionUID = 1L;
    public UpdateFailedException() {
        super(BaseEnums.VERSION_NOT_MATCH.code());
    }

}

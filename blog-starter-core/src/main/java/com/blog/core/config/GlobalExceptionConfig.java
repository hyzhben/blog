package com.blog.core.config;

import com.blog.core.base.Result;
import com.blog.core.constants.BaseEnums;
import com.blog.core.exception.BaseException;
import com.blog.core.exception.ServiceException;
import com.blog.core.system.common.util.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionConfig {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionConfig.class);

    /**
     * 处理ServiceException 异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e){
        Result result = Results.failure(e.getCode(),e.getMessage());
        result.setStatus((HttpStatus.BAD_REQUEST.value()));
        logger.info("ServiceException[code:{},message:{}]",e.getCode(),e.getMessage());
        return result;
    }

    /**
     * 处理NoHandlerFoundException异常
     * 需配置[spring.mvc.throw-exception-if-no-handler-found=true]
     * 需配置[spring.resources.add-mappings=false]
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handlerNotFoundException(NoHandlerFoundException e){
        Result result = Results.failure(BaseEnums.NOT_FOUND.code(), BaseEnums.NOT_FOUND.desc());
        result.setStatus(HttpStatus.NOT_FOUND.value());
        logger.info(e.getMessage());
        return result;
    }

    /**
     * 处理BaseException异常
     */
    @ExceptionHandler(BaseException.class)
    public Result handleBaseException(BaseException e){
        Result result = Results.failure(e.getCode(), e.getMessage());
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("BaseException[code: {}, message: {}]", e.getCode(), e.getMessage(), e);
        return result;
    }

    /**
     * 处理Exception异常
     *
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        Result result = Results.failure(BaseEnums.ERROR.code(), BaseEnums.ERROR.desc());
        result.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error(e.getMessage(),e);
        return result;
    }
}

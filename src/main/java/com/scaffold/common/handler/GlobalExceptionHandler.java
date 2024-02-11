package com.scaffold.common.handler;

import com.scaffold.common.exceptions.BusinessException;
import com.scaffold.common.exceptions.SystemException;
import com.scaffold.model.web.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
@ResponseBody
/*
 * 全局异常处理
 * */
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ServerResponse businessException(BusinessException e) {
        log.error("业务处理异常：{}", e.getMessage());
        return ServerResponse.failure(e);
    }

    @ExceptionHandler(SystemException.class)
    public ServerResponse systemException(SystemException e) {
        log.error("系统异常：{}", e.getMessage());
        return ServerResponse.failure(e);
    }

    /**
     * 缺少请求参数异常
     *
     * @param ex HttpMessageNotReadableException
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ServerResponse handleHttpMessageNotReadableException(MissingServletRequestParameterException ex) {
        log.error("请求参数异常，{}", ex.getMessage());
        return ServerResponse.failure(BusinessException.validationException("参数校验异常"));
    }
}

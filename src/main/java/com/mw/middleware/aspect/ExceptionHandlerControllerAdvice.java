package com.mw.middleware.aspect;

import com.mw.middleware.bean.ResultResponse;
import com.mw.middleware.exception.BusinessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultResponse<Void> handleException(Exception e) {
        return ResultResponse.error(500, e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultResponse<Void> handleBusinessException(BusinessException e) {
        return ResultResponse.error(400, e.getMessage());
    }
}

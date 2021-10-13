package com.orange.share.exception;


import com.orange.share.response.ResponseWrapper;
import com.orange.share.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



@Slf4j
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {


    @ExceptionHandler(value=Exception.class)
    public ResponseWrapper allExceptionHandler(Exception exception) throws Exception
    {
        if (exception instanceof ParentRuntimeException) {
            log.error("异常摘要信息:"+ JsonUtil.objectToString(exception));
        }
        log.error("异常详细:",exception);
        return ResponseWrapper.markCustom(false,"500","失败",exception.getMessage());
    }

    @ExceptionHandler(value=ParentRuntimeException.class)
    public ResponseWrapper allParentExceptionHandler(ParentRuntimeException exception) throws Exception
    {
        log.error("订单异常摘要信息:"+exception.getMessage());
        return ResponseWrapper.markError(exception);
    }
}

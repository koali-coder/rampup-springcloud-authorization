package org.example.handle;

import org.example.exception.CustomException;
import org.example.model.RestData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

/**
 * @author zhouyw
 * 自定义全局异常拦截
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public RestData<String> handleNestedServletException(NestedServletException e){
        return RestData.failed(e.getCause().getCause().getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RestData<String> handleException(Exception e){
        return RestData.failed(e.getMessage());
    }

}

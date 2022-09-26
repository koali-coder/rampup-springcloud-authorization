package org.example.handle;

import org.example.model.RestData;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

/**
 * @author zxg
 * 自定义全局异常拦截
 */
@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(NestedServletException.class)
    public RestData<String> handleNestedServletException(NestedServletException e){
        return RestData.failed(e.getCause().getCause().getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RestData<String> handleException(Exception e){
        return RestData.failed(e.getMessage());
    }

}

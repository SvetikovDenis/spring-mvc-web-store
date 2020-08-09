package com.svetikov.ecommerceshop.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalDefaultExceptionHandler extends DefaultHandlerExceptionResolver {


    @ExceptionHandler(NoHandlerFoundException.class)
    public String handlerNoHandlerFoundException(Exception ex) {
        return "notFound";
    }

    @ExceptionHandler(Exception.class)
    public String handlerInternalFoundException(Exception ex) {
        return "internalError";
    }


}

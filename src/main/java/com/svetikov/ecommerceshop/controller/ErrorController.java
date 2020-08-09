package com.svetikov.ecommerceshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController {

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest httpRequest) {
        int httpErrorCode = getErrorCode(httpRequest);
        if (httpErrorCode == 404) {
            return "notFound";
        }
        return "internalError";
    }


    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status__code");
    }
}

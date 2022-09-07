package com.example.sellyourthing.controllers;

import com.example.sellyourthing.models.AccountDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/*
 * Class to handle displaying errors. This should soon replace the ExceptionHandlerClass
 * */
@Controller
public class CustomErrorController implements ErrorController {
    private final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        final String errorDir = "error-pages/";
        String errorPage = "error";

        Authentication auth = (Authentication) request.getUserPrincipal();
        String accessedBy = "Accessed by account id: " + ((AccountDetails) auth.getPrincipal()).accountId();

        String uriAccessed = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI) + "";

        String accessedByAndUrl = "URL: " + uriAccessed + " --> " + accessedBy;

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = (int) status;

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                logger.warn("Not Found: " + accessedByAndUrl);
                errorPage = "404-error";
            }
            else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                logger.warn("Forbidden: " + accessedByAndUrl);
                errorPage = "403-error";
            }
            else {
                logger.error("UNHANDLED ERROR: " + accessedByAndUrl);
            }
        }
        return errorDir + errorPage;
    }
}

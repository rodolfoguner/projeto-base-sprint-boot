package br.fai.models.client.controller;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionHandlerController {

    @ExceptionHandler({Exception.class})
    public ModelAndView excepetionHandler(Exception exception) {
        String errorMessage = null;

        ModelAndView model = new ModelAndView();
        model.setViewName("common/error");

        if (exception != null) {
            errorMessage = ExceptionUtils.getStackTrace(exception);
        }

        model.addObject("errorMessage", errorMessage);

        return model;
    }
}

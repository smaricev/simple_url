package com.marichely.simpleurl.web.controller;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice
@RequestMapping(produces = "application/json")
public class ExceptionHandleConsoler {

}

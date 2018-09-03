package com.marichely.simpleurl.web.controller;

import com.marichely.simpleurl.model.internal.InternalUrl;
import com.marichely.simpleurl.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Controller
public class UrlRedirectController {

    private UrlService urlService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UrlRedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{id}")
    public RedirectView redirectPostToPost(@PathVariable String id) throws IOException {
        InternalUrl originalUrl = null;
        try {
            originalUrl = urlService.getOriginalUrl(id);
        } catch (Exception e) {
            logger.error("error fetching url from service", e);
        }
        if (originalUrl != null) {
            RedirectView redirectView = new RedirectView(originalUrl.getFullUrl());
            redirectView.setStatusCode(HttpStatus.resolve(originalUrl.getRedirectNumber()));
            return redirectView;
        }
        else {
            throw new IOException();
        }
    }
}

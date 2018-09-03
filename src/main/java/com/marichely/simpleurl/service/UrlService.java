package com.marichely.simpleurl.service;

import com.marichely.simpleurl.model.internal.InternalUrl;

import java.util.Map;

public interface UrlService {
    String registerUrl(String url, Integer redirectNumber, Long userId) throws Exception;
    InternalUrl getOriginalUrl(String url) throws Exception;
    Map<String,Long> getStatistics(String username) throws Exception;
}

package com.marichely.simpleurl.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShortenUrlResponse {
    private String shortUrl;

    public ShortenUrlResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public static ShortenUrlResponse create(String url){
        ShortenUrlResponse shortenUrlResponse = new ShortenUrlResponse(url);
        shortenUrlResponse.setShortUrl(url);
        return shortenUrlResponse;
    }


}

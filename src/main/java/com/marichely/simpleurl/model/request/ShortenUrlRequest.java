package com.marichely.simpleurl.model.request;

import lombok.Data;

@Data
public class ShortenUrlRequest {
    private String url;
    private Integer redirectType;
}

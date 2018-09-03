package com.marichely.simpleurl.model.internal;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;
@Data
public class InternalUrl {

    @Column(name = "URL")
    private String url;
    @Column(name = "SHORT_URL")
    private String shortUrl;
    @Column(name = "REDIRECT_NUMBER")
    private int redirectNumber;
    @Column(name = "VISITS")
    private long visits;
    @Column(name = "ACCOUNT_ID")
    private long owner;

    public static InternalUrl createInteralUrl(String url, String shortUrl, Integer redirectNumber,Long visits,Long owner) {
        InternalUrl result = new InternalUrl();
        result.setShortUrl(shortUrl);
        result.setUrl(url);
        if(redirectNumber == null)result.setRedirectNumber(302);
        else result.setRedirectNumber(redirectNumber);
        if(visits == null)result.setVisits(0);
        else result.setVisits(visits);
        result.setOwner(owner);
        return result;
    }

    public String getFullUrl(){
        if(!StringUtils.contains("http://",url) || StringUtils.contains("https://",url)){
            return "http://" + url;
        }
        else {
            return url;
        }
    }
}

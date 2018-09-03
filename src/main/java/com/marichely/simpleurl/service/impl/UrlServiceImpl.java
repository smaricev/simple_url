package com.marichely.simpleurl.service.impl;

import com.google.common.base.Splitter;
import com.marichely.simpleurl.dao.AccountRepository;
import com.marichely.simpleurl.dao.UrlRepository;
import com.marichely.simpleurl.exception.InvalidResultSetSizeException;
import com.marichely.simpleurl.exception.InvalidUserSuppliedData;
import com.marichely.simpleurl.model.internal.InternalUrl;
import com.marichely.simpleurl.model.internal.InternalUser;
import com.marichely.simpleurl.service.UrlService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UrlServiceImpl implements UrlService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private UrlRepository urlRepository;
    private AccountRepository accountRepository;

    @Autowired
    public UrlServiceImpl(UrlRepository urlRepository, AccountRepository accountRepository) {
        this.urlRepository = urlRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public String registerUrl(String url, Integer redirectNumber, Long userId) throws Exception {
        validateUrl(url);
        InternalUrl internalUrl = null;
        try {
            internalUrl = urlRepository.getUrlByLongUrl(url);
        }catch (InvalidResultSetSizeException e){
            logger.info("No record found with url {}",url);
        }
        if(internalUrl != null) return internalUrl.getShortUrl();
        String shortenedUrl = createShortUrl(url, generateIdWithUUID(), redirectNumber, userId);
        if (StringUtils.isEmpty(shortenedUrl))
            shortenedUrl = createShortUrl(url, generateIdWithSha256(url), redirectNumber, userId);
        return shortenedUrl;
    }

    private void validateUrl(String url) throws Exception {
        UrlValidator urlValidator = new UrlValidator() {
            @Override
            public boolean isValid(String value) {
                return super.isValid(value) || super.isValid("http://" + value) || super.isValid("https://" + value);
            }
        };
        if (!urlValidator.isValid(url)) {
            throw new InvalidUserSuppliedData("Invalid url");
        }
    }


    private String createShortUrl(String url, Iterable<String> it, Integer redirectNumber, Long userid) throws Exception {
        for (String shortenedUrl : it) {
            try {
                urlRepository.insertNewUrl(InternalUrl.createInteralUrl(url, shortenedUrl, redirectNumber, null, userid));
                return shortenedUrl;
            } catch (Exception e) {
                logger.info("failed inserting short url {}", shortenedUrl, e);
            }
        }
        return null;
    }

    @Override
    public InternalUrl getOriginalUrl(String url) throws Exception {
        InternalUrl internalUrl = urlRepository.getUrlByShortUrl(url);
        urlRepository.updateVisits(internalUrl.getVisits() + 1, internalUrl.getShortUrl());
        return internalUrl;
    }

    @Override
    public Map<String, Long> getStatistics(String username) throws Exception {
        InternalUser internalUser = accountRepository.getUserByUsername(username);
        List<InternalUrl> urlsByOwner = urlRepository.getUrlsByOwner(internalUser.getId());
        return urlsByOwner.stream()
                          .collect(Collectors.toMap(InternalUrl::getUrl, InternalUrl::getVisits));

    }



    private Iterable<String> generateIdWithUUID() {
        UUID uuid = UUID.randomUUID();
        String uuidWithoutDash = StringUtils.remove(uuid.toString(), "-");
        return Splitter.fixedLength(6)
                       .split(uuidWithoutDash);
    }

    private Iterable<String> generateIdWithSha256(String id) {
        String resultElement = DigestUtils.sha256Hex(getID(id));
        return Splitter.fixedLength(8)
                       .split(resultElement);
    }

    private String getID(String id) {
        Date date = new Date();
        int i = date.hashCode();
        int i1 = id.hashCode();
        return Integer.toString(i + i1);
    }
}

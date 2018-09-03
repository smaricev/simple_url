package com.marichely.simpleurl.web.controller;

import com.marichely.simpleurl.configuration.security.UserDetailsImpl;
import com.marichely.simpleurl.model.request.AccountRegistrationRequest;
import com.marichely.simpleurl.model.request.ShortenUrlRequest;
import com.marichely.simpleurl.model.response.AccountRegistrationResponse;
import com.marichely.simpleurl.model.response.ShortenUrlResponse;
import com.marichely.simpleurl.service.AccountService;
import com.marichely.simpleurl.service.UrlService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@Controller
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UrlConfigurationController {

    private AccountService accountService;
    private UrlService urlService;

    @Autowired
    public UrlConfigurationController(AccountService accountService, UrlService urlService) {
        this.accountService = accountService;
        this.urlService = urlService;
    }


    @ResponseBody
    @PostMapping(value = "/account")
    public AccountRegistrationResponse createAccount(@RequestBody AccountRegistrationRequest accountRegistrationRequest){
        String password;
        try {
            password = accountService.createAccount(accountRegistrationRequest.getAccountId());
        }catch (DuplicateKeyException e){
            return AccountRegistrationResponse.createFail("The id you tried to register already exists try using a different one");
        }
        catch (Exception e){
            return AccountRegistrationResponse.createFail(e.getMessage());
        }
        if (StringUtils.isEmpty(password)){
            return AccountRegistrationResponse.createFail("empty password generated");
        }
        return AccountRegistrationResponse.createOk(password);
    }

    @ResponseBody
    @PostMapping(value = "/register")
    public ResponseEntity<ShortenUrlResponse> registerUrl(@RequestBody ShortenUrlRequest request, Principal principal){
        try {
            Long userID = getUserID((UsernamePasswordAuthenticationToken) principal);
            String shortenedUrl = urlService.registerUrl(request.getUrl(), request.getRedirectType(),userID);
            return ResponseEntity.ok(ShortenUrlResponse.create(shortenedUrl));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ShortenUrlResponse.create(null));
        }
    }

    private Long getUserID(UsernamePasswordAuthenticationToken principal) {
        UserDetailsImpl userDetails =(UserDetailsImpl) principal.getPrincipal();
        return userDetails.getid();
    }

    @ResponseBody
    @GetMapping(value = "/statistic/{accountId}")
    public Map<String,Long> getStatisticsForAccount(@PathVariable String accountId,Principal principal) throws Exception {
        return urlService.getStatistics(accountId);
    }
}

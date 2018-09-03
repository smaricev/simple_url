package com.marichely.simpleurl.service.impl;

import com.marichely.simpleurl.dao.AccountRepository;
import com.marichely.simpleurl.exception.InvalidUserSuppliedData;
import com.marichely.simpleurl.model.internal.InternalUser;
import com.marichely.simpleurl.service.AccountService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private PasswordEncoder encoder;
    private AccountRepository accountRepository;


    @Autowired
    public PasswordEncoder getEncoder() {
        return encoder;
    }

    @Autowired
    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    @Override
    public String createAccount(String accountId) throws Exception {
        if(StringUtils.isEmpty(accountId)) {
            throw new InvalidUserSuppliedData("Can't create account with empty string");
        }
        String password = RandomStringUtils.randomAlphanumeric(8);
        accountRepository.insertAccount(accountId, encoder.encode(password));
        return password;
    }


}

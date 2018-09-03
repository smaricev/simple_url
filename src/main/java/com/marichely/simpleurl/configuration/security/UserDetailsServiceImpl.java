package com.marichely.simpleurl.configuration.security;

import com.marichely.simpleurl.dao.AccountRepository;
import com.marichely.simpleurl.model.internal.InternalUser;
import com.marichely.simpleurl.model.internal.InternalUserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("real")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        InternalUser internalUser = null;
        try {
            internalUser = accountRepository.getUserByUsername(username);
        } catch (Exception e) {
            logger.error("Error fetching password from the database", e);
            throw new UsernameNotFoundException(e.getMessage());
        }
        if (internalUser == null) {
            throw new UsernameNotFoundException("No such user (" + username + ")");
        } else {
            internalUser.setGrantedAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            return new UserDetailsImpl(internalUser);
        }
    }




}

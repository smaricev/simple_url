package com.marichely.simpleurl.model.internal;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class InternalUserBuilder {
    private long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public InternalUserBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public InternalUserBuilder setUsername(String username) {
        this.username = username;
        return this;
    }

    public InternalUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public InternalUserBuilder setGrantedAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
        return this;
    }

    public InternalUser createInternalUser() {
        return new InternalUser(id, username, password, grantedAuthorities);
    }
}
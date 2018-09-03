package com.marichely.simpleurl.model.internal;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import java.util.Collection;

@Data
public class InternalUser {
    @Column(name = "ID")
    private long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "PASSWORD")
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public InternalUser() {
    }

    public InternalUser(long id, String username, String password, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;
    }
}

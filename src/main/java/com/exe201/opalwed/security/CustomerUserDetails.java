package com.exe201.opalwed.security;

import com.exe201.opalwed.model.Account;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomerUserDetails implements UserDetails {

    private Account opalAccount;

    public CustomerUserDetails(Account opalAccount) {
        this.opalAccount = opalAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + opalAccount.getAccountRole().name()));
    }

    @Override
    public String getPassword() {
        return opalAccount.getPassword();
    }

    @Override
    public String getUsername() {
        return opalAccount.getEmail();
    }
}

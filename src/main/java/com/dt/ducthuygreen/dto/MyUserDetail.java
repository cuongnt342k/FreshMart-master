package com.dt.ducthuygreen.dto;

import com.dt.ducthuygreen.entities.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MyUserDetail extends org.springframework.security.core.userdetails.User {
    private User user;

    private String fullName;

    public MyUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities, User user) {
        super(username, password, authorities);
        this.user = user;
    }

    public MyUserDetail(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

//    public String getFullName(){
//        return user.getFullName();
//    }


    public String getFullName() {
        if (user != null)
            return user.getFullName();
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}

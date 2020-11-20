package com.bakery.shark.bakery_shark.app.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CurrentUser extends User {
    private final com.bakery.shark.bakery_shark.app.model.User user;
    public CurrentUser(String username, String password,
                       Collection<? extends GrantedAuthority> authorities,
                       com.bakery.shark.bakery_shark.app.model.User user) {
        super(username, password,authorities);
        this.user = user;
    }
    public com.bakery.shark.bakery_shark.app.model.User getUser() {return user;}

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }
}

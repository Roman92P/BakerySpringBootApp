package com.bakery.shark.bakery_shark.app.user;

import com.bakery.shark.bakery_shark.app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

//    chanche saveUser on boolean for email
    User findByUserName(String name);
    boolean saveUser(User user);

    boolean activateUser(String code);
}

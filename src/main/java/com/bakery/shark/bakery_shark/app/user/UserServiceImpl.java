package com.bakery.shark.bakery_shark.app.user;


import com.bakery.shark.bakery_shark.app.email.MailSender;
import com.bakery.shark.bakery_shark.app.model.Role;
import com.bakery.shark.bakery_shark.app.model.User;
import com.bakery.shark.bakery_shark.app.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    //for email
    @Autowired
    private MailSender mailSender;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User findByUserName(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public boolean saveUser(User user) {

        //for email
        User byUsername = userRepository.findByUsername(user.getUsername());
        if(byUsername !=null){
            return false;
        }
        //unique identificator
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActive(false);
        //for email end
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<Role>(Collections.singletonList(userRole)));
        userRepository.save(user);

        //for email
        if(!StringUtils.isEmpty(user.getEmail())){
            String message = String.format("Hello %s! Welcome to Bakery Manager. Please activate your account" +
                    " throught this link: http://localhost:8080/activate/%s", user.getFirstName(), user.getActivationCode());
            mailSender.send(user.getEmail(), "Activation code", message);
        }
        return true;
    }

    @Override
    public boolean activateUser(String code) {
        User user =  userRepository.findByActivationCode(code);
        if(user==null){
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        user.setEnabled(true);
        userRepository.save(user);

        return true;
    }

}

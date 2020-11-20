package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.model.Role;
import com.bakery.shark.bakery_shark.app.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;


public class RoleConverter implements Converter<String, Role> {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role convert(String s) {
        return roleRepository.findByName(s);
    }
}

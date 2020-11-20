package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.manufacture.JpaManufacturedService;
import com.bakery.shark.bakery_shark.app.model.Manufactured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityNotFoundException;

public class ManufacturedConverter implements Converter<String, Manufactured> {

    @Autowired
    private JpaManufacturedService jpaManufacturedService;

    @Override
    public Manufactured convert(String source) {
        return jpaManufacturedService.getManufactured(Long.parseLong(source)).orElseThrow(EntityNotFoundException::new);
    }
}

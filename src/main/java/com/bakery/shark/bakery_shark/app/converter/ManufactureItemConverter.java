package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.manufacture.JpaManufactureItemService;
import com.bakery.shark.bakery_shark.app.model.ManufactureItem;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityNotFoundException;

public class ManufactureItemConverter implements Converter<String, ManufactureItem> {

    private JpaManufactureItemService jpaManufactureItemService;

    @Override
    public ManufactureItem convert(String source) {
        return jpaManufactureItemService.getManufactureItem(Long.parseLong(source)).orElseThrow(EntityNotFoundException::new);
    }
}

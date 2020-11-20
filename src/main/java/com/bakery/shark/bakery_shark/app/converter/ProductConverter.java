package com.bakery.shark.bakery_shark.app.converter;


import com.bakery.shark.bakery_shark.app.model.Product;
import com.bakery.shark.bakery_shark.app.product.JpaProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.EntityNotFoundException;


public class ProductConverter implements Converter<String, Product> {

    @Autowired
    private JpaProductService jpaProductService;

    @Override
    public Product convert(String source) {
        return jpaProductService.getProduct(Long.parseLong(source)).orElseThrow(EntityNotFoundException::new);
    }
}

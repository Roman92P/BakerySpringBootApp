package com.bakery.shark.bakery_shark.app.product;

import com.bakery.shark.bakery_shark.app.model.Product;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private JpaProductService productService;

    @Autowired
    private Gson gson;

    @GetMapping(value = "/allImages/{id}",produces ="text/plain;charset=UTF-8")
    public String giveAllProductImages(@PathVariable Long id){
        Product product = productService.getProduct(id).orElseThrow(NoClassDefFoundError::new);
            byte[] img = product.getPhoto();
            String image = "";
            if (img != null && img.length > 0) {
                image = Base64.getEncoder().encodeToString(img);
            }
        return gson.toJson(image);
    }
}

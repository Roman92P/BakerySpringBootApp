package com.bakery.shark.bakery_shark.app.product;



import com.bakery.shark.bakery_shark.app.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product>getAllProducts();

    Optional<Product> getProduct(Long id);

    void deleteProduct(Product product);

    void updateProduct(Product product);

    void addProduct(Product product);

}

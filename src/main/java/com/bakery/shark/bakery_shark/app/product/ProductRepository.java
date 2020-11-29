package com.bakery.shark.bakery_shark.app.product;

import com.bakery.shark.bakery_shark.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByNameEquals(String productName);
}

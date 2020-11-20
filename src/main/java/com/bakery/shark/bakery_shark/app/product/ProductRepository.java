package com.bakery.shark.bakery_shark.app.product;

import com.bakery.shark.bakery_shark.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long> {
}

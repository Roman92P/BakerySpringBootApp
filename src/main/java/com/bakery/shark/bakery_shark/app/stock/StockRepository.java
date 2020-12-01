package com.bakery.shark.bakery_shark.app.stock;

import com.bakery.shark.bakery_shark.app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, Long> {


    Stock findFirstByProductName(String productName);

    @Query(value = "SELECT SUM(product_quantity) FROM stock", nativeQuery = true)
    Integer getSumOfStockProductQuantity();
}

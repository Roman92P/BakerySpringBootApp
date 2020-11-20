package com.bakery.shark.bakery_shark.app.stock;

import com.bakery.shark.bakery_shark.app.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {


    Stock findFirstByProductName(String productName);
}

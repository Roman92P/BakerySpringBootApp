package com.bakery.shark.bakery_shark.app.lostProducts;

import com.bakery.shark.bakery_shark.app.model.LostProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LostProductRepository extends JpaRepository<LostProducts, Long> {

    @Query(value = "SELECT DATE(created_on),value FROM lost_products WHERE DATE (created_on) BETWEEN ? AND ?", nativeQuery = true)
    List<Object[]> getLostsBetweenDates(String startDate, String endDate);
}

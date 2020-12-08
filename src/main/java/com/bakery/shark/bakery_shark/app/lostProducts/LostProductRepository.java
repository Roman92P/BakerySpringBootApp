package com.bakery.shark.bakery_shark.app.lostProducts;

import com.bakery.shark.bakery_shark.app.model.LostProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LostProductRepository extends JpaRepository<LostProducts, Long> {
//"SELECT date( created_on), real_sum_of_order from bill WHERE created_on >= ? AND created_on<= ? "
    @Query(value = "SELECT DATE(created_on),value FROM lost_products WHERE created_on >= ? AND created_on<= ?", nativeQuery = true)
    List<Object[]> getLostsBetweenDates(LocalDate startDate, LocalDate endDate);
}

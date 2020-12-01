package com.bakery.shark.bakery_shark.app.produced;

import com.bakery.shark.bakery_shark.app.model.Produced;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProducedRepository extends JpaRepository<Produced, Long> {

    @Query(value = "SELECT SUM(product_stock_quantity) FROM produced where CURRENT_DATE", nativeQuery = true)
    Integer getSumToday();
}

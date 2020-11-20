package com.bakery.shark.bakery_shark.app.workStockProductQuantity;

import com.bakery.shark.bakery_shark.app.model.WorkStockQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkStockProductQuantityRepository extends JpaRepository<WorkStockQuantity, Long> {

    WorkStockQuantity findByWorkStockProductName(String workBillProductName);
}

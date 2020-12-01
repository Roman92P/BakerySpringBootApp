package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Bill getBillByCreatedOnAndUserId(LocalDateTime created, Long userId);

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill where CURRENT_DATE", nativeQuery = true)
    Double getSumOfTodaySold();

}

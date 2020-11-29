package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Bill getBillByCreatedOnAndUserId(LocalDateTime created, Long userId);

}

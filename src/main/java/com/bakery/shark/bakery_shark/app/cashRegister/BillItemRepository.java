package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    List<BillItem> findAllByBillNull();

    BillItem findByBillNullAndAndSoldProductName(String soldProductName);
}

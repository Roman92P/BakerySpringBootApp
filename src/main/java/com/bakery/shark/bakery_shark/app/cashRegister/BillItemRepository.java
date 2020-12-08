package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    List<BillItem> findAllByBillNull();

    BillItem findByBillNullAndAndSoldProductName(String soldProductName);

    List<BillItem> findAllByBill_Id(Long billId);

    @Query(value = "Select sold_product_name, sold_product_quantity FROM bill_item INNER JOIN bill b on b.id = bill_item.bill_id" +
            " WHERE to_char(b.created_on, 'YYYY') = ? AND to_char(b.created_on, 'MM') = ?;", nativeQuery = true)
    List<Object[]>getSoldProductsBetweenDates(String year, String month);
}

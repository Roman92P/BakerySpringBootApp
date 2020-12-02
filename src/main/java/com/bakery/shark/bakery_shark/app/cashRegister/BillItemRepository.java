package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.BillItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BillItemRepository extends JpaRepository<BillItem, Long> {

    List<BillItem> findAllByBillNull();

    BillItem findByBillNullAndAndSoldProductName(String soldProductName);

    List<BillItem> findAllByBill_Id(Long billId);

    @Query(value = "SELECT sold_product_name, sold_product_quantity From bill_item " +
            "INNER JOIN bill on bill_item.bill_id = bill.id " +
            "where YEAR(created_on) =? AND MONTH(created_on) = ?", nativeQuery = true)
    List<Object[]>getSoldProductsBetweenDates(String year, String month);
}

package com.bakery.shark.bakery_shark.app.cashRegister;



import com.bakery.shark.bakery_shark.app.model.Bill;

import java.util.List;
import java.util.Optional;

public interface BillService {
    List<Bill> getAllBills();

    Optional<Bill> getBill(Long id);

    void deleteBill(Bill bill);

    void updateBill(Bill bill);

    void addBill(Bill bill);
}

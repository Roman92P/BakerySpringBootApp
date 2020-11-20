package com.bakery.shark.bakery_shark.app.cashRegister;



import com.bakery.shark.bakery_shark.app.model.BillItem;

import java.util.List;
import java.util.Optional;

public interface BillItemService {
    List<BillItem> getAllBillItems();

    Optional<BillItem> getBillItem(Long id);

    void deleteBillItem(BillItem billItem);

    void updateBillItem(BillItem billItem);

    void addBillItem(BillItem billItem);
}

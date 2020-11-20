package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.Bill;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
@AllArgsConstructor
public class JpaBillService implements BillService {

    @Autowired
    private final BillRepository billRepository;

    @Override
    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    @Override
    public Optional<Bill> getBill(Long id) {
        return billRepository.findById(id);
    }

    @Override
    public void deleteBill(Bill bill) {
        billRepository.delete(bill);
    }

    @Override
    public void updateBill(Bill bill) {
        billRepository.save(bill);
    }

    @Override
    public void addBill(Bill bill) {
        billRepository.save(bill);
    }
}

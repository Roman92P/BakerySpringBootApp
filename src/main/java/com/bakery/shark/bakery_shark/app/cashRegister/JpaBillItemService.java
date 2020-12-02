package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.BillItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class JpaBillItemService implements BillItemService {


    private final BillItemRepository billItemRepository;

    @Override
    public List<BillItem> getAllBillItems() {
        return billItemRepository.findAll();
    }

    @Override
    public Optional<BillItem> getBillItem(Long id) {
        return billItemRepository.findById(id);
    }

    @Override
    public void deleteBillItem(BillItem billItem) {
        billItemRepository.delete(billItem);
    }

    @Override
    public void updateBillItem(BillItem billItem) {
        billItemRepository.save(billItem);
    }

    @Override
    public void addBillItem(BillItem billItem) {
        if(billItemRepository.findByBillNullAndAndSoldProductName(billItem.getSoldProductName())!=null){
            BillItem billItemToUpdate = billItemRepository.findByBillNullAndAndSoldProductName(billItem.getSoldProductName());
            billItemToUpdate.setSoldProductQuantity(billItemToUpdate.getSoldProductQuantity()+billItem.getSoldProductQuantity());
            updateBillItem(billItemToUpdate);
        }else
        billItemRepository.save(billItem);
    }

    public List<BillItem> getAllItemsWithNullBill(){
       return billItemRepository.findAllByBillNull();
    }

    public List<BillItem> getAllByBillId(Long billId){return  billItemRepository.findAllByBill_Id(billId);}

    public List<Object[]> getSoldNamesAndQuantitiesForPeriod(String year, String month){
       return billItemRepository.getSoldProductsBetweenDates(year, month);
    }
}

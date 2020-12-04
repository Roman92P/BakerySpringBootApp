package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.Bill;
import com.bakery.shark.bakery_shark.app.model.BillItem;
import com.bakery.shark.bakery_shark.app.product.ProductRepository;
import com.bakery.shark.bakery_shark.app.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class JpaBillService implements BillService {

    private final UserService userService;
    private final ProductRepository productRepository;
    private final BillItemRepository billItemRepository;
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

    public Bill getBillByCreatedTimeAndUserId(LocalDateTime created, Long userId){return billRepository.getBillByCreatedOnAndUserId(created,userId);}

    public void createTextBill(Long id, Long userId) {
        Bill billById = billRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        List<BillItem> allByBill_id = billItemRepository.findAllByBill_Id(id);
        double sum = allByBill_id.stream().mapToDouble(billItem -> billItem.getProduct().getPrice() * billItem.getSoldProductQuantity()).sum();
        double roundedSum = BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).doubleValue();
        billById.setRealSumOfOrder(roundedSum);
        billById.setUser(userService.findByUserId(userId).orElseThrow(EntityNotFoundException::new));
        billRepository.save(billById);
    }

    public Double getSumOfTodayOrders() {
        return billRepository.getSumOfTodaySold();
    }

    public List<Object[]> getBillsForCurrentMonth(){
        return billRepository.getBillsFromCurrentMonth();
    }

    public List<Object[]> getBillsForPreviousMonth(){
        return billRepository.getBillsFromPreviousMonth();
    }

    public List<Object[]> getBillsForCertainPeriod(String startDate, String endDate){
       return billRepository.getBillsFromCustomPeriod(startDate,endDate);
    }

    public double getTotalSalesSumOfCurrentMonth(){
        return billRepository.sumOfCurrentMonth();
    }

    public double getYesterdaySalesSum(){
        return billRepository.sumOfYesterdaySales();
    }

    public double getSumOfThisMonthYearBefore(){
        return billRepository.sumOfThisMonthYearBefore();
    }
}

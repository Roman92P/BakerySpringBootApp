package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Bill getBillByCreatedOnAndUserId(LocalDateTime created, Long userId);

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill where DATE (created_on) = CURDATE()", nativeQuery = true)
    Double getSumOfTodaySold();

    @Query(value = "SELECT DATE (created_on), real_sum_of_order FROM bill WHERE MONTH(bill.created_on) = MONTH(CURRENT_DATE)", nativeQuery = true)
    List<Object[]> getBillsFromCurrentMonth();

    @Query(value = "SELECT DATE (created_on), real_sum_of_order From bill  WHERE YEAR(created_on) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)\n" +
            "AND MONTH(created_on) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)", nativeQuery = true)
    List<Object[]> getBillsFromPreviousMonth();

    @Query(value = "SELECT DATE (created_on), real_sum_of_order from bill WHERE DATE (created_on) BETWEEN ? AND ?", nativeQuery = true)
    List<Object[]>getBillsFromCustomPeriod( String  startTime,  String endTime);

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill where MONTH(created_on)= MONTH(CURRENT_DATE)",nativeQuery = true)
    Double sumOfCurrentMonth();

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill WHERE DATE (created_on)= SUBDATE(current_date,1)", nativeQuery = true)
    Double sumOfYesterdaySales();

    @Query(value="SELECT SUM(real_sum_of_order) FROM bill WHERE MONTH(created_on)=MONTH(CURRENT_DATE) AND " +
            "YEAR(created_on) = YEAR(DATE_SUB(CURDATE(), INTERVAL 1 YEAR))",nativeQuery = true)
    Double sumOfThisMonthYearBefore();
}

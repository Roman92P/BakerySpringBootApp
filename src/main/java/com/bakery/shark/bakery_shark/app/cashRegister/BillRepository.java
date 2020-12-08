package com.bakery.shark.bakery_shark.app.cashRegister;

import com.bakery.shark.bakery_shark.app.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {

    Bill getBillByCreatedOnAndUserId(LocalDateTime created, Long userId);

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill where DATE (created_on) = CURRENT_DATE", nativeQuery = true)
    Double getSumOfTodaySold();

//    @Query(value = "SELECT DATE (created_on), real_sum_of_order FROM bill WHERE MONTH(bill.created_on) = MONTH(CURRENT_DATE)", nativeQuery = true)
    @Query(value = "SELECT DATE (created_on), real_sum_of_order FROM bill WHERE extract(Month from created_on) = extract(Month from CURRENT_DATE)", nativeQuery = true)
    List<Object[]> getBillsFromCurrentMonth();

    @Query(value = "SELECT DATE (created_on), real_sum_of_order From bill  WHERE extract(YEAR from created_on) = extract(Year from now()) AND extract(MONTH from created_on) = extract(MONTH from now() - Interval '1 MONTH')", nativeQuery = true)
    List<Object[]> getBillsFromPreviousMonth();

    @Query(value = "SELECT date( created_on), real_sum_of_order from bill WHERE created_on >= ? AND created_on<= ? ", nativeQuery = true)
    List<Object[]>getBillsFromCustomPeriod(LocalDate startTime,LocalDate endTime);

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill where EXTRACT(MONTH from created_on)= EXTRACT(MONTH from (CURRENT_DATE))",nativeQuery = true)
    Double sumOfCurrentMonth();

    @Query(value = "SELECT SUM(real_sum_of_order) FROM bill WHERE date (created_on)= date( CURRENT_DATE - 1);", nativeQuery = true)
    Double sumOfYesterdaySales();

    @Query(value= "Select sum(real_sum_of_order) FROM bill WHERE extract(Month from created_on) = extract(Month from Now())" +
            " and extract(year from created_on)=extract(year from now() - interval'1 year')",nativeQuery = true)
    Double sumOfThisMonthYearBefore();
}

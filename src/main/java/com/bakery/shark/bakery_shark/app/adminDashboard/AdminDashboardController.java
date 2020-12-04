package com.bakery.shark.bakery_shark.app.adminDashboard;

import com.bakery.shark.bakery_shark.app.cashRegister.JpaBillService;
import com.bakery.shark.bakery_shark.app.ingredient.JpaIngredientService;
import com.bakery.shark.bakery_shark.app.model.User;
import com.bakery.shark.bakery_shark.app.produced.JpaProducedService;
import com.bakery.shark.bakery_shark.app.stock.JpaStockService;
import com.bakery.shark.bakery_shark.app.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/bakery/dashboard")
public class AdminDashboardController {

    private final JpaProducedService jpaProducedService;
    private final JpaBillService jpaBillService;
    private final JpaStockService jpaStockService;
    private final JpaIngredientService jpaIngredientService;
    private final UserService userService;


    @RequestMapping
    public String getAdminDashboard() {

        return "dashboardView";
    }

    @ModelAttribute("producedTodayQuantity")
    public Integer getTodayProducedQuantity() {
        Integer producedQuantityToday = jpaProducedService.getProducedQuantityToday();
        return Objects.requireNonNullElse(producedQuantityToday, 0);
    }

    @ModelAttribute("soldToday")
    public Double getTodaySoldSum(){
        Double sumOfTodayOrders = jpaBillService.getSumOfTodayOrders();
        if(sumOfTodayOrders==null){
            return 0.0;
        }
        return sumOfTodayOrders;
    }

    @ModelAttribute("productStockQuantity")
    public Integer getProductStockQuantity(){
        Integer allStockProductQuantity = jpaStockService.getAllStockProductQuantity();
        if(allStockProductQuantity==null){
            return 0;
        }
        return allStockProductQuantity;
    }

    @ModelAttribute("allZeroIngredients")
    public Integer getNumberOfEmptyIngredients(){
        Integer countOfEmptyIngredients = jpaIngredientService.getCountOfEmptyIngredients();
        if(countOfEmptyIngredients==null){
            return 0;
        }
        return countOfEmptyIngredients;
    }

    @ModelAttribute("allActiveUsers")
    public List<User> allActUsers(){
        List<User> allUsers = userService.getAllUsers();
        return allUsers.stream().filter(user -> !user.getFirstName().equals("Admin")).collect(Collectors.toList());
    }

    @ModelAttribute("soldsInCurrentMonth")
    public List<Object[]> getSoldsInCurrentMonth(){
        return jpaBillService.getBillsForCurrentMonth();
    }

    @ModelAttribute("currentMonthTotal")
    public Double getCurrentMonthTotal() {
        double totalSalesSumOfCurrentMonth;
        try {
            totalSalesSumOfCurrentMonth = jpaBillService.getTotalSalesSumOfCurrentMonth();
        } catch (NullPointerException e) {
            return 0.0;
        }
        return totalSalesSumOfCurrentMonth;
    }

    @ModelAttribute("yesterdaySalesSum")
    public Double getSumOfYesterdayBills() {
        double yesterdaySalesSum;
        try {
            yesterdaySalesSum = jpaBillService.getYesterdaySalesSum();
        } catch (NullPointerException e) {
            return 0.0;
        }
        return yesterdaySalesSum;
    }

    @ModelAttribute("monthSalesYearBefore")
    public Double getSumFromThisMonthYB() {
        double sumOfThisMonthYearBefore;
        try {
            sumOfThisMonthYearBefore = jpaBillService.getSumOfThisMonthYearBefore();
        } catch (NullPointerException e) {
            return 0.0;
        }
        return sumOfThisMonthYearBefore;
    }

    @ModelAttribute("totalPlusBacklog")
    public Double gettotalAndBacklog() {
        double totalSalesSumOfCurrentMonth;

        Double sumOfAllStockProducts= jpaStockService.getSumOfAllStockProducts();
        if(sumOfAllStockProducts==null){
           sumOfAllStockProducts=0.0;
        }
        try {
            totalSalesSumOfCurrentMonth = jpaBillService.getTotalSalesSumOfCurrentMonth();
        } catch (NullPointerException e) {
            totalSalesSumOfCurrentMonth=0.0;
        }
        return totalSalesSumOfCurrentMonth + sumOfAllStockProducts;
    }
}

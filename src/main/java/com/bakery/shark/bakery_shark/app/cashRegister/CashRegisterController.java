package com.bakery.shark.bakery_shark.app.cashRegister;


import com.bakery.shark.bakery_shark.app.model.Bill;
import com.bakery.shark.bakery_shark.app.model.BillItem;
import com.bakery.shark.bakery_shark.app.model.Stock;
import com.bakery.shark.bakery_shark.app.model.WorkStockQuantity;
import com.bakery.shark.bakery_shark.app.stock.JpaStockService;
import com.bakery.shark.bakery_shark.app.workStockProductQuantity.JpaWorkStockProductQuantityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cashRegister")
public class cashRegisterController {

    @Autowired
    Validator validator;

    @Autowired
    private final JpaStockService jpaStockService;
    private final JpaBillItemService jpaBillItemService;
    private final JpaBillService jpaBillService;
    private final JpaWorkStockProductQuantityService jpaWorkStockProductQuantityService;

    @RequestMapping
    public String showCashRegister(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession();
        if(session.getAttribute("workStockQuantity")==null){
            List<Stock> allStock = jpaStockService.getAllStock();
            for(Stock s:allStock){
                WorkStockQuantity workStockQuantity = new WorkStockQuantity();
                workStockQuantity.setWorkStockProductName(s.getProductName());
                workStockQuantity.setWorkStockProductQuantity(s.getProductQuantity());
                jpaWorkStockProductQuantityService.addWorkStockQuantity(workStockQuantity);
            }
//            List<WorkStockQuantity> allWorkStock = jpaWorkStockProductQuantityService.getAllWorkStock();
            session.setAttribute("workStockQuantity", "workStock");
        }

        List<BillItem> allItemsWithNullBill = jpaBillItemService.getAllItemsWithNullBill();
        model.addAttribute("allItemsWithNullBill", allItemsWithNullBill);
        return "CashRegisterView";
    }

    @ModelAttribute("stockToSold")
    public List<Stock> allStockProducts() {
        List<Stock> allStock = jpaStockService.getAllStock();
        for(Stock s : allStock){
            if(s.getProductQuantity()==0){
                jpaStockService.deleteStock(s);
            }
        }
        return jpaStockService.getAllStock();
    }

    @RequestMapping("/createSoldItem/{id}")
    public String createSoldItem(@PathVariable long id, Model model) {
        Stock stock = jpaStockService.getStockProduct(id).orElseThrow(EntityNotFoundException::new);
        BillItem billItem = new BillItem();
        billItem.setSoldProductName(stock.getProductName());
        billItem.setSoldProductPrice(stock.getProductPrice());
        model.addAttribute("billItem", billItem);
        return "addQuantityCashView";
    }

    @PostMapping("/addBillItem")
    public String addBillItem(@Valid BillItem billItem, BindingResult result, Model model, HttpServletRequest request) {

        if(result.hasErrors()){
            return "addQuantityCashView";
        }

        String soldProductName = billItem.getSoldProductName();
        int soldProductQuantity = billItem.getSoldProductQuantity();

        Stock stockProductByProductName = jpaStockService.getStockProductByProductName(billItem.getSoldProductName());

        HttpSession session = request.getSession();
        if(session.getAttribute("workStockQuantity")!=null){
            List<WorkStockQuantity> allWorkStock = jpaWorkStockProductQuantityService.getAllWorkStock();
            for ( WorkStockQuantity wsq : allWorkStock ){
                if(soldProductName.equals(wsq.getWorkStockProductName())){
                    if(billItem.getSoldProductQuantity()> wsq.getWorkStockProductQuantity()){
                        model.addAttribute("alertProductBillName", billItem.getSoldProductName());
                        model.addAttribute("alertProductBillQuantity", billItem.getSoldProductQuantity());
                        model.addAttribute("alertProductId", stockProductByProductName.getId());
                        return "addQuantityCashView";
                    }
                    WorkStockQuantity workStockQuantityByProductName = jpaWorkStockProductQuantityService.getWorkStockQuantityByProductName(soldProductName);
                    workStockQuantityByProductName.setWorkStockProductQuantity(workStockQuantityByProductName.getWorkStockProductQuantity()-billItem.getSoldProductQuantity());
                    jpaWorkStockProductQuantityService.updateWorkStockQuantity(workStockQuantityByProductName);
                }
            }
        }
        jpaBillItemService.addBillItem(billItem);
        return "redirect:/cashRegister";
    }

    @RequestMapping("/finishOrder")
    public String finalizeCustomerOrder(HttpServletRequest request){
        List<BillItem> allItemsWithNullBill = jpaBillItemService.getAllItemsWithNullBill();
        Bill bill = new Bill();
        jpaBillService.addBill(bill);
        double sum = 0;
        for ( BillItem billItem : allItemsWithNullBill ){
//            sum +=billItem.getSoldProductPrice();
            billItem.setBill(bill);
            jpaBillItemService.updateBillItem(billItem);
            Stock stockProductByProductName = jpaStockService.getStockProductByProductName(billItem.getSoldProductName());
            stockProductByProductName.setProductQuantity(stockProductByProductName.getProductQuantity()-billItem.getSoldProductQuantity());
            jpaStockService.updateStock(stockProductByProductName);
        }
//        bill.setSumOfCustomerOrder(sum);
        double sum1 = allItemsWithNullBill.stream().mapToDouble(BillItem::getSoldProductPrice).sum();
        bill.setSumOfCustomerOrder(sum1);
        jpaBillService.updateBill(bill);


        HttpSession session = request.getSession();
        if(session.getAttribute("workStockQuantity")!=null){
            List<WorkStockQuantity> allWorkStock = jpaWorkStockProductQuantityService.getAllWorkStock();
            for ( WorkStockQuantity w:allWorkStock ){
                jpaWorkStockProductQuantityService.deleteWorkStockProductQuantity(w);
            }
            session.removeAttribute("workStockQuantity");
        }

        return "redirect:/cashRegister";
    }
}

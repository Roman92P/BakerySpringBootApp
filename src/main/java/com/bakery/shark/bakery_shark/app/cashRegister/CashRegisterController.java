package com.bakery.shark.bakery_shark.app.cashRegister;


import com.bakery.shark.bakery_shark.app.model.*;
import com.bakery.shark.bakery_shark.app.product.JpaProductService;
import com.bakery.shark.bakery_shark.app.stock.JpaStockService;
import com.bakery.shark.bakery_shark.app.user.UserService;
import com.bakery.shark.bakery_shark.app.workStockProductQuantity.JpaWorkStockProductQuantityService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/cashRegister")
@SessionAttributes("orderSession")
public class CashRegisterController {

    private final Logger logger = LoggerFactory.getLogger(CashRegisterController.class);

    @Autowired
    Validator validator;

    private final JpaStockService jpaStockService;
    private final JpaBillItemService jpaBillItemService;
    private final JpaBillService jpaBillService;
    private final JpaWorkStockProductQuantityService jpaWorkStockProductQuantityService;
    private final JpaProductService jpaProductService;
    private final UserService userService;

    //Reset Orde
    @RequestMapping("/resetOrder")
    public String deleteWholeOrder() {
        List<BillItem> allItemsWithNullBill = jpaBillItemService.getAllItemsWithNullBill();
        allItemsWithNullBill.stream().forEach(jpaBillItemService::deleteBillItem);
        return "redirect:/cashRegister";
    }

    //Delete WorkItem
    @RequestMapping("/deleteWorkItem/{id}")
    public String deleteWorkItem(@PathVariable long id) {
        BillItem billItem = jpaBillItemService.getBillItem(id).orElseThrow(EntityNotFoundException::new);
        String name = billItem.getProduct().getName();
        List<WorkStockQuantity> allWorkStock = jpaWorkStockProductQuantityService.getAllWorkStock();
        for(WorkStockQuantity w: allWorkStock){
            if(name.equals(w.getWorkStockProductName())){
                w.setWorkStockProductQuantity(w.getWorkStockProductQuantity()+billItem.getSoldProductQuantity());
                jpaWorkStockProductQuantityService.updateWorkStockQuantity(w);
            }
        }
        jpaBillItemService.deleteBillItem(billItem);
        return "redirect:/cashRegister";
    }

//    @PostMapping("/editWorkItem")
//    public String editWorkItem(@Valid BillItem billItem, BindingResult bindingResult, RedirectAttributes  redirectAttributes){
//        if(bindingResult.hasErrors()){
//            redirectAttributes.addFlashAttribute("editBillItemError","Minimum quantity is 1");
//            return "redirect:/cashRegister";
//        }
//        jpaBillItemService.updateBillItem(billItem);
//        return "redirect:/cashRegister";
//    }

//    @ModelAttribute("billItemToUpdate")
//    public BillItem giveBillItemToUpdate(){
//        return new BillItem();
//    }

    // First Cashregister View
    @RequestMapping
    public String showCashRegister(Model model, HttpServletRequest request, HttpSession session,SessionStatus status) {
        List<BillItem> allItemsWithNullBill = jpaBillItemService.getAllItemsWithNullBill();
        if(allItemsWithNullBill.size()==0){
            logger.error("Session status in 1 view: "+ status.isComplete());
            status.setComplete();
        }
        model.addAttribute("allItemsWithNullBill", allItemsWithNullBill);
        return "CashRegisterView";
    }

    @ModelAttribute("stockToSold")
    public List<Stock> allStockProducts() {
        List<Stock> allStock = jpaStockService.getAllStock();
        for ( Stock s : allStock ) {
            if (s.getProductQuantity() == 0) {
                jpaStockService.deleteStock(s);
            }
        }
        return jpaStockService.getAllStock();
    }

    //Add product quantity
    @RequestMapping("/createSoldItem/{id}/{name}")
    public String createSoldItem(@PathVariable long id, @PathVariable String name, Model model) {
        Stock stock = jpaStockService.getStockProduct(id).orElseThrow(EntityNotFoundException::new);
        BillItem billItem = new BillItem();
        billItem.setSoldProductName(stock.getProductName());
        billItem.setSoldProductPrice(stock.getProductPrice());
        model.addAttribute("billItem", billItem);
        return "addQuantityCashView";
    }

    @PostMapping("/addBillItem")
    public String addBillItem(@Valid BillItem billItem, BindingResult result, Model model, HttpServletRequest request, SessionStatus status) {
        if (result.hasErrors()) {
            return "addQuantityCashView";
        }

        if(model.getAttribute("orderSession")==null){
            jpaWorkStockProductQuantityService.getAllWorkStock().stream().forEach(jpaWorkStockProductQuantityService::deleteWorkStockProductQuantity);
            List<Stock> allStock = jpaStockService.getAllStock();
            for ( Stock s : allStock ) {
                WorkStockQuantity workStockQuantity = new WorkStockQuantity();
                workStockQuantity.setWorkStockProductName(s.getProductName());
                workStockQuantity.setWorkStockProductQuantity(s.getProductQuantity());
                jpaWorkStockProductQuantityService.addWorkStockQuantity(workStockQuantity);
                model.addAttribute("orderSession","yes");
            }
        }

        String soldProductName = billItem.getSoldProductName();
        int soldProductQuantity = billItem.getSoldProductQuantity();
        Product product = jpaProductService.getProductByProductName(soldProductName).orElseThrow(EntityNotFoundException::new);


        Stock stockProductByProductName = jpaStockService.getStockProductByProductName(billItem.getSoldProductName());

        List<WorkStockQuantity> allWorkStock = jpaWorkStockProductQuantityService.getAllWorkStock();
        for ( WorkStockQuantity wsq : allWorkStock ) {
            if (soldProductName.equals(wsq.getWorkStockProductName())) {
                if (billItem.getSoldProductQuantity() > wsq.getWorkStockProductQuantity()) {
                    model.addAttribute("alertProductBillName", billItem.getSoldProductName());
                    model.addAttribute("alertProductBillQuantity", billItem.getSoldProductQuantity());
                    model.addAttribute("alertProductId", stockProductByProductName.getId());
                    return "addQuantityCashView";
                }
                WorkStockQuantity workStockQuantityByProductName = jpaWorkStockProductQuantityService.getWorkStockQuantityByProductName(soldProductName);
                workStockQuantityByProductName.setWorkStockProductQuantity(workStockQuantityByProductName.getWorkStockProductQuantity() - billItem.getSoldProductQuantity());
                jpaWorkStockProductQuantityService.updateWorkStockQuantity(workStockQuantityByProductName);
            }
        }
        billItem.setProduct(product);
        jpaBillItemService.addBillItem(billItem);
        return "redirect:/cashRegister";
    }

    // Finish order
    @RequestMapping("/finishOrder")
    public String finalizeCustomerOrder(HttpSession session, Principal principal, SessionStatus status) {
        List<BillItem> allItemsWithNullBill = jpaBillItemService.getAllItemsWithNullBill();
        Bill bill = new Bill();
        jpaBillService.addBill(bill);
        for ( BillItem billItem : allItemsWithNullBill ) {
            billItem.setBill(bill);
            Stock stockProductByProductName = jpaStockService.getStockProductByProductName(billItem.getSoldProductName());
            stockProductByProductName.setProductQuantity(stockProductByProductName.getProductQuantity() - billItem.getSoldProductQuantity());
            jpaStockService.updateStock(stockProductByProductName);
        }
        double sum1 = allItemsWithNullBill.stream().mapToDouble(item -> item.getSoldProductPrice() * (item.getSoldProductQuantity())).sum();
        double roundedSum = BigDecimal.valueOf(sum1).setScale(2, RoundingMode.HALF_UP).doubleValue();
        logger.error("Sum of basket: " + sum1);
        bill.setSumOfCustomerOrder(roundedSum);
        jpaBillService.updateBill(bill);

        List<WorkStockQuantity> allWorkStock = jpaWorkStockProductQuantityService.getAllWorkStock();
        for ( WorkStockQuantity w : allWorkStock ) {
            jpaWorkStockProductQuantityService.deleteWorkStockProductQuantity(w);
        }

        status.setComplete();

        logger.error("bill id after set bill to bileItems " + bill.getId());

        jpaBillService.createTextBill(bill.getId(), userService.findByUserName(principal.getName()).getId());

        return "redirect:/cashRegister/showBill/" + bill.getId();
    }

    //Show new bill
    @RequestMapping("/showBill/{id}")
    public String showOrderBill(@PathVariable Long id, Model model, Principal principal) {
        model.addAttribute("billItems", jpaBillItemService.getAllByBillId(id));
        model.addAttribute("thisBill", jpaBillService.getBill(id).orElseThrow(EntityNotFoundException::new));
        model.addAttribute("currentDateTime", LocalDateTime.now());
        model.addAttribute("user", principal.getName());
        BillImg billImg = new BillImg();
        model.addAttribute("billImg", billImg);
        return "billView";
    }

}

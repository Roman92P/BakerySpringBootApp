package com.bakery.shark.bakery_shark.app.stock;

import com.bakery.shark.bakery_shark.app.model.Stock;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

    @Autowired
    private JpaStockService jpaStockService;

    @RequestMapping
    public String showWarehouse(Model model){
        return"stockView";
    }

    @ModelAttribute("stock")
    public List<Stock> getAllStock(){
        return jpaStockService.getAllStock();
    }
}

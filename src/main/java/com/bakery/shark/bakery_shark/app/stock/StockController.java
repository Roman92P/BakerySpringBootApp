package com.bakery.shark.bakery_shark.app.stock;

import com.bakery.shark.bakery_shark.app.lostProducts.LostProductService;
import com.bakery.shark.bakery_shark.app.model.Stock;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/stock")
@AllArgsConstructor
public class StockController {

    private final Logger logger = LoggerFactory.getLogger(StockController.class);

    private final JpaStockService jpaStockService;
    private final LostProductService lostProductService;

    @RequestMapping
    public String showWarehouse(Model model) {
        return "stockView";
    }

    @ModelAttribute("stock")
    public List<Stock> getAllStock() {
        return jpaStockService.getAllStock().stream().filter(stock -> stock.getProductQuantity()!=0).collect(Collectors.toList());
    }


    @RequestMapping("/removeAll/{id}")
    public String removeStockItem(@PathVariable long id) {
        Stock stock = jpaStockService.getStockProduct(id).orElseThrow(EntityNotFoundException::new);
        lostProductService.addStockToLost(stock,stock.getProductQuantity());
        jpaStockService.deleteStock(stock);
        return "redirect:/stock";
    }

    @GetMapping("/delete")
    public String removeParticularQuantityFromStock(@RequestParam("id") String id, @RequestParam("productQuantity")
            String quantity, Model model) {
        try {
            Stock stock1 = jpaStockService.getStockProduct(Long.parseLong(id)).orElseThrow(EntityNotFoundException::new);
            if (Integer.parseInt(quantity) > stock1.getProductQuantity()) {
                model.addAttribute("dangerQuantity", String.format("There are not %s %s product on stock", quantity, stock1.getProductName()));
                return "stockView";
            }
            stock1.setProductQuantity(stock1.getProductQuantity() - Integer.parseInt(quantity));
            jpaStockService.updateStock(stock1);
            lostProductService.addStockToLost(stock1, Integer.parseInt(quantity));
        }catch (NumberFormatException e){
            return "stockView";
        }
        return "redirect:/stock";
    }

    @ModelAttribute("stockProduct")
    public Stock getStockProduct() {
        return new Stock();
    }

}

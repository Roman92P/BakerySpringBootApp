package com.bakery.shark.bakery_shark.app.lostProducts;

import com.bakery.shark.bakery_shark.app.model.LostProducts;
import com.bakery.shark.bakery_shark.app.model.Stock;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class JpaLostProductService implements LostProductService {

    private final LostProductRepository lostProductRepository;

    public JpaLostProductService(LostProductRepository lostProductRepository) {
        this.lostProductRepository = lostProductRepository;
    }

    @Override
    public List<LostProducts> getAllLostProducts() {
        return lostProductRepository.findAll();
    }

    @Override
    public Optional<LostProducts> getLostProduct(Long id) {
        return lostProductRepository.findById(id);
    }

    @Override
    public void deleteLostProducts(LostProducts lostProducts) {
        lostProductRepository.delete(lostProducts);
    }

    @Override
    public void updateLostProducts(LostProducts lostProducts) {
        lostProductRepository.save(lostProducts);
    }

    @Override
    public void addLostProduct(LostProducts lostProducts) {
        lostProductRepository.save(lostProducts);

    }

    @Override
    public void addStockToLost(Stock stock, int quantity) {
        LostProducts lostProducts = new LostProducts();
        lostProducts.setLostStock(stock);
        double v = 0;
        if (quantity > 1) {
            v = BigDecimal.valueOf(stock.getProductPrice() * quantity)
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
        }else {
            v=stock.getProductPrice();
        }
        lostProducts.setValue(v);
        lostProductRepository.save(lostProducts);
    }

    @Override
    public List<Object[]> getAllLostsBetweenDates(LocalDate startDate, LocalDate endDate) {
        return lostProductRepository.getLostsBetweenDates(startDate, endDate);
    }
}

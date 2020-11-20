package com.bakery.shark.bakery_shark.app.stock;


import com.bakery.shark.bakery_shark.app.manufacture.ManufacturedRepository;
import com.bakery.shark.bakery_shark.app.model.ManufactureItem;
import com.bakery.shark.bakery_shark.app.model.Manufactured;
import com.bakery.shark.bakery_shark.app.model.Stock;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JpaStockService implements StockService {

    private final StockRepository stockRepository;
    private final ManufacturedRepository manufacturedRepository;

    public JpaStockService(StockRepository stockRepository, ManufacturedRepository manufacturedRepository) {
        this.stockRepository = stockRepository;
        this.manufacturedRepository = manufacturedRepository;
    }

    @Override
    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public Optional<Stock> getStockProduct(Long id) {
        return stockRepository.findById(id);
    }

    @Override
    public void deleteStock(Stock stock) {
        stockRepository.delete(stock);
    }

    @Override
    public void updateStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    public void addStock(Manufactured manufactured) {
        Set<ManufactureItem> manufactureItems = manufactured.getManufactureItems();
        Iterator<ManufactureItem> itemIterator = manufactureItems.iterator();
        while (itemIterator.hasNext()) {
            ManufactureItem next = itemIterator.next();
            String productName = next.getProduct().getName();
            Stock firstByProductName = stockRepository.findFirstByProductName(productName);
            if (firstByProductName == null) {
                Stock stock = new Stock();
                stock.setProductName(next.getProduct().getName());
                stock.setProductQuantity(next.getQuantity());
                stock.setProductPrice(next.getProduct().getPrice());
                stockRepository.save(stock);
            } else if (firstByProductName.getProductName().equals(productName)) {
                firstByProductName.setProductQuantity(next.getQuantity() + firstByProductName.getProductQuantity());
                stockRepository.save(firstByProductName);
            }
        }
    }

    public Stock getStockProductByProductName(String productName) {
        return stockRepository.findFirstByProductName(productName);
    }
}

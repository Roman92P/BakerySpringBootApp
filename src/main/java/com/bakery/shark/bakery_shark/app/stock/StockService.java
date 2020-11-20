package com.bakery.shark.bakery_shark.app.stock;



import com.bakery.shark.bakery_shark.app.model.Manufactured;
import com.bakery.shark.bakery_shark.app.model.Stock;

import java.util.List;
import java.util.Optional;

public interface StockService {

    List<Stock> getAllStock();

    Optional<Stock> getStockProduct(Long id);

    void deleteStock(Stock stock);

    void updateStock(Stock stock);

    void addStock(Manufactured manufactured);
}

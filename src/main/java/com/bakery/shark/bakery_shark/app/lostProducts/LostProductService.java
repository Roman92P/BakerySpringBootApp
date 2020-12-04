package com.bakery.shark.bakery_shark.app.lostProducts;

import com.bakery.shark.bakery_shark.app.model.LostProducts;
import com.bakery.shark.bakery_shark.app.model.Stock;

import java.util.List;
import java.util.Optional;

public interface LostProductService {

    List<LostProducts> getAllLostProducts();

    Optional<LostProducts> getLostProduct(Long id);

    void deleteLostProducts(LostProducts lostProducts);

    void updateLostProducts(LostProducts lostProducts);

    void addLostProduct(LostProducts lostProducts);

    void addStockToLost(Stock stock, int quantity);

    List<Object[]>getAllLostsBetweenDates(String startDate, String endDate);
}

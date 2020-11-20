package com.bakery.shark.bakery_shark.app.workStockProductQuantity;



import com.bakery.shark.bakery_shark.app.model.WorkStockQuantity;

import java.util.List;
import java.util.Optional;

public interface WorkStockQuantityService {

    List<WorkStockQuantity> getAllWorkStock();

    Optional<WorkStockQuantity> getWorkStock(Long id);

    void deleteWorkStockProductQuantity(WorkStockQuantity workStockQuantity);

    void updateWorkStockQuantity(WorkStockQuantity workStockQuantity);

    void addWorkStockQuantity(WorkStockQuantity workStockQuantity);
}

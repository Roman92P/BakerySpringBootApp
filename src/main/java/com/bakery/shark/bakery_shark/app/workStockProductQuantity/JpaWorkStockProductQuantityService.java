package com.bakery.shark.bakery_shark.app.workStockProductQuantity;

import com.bakery.shark.bakery_shark.app.model.WorkStockQuantity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaWorkStockProductQuantityService implements WorkStockQuantityService {

    private final WorkStockProductQuantityRepository workStockProductQuantityRepository;

    public JpaWorkStockProductQuantityService(WorkStockProductQuantityRepository workStockProductQuantityRepository) {
        this.workStockProductQuantityRepository = workStockProductQuantityRepository;
    }

    @Override
    public List<WorkStockQuantity> getAllWorkStock() {
        return workStockProductQuantityRepository.findAll();
    }

    @Override
    public Optional<WorkStockQuantity> getWorkStock(Long id) {
        return workStockProductQuantityRepository.findById(id);
    }

    @Override
    public void deleteWorkStockProductQuantity(WorkStockQuantity workStockQuantity) {
        workStockProductQuantityRepository.delete(workStockQuantity);
    }

    @Override
    public void updateWorkStockQuantity(WorkStockQuantity workStockQuantity) {
        workStockProductQuantityRepository.save(workStockQuantity);
    }

    @Override
    public void addWorkStockQuantity(WorkStockQuantity workStockQuantity) {
        workStockProductQuantityRepository.save(workStockQuantity);
    }

    public WorkStockQuantity getWorkStockQuantityByProductName(String productName){
        return workStockProductQuantityRepository.findByWorkStockProductName(productName);
    }
}

package com.bakery.shark.bakery_shark.app.workIngredientQuantity;

import com.bakery.shark.bakery_shark.app.model.WorkIngredientQuantity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaWorkIngredientQuantityService implements WorkIngredientQuantityService {

    private final WorkIngredientQuantityRepository workIngredientQuantityRepository;

    public JpaWorkIngredientQuantityService(WorkIngredientQuantityRepository workIngredientQuantityRepository) {
        this.workIngredientQuantityRepository = workIngredientQuantityRepository;
    }

    @Override
    public List<WorkIngredientQuantity> getAllWorkIngredients() {
        return workIngredientQuantityRepository.findAll();
    }

    @Override
    public Optional<WorkIngredientQuantity> getWorkIngredient(Long id) {
        return workIngredientQuantityRepository.findById(id);
    }

    @Override
    public void deleteWorkIngredient(WorkIngredientQuantity workIngredientQuantity) {
        workIngredientQuantityRepository.delete(workIngredientQuantity);
    }

    @Override
    public void updateWorkIngredient(WorkIngredientQuantity workIngredientQuantity) {
        workIngredientQuantityRepository.save(workIngredientQuantity);
    }

    @Override
    public void addWorkIngredient(WorkIngredientQuantity workIngredientQuantity) {
        workIngredientQuantityRepository.save(workIngredientQuantity);
    }

    public WorkIngredientQuantity getWorkIngredientByIngredientName(String workIngredientName){
        return workIngredientQuantityRepository.findByWorkIngredientName(workIngredientName);
    }

}

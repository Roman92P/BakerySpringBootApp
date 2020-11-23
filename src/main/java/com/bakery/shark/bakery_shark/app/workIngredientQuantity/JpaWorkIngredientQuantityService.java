package com.bakery.shark.bakery_shark.app.workIngredientQuantity;

import com.bakery.shark.bakery_shark.app.model.Product;
import com.bakery.shark.bakery_shark.app.model.RecipeItem;
import com.bakery.shark.bakery_shark.app.model.WorkIngredientQuantity;
import com.bakery.shark.bakery_shark.app.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JpaWorkIngredientQuantityService implements WorkIngredientQuantityService {

    private final WorkIngredientQuantityRepository workIngredientQuantityRepository;
    private final ProductRepository productRepository;

    public JpaWorkIngredientQuantityService(WorkIngredientQuantityRepository workIngredientQuantityRepository, ProductRepository productRepository) {
        this.workIngredientQuantityRepository = workIngredientQuantityRepository;
        this.productRepository = productRepository;
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

    public WorkIngredientQuantity getWorkIngredientByIngredientName(String workIngredientName) {
        return workIngredientQuantityRepository.findByWorkIngredientName(workIngredientName);
    }

    public void updateWorkQuantity(Long productId, int productQuantity ) {

        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isPresent()) {
            Set<RecipeItem> recipeItemList = byId.get().getRecipe().getRecipeItemList();
//        Set<RecipeItem> recipeItemList = manufactureItem.getProduct().getRecipe().getRecipeItemList();

            for ( RecipeItem recipeItem : recipeItemList ) {
                if (recipeItem.getIngredients().getLitr() != 0) {
                    WorkIngredientQuantity workIngredientByIngredientName =
                            workIngredientQuantityRepository
                                    .findByWorkIngredientName(recipeItem.getIngredients().getName());
                    workIngredientByIngredientName.setWorkIngredientQuantity(workIngredientByIngredientName.getWorkIngredientQuantity() - (recipeItem.getIngredientQuantity() * productQuantity));
                    workIngredientQuantityRepository.save(workIngredientByIngredientName);
                } else if (recipeItem.getIngredients().getQuantity() != 0) {
                    WorkIngredientQuantity workIngredientByIngredientName =
                            workIngredientQuantityRepository
                                    .findByWorkIngredientName(recipeItem.getIngredients().getName());
                    workIngredientByIngredientName.setWorkIngredientQuantity(workIngredientByIngredientName.getWorkIngredientQuantity() - (recipeItem.getIngredientQuantity() * productQuantity));
                    workIngredientQuantityRepository.save(workIngredientByIngredientName);
                } else {
                    WorkIngredientQuantity workIngredientByIngredientName =
                            workIngredientQuantityRepository
                                    .findByWorkIngredientName(recipeItem.getIngredients().getName());
                    workIngredientByIngredientName.setWorkIngredientQuantity(workIngredientByIngredientName.getWorkIngredientQuantity() - (recipeItem.getIngredientQuantity() * productQuantity));
                    workIngredientQuantityRepository.save(workIngredientByIngredientName);
                }
            }
        }
    }
}

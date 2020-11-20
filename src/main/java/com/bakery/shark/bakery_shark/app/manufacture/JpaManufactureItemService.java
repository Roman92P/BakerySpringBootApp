package com.bakery.shark.bakery_shark.app.manufacture;

import com.bakery.shark.bakery_shark.app.model.ManufactureItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class JpaManufactureItemService implements ManufactureItemService{

    private final ManufactureItemRepository manufactureItemRepository;

    public JpaManufactureItemService(ManufactureItemRepository manufactureItemRepository) {
        this.manufactureItemRepository = manufactureItemRepository;
    }

    @Override
    public List<ManufactureItem> getAllManufactureItems() {
        return manufactureItemRepository.findAll();
    }

    @Override
    public Optional<ManufactureItem> getManufactureItem(Long id) {
        return manufactureItemRepository.findById(id);
    }

    @Override
    public void deleteManufactureItem(ManufactureItem manufactureItem) {
        manufactureItemRepository.delete(manufactureItem);
    }

    @Override
    public void updateManufactureItem(ManufactureItem manufactureItem) {
        manufactureItemRepository.save(manufactureItem);
    }

    @Override
    public void addManufactureItem(ManufactureItem manufactureItem) {
        manufactureItemRepository.save(manufactureItem);
    }

    public List<ManufactureItem> getAllByManufactureId(long id){
        return manufactureItemRepository.findAllByManufactured_Id(id);
    }

    public Set<ManufactureItem> getAllItemsWithNullManufatured(){
        return manufactureItemRepository.findAllByManufacturedIsNull();
    }

    public ManufactureItem findByProduct_NameAndManufacturedNull(String productName){
        return manufactureItemRepository.findByProduct_NameAndManufacturedNull(productName);
    }
}

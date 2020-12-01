package com.bakery.shark.bakery_shark.app.produced;

import com.bakery.shark.bakery_shark.app.model.ManufactureItem;
import com.bakery.shark.bakery_shark.app.model.Produced;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaProducedService implements ProducedService {

    private final ProducedRepository producedRepository;

    public JpaProducedService(ProducedRepository producedRepository) {
        this.producedRepository = producedRepository;
    }

    @Override
    public List<Produced> getAllProduced() {
        return producedRepository.findAll();
    }

    @Override
    public Optional<Produced> getProducedProduct(Long id) {
        return producedRepository.findById(id);
    }

    @Override
    public void deleteProduced(Produced produced) {
        producedRepository.delete(produced);
    }

    @Override
    public void updateProduced(Produced produced) {
        producedRepository.save(produced);
    }

    @Override
    public void addProduced(Produced produced) {
        producedRepository.save(produced);
    }

    public void addManufacturedToProduced(List<ManufactureItem> manufactureItemList) {

        for ( ManufactureItem item : manufactureItemList ) {
            Produced produced = new Produced();
            produced.setProductName(item.getProduct().getName());
            produced.setProductPrice(item.getProduct().getPrice());
            produced.setProductStockQuantity(item.getQuantity());
            producedRepository.save(produced);
        }
    }

    public Integer getProducedQuantityToday(){
       return producedRepository.getSumToday();
    }
}

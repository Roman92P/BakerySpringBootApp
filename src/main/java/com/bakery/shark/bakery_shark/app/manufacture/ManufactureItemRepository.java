package com.bakery.shark.bakery_shark.app.manufacture;

import com.bakery.shark.bakery_shark.app.model.ManufactureItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ManufactureItemRepository extends JpaRepository<ManufactureItem, Long> {

        List<ManufactureItem> findAllByManufactured_Id(Long id);

        Set<ManufactureItem> findAllByManufacturedIsNull();

        ManufactureItem findByProduct_NameAndManufacturedNull(String productName);



}

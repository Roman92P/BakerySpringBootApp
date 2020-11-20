package com.bakery.shark.bakery_shark.app.manufacture;



import com.bakery.shark.bakery_shark.app.model.ManufactureItem;

import java.util.List;
import java.util.Optional;

public interface ManufactureItemService {
    List<ManufactureItem> getAllManufactureItems();

    Optional<ManufactureItem> getManufactureItem(Long id);

    void deleteManufactureItem(ManufactureItem manufactureItem);

    void updateManufactureItem(ManufactureItem manufactureItem);

    void addManufactureItem(ManufactureItem manufactureItem);
}

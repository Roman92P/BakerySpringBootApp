package com.bakery.shark.bakery_shark.app.manufacture;



import com.bakery.shark.bakery_shark.app.model.Manufactured;

import java.util.List;
import java.util.Optional;

public interface ManufacturedService {
    List<Manufactured> getAllManufactured();

    Optional<Manufactured> getManufactured(Long id);

    void deleteManufactured(Manufactured manufactured);

    void updateManufactured(Manufactured manufactured);

    void addManufactured(Manufactured manufactured);
}

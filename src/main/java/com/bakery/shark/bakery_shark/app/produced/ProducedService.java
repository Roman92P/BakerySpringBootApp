package com.bakery.shark.bakery_shark.app.produced;



import com.bakery.shark.bakery_shark.app.model.Produced;

import java.util.List;
import java.util.Optional;

public interface ProducedService {
    List<Produced> getAllProduced();

    Optional<Produced> getProducedProduct(Long id);

    void deleteProduced(Produced produced);

    void updateProduced(Produced produced);

    void addProduced(Produced produced);
}

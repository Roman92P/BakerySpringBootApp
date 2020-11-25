package com.bakery.shark.bakery_shark.app.manufacture;

import com.bakery.shark.bakery_shark.app.model.Manufactured;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ManufacturedRepository extends JpaRepository<Manufactured, Long> {
    Manufactured findByFinalizedWorkOrderFalse();

}

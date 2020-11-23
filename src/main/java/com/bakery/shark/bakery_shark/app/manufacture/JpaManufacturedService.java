package com.bakery.shark.bakery_shark.app.manufacture;

import com.bakery.shark.bakery_shark.app.model.Manufactured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaManufacturedService implements ManufacturedService {

    private final ManufacturedRepository manufacturedRepository;

    public JpaManufacturedService(ManufacturedRepository manufacturedRepository) {
        this.manufacturedRepository = manufacturedRepository;
    }


    @Override
    public List<Manufactured> getAllManufactured() {
        return manufacturedRepository.findAll();
    }

    @Override
    public Optional<Manufactured> getManufactured(Long id) {
        return manufacturedRepository.findById(id);
    }

    @Override
    public void deleteManufactured(Manufactured manufactured) {
        manufacturedRepository.delete(manufactured);
    }

    @Override
    public void updateManufactured(Manufactured manufactured) {
        manufacturedRepository.save(manufactured);
    }

    @Override
    public void addManufactured(Manufactured manufactured) {
        manufacturedRepository.save(manufactured);
    }

    public Manufactured getManufacturedNotFinalized() {
        return manufacturedRepository.findByFinalizedWorkOrderFalse();
    }
}

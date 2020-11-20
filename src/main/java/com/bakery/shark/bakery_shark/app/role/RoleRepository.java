package com.bakery.shark.bakery_shark.app.role;

import com.bakery.shark.bakery_shark.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}

package me.braun.spacecraftservice.data.repository;


import me.braun.spacecraftservice.data.model.CraftName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CraftNameRepository extends JpaRepository<CraftName, Byte> {
    Optional<CraftName> findById(Byte id);
}


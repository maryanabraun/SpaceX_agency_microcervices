package me.braun.spacecraftservice.data.repository;


import me.braun.spacecraftservice.data.model.Spacecraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpacecraftRepository extends JpaRepository<Spacecraft, Long> {
}

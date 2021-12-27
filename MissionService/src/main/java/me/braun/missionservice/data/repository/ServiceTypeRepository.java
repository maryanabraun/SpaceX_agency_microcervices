package me.braun.missionservice.data.repository;


import me.braun.missionservice.data.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceTypeRepository extends JpaRepository<ServiceType, Byte> {

}

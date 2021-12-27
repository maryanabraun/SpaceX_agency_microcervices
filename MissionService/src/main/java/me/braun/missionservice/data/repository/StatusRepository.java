package me.braun.missionservice.data.repository;

import me.braun.missionservice.data.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Short> {

}

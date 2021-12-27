package me.braun.spacecraftservice.service;

import me.braun.spacecraftservice.data.model.CraftName;

import me.braun.spacecraftservice.data.repository.CraftNameRepository;
import me.braun.spacecraftservice.exception.CraftNotFoundException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CraftNameService {
    private final CraftNameRepository craftNameRepository;

    public CraftNameService(CraftNameRepository craftNameRepository) {
        this.craftNameRepository = craftNameRepository;
    }

    public List<CraftName> getCraftNames(){return craftNameRepository.findAll();}

    public CraftName getCraftNameById(byte id) {
        Optional<CraftName> craftName = craftNameRepository.findById(id);
        if (craftName.isPresent()) {
            log.info("spacecraft title: {}", craftName.get());
            return craftName.get();
        }
        throw new CraftNotFoundException();
    }

    public void deleteCraftName(byte id) {
        craftNameRepository.deleteById(id);
    }
}

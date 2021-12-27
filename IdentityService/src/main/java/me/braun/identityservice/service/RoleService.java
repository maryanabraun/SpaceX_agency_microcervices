package me.braun.identityservice.service;


import me.braun.identityservice.data.model.Role;
import me.braun.identityservice.data.repository.RoleRepository;
import me.braun.identityservice.exception.RoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getroles(){return roleRepository.findAll();}


    public Role getroleById(long id) {
      Optional<Role> role = roleRepository.findById(id);
      if (role.isPresent()) {
          log.info("role: {}", role.get());
          return role.get();
      }
      throw new RoleNotFoundException();

    }

    public void deleteRole(long id) {
        roleRepository.deleteById(id);
    }
}


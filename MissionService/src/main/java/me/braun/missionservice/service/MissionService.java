package me.braun.missionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import me.braun.missionservice.api.dto.AccountDto;
import me.braun.missionservice.api.dto.MissionDto;
import me.braun.missionservice.api.dto.SpacecraftDto;
import me.braun.missionservice.data.model.Mission;
import me.braun.missionservice.data.model.ServiceType;
import me.braun.missionservice.data.model.Status;
import me.braun.missionservice.data.repository.MissionRepository;
import me.braun.missionservice.data.repository.ServiceTypeRepository;
import me.braun.missionservice.data.repository.StatusRepository;
import me.braun.missionservice.exception.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MissionService {
    private final MissionRepository missionRepository;
    private final StatusRepository statusRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final String usersUrl = "http://localhost:8081/accounts";
    private final String spacecraftUrl = "http://localhost:8083/spacecrafts";

    public List<Mission> getMissions(){
        return missionRepository.findAll();
    }

    public Mission getMissionById(Long id) {
        Optional<Mission> mission = missionRepository.findById(id);
        if (mission.isPresent()) {
            log.info("mission: {}", mission.get());
            return mission.get();
        }
        throw new MissionNotFoundException();
    }

    public AccountDto getCustomer(long missionId, HttpEntity<?> headers) {
        final Optional<Mission> mission = missionRepository.findById(missionId);

        if (mission.isPresent()) {

            final long customer = mission.get().getCustomerId();
            final RestTemplate restTemplate = new RestTemplate();


            final ResponseEntity<AccountDto> userResponse = restTemplate
                    .exchange(usersUrl + "/" + customer,
                            HttpMethod.GET, headers, AccountDto.class);
            return userResponse.getBody();
        }
        else
            throw new MissionNotFoundException();
    }

    public SpacecraftDto getSpacecraft(long missionId, HttpEntity<?> headers) {
        final Optional<Mission> mission = missionRepository.findById(missionId);

        if (mission.isPresent()) {

            final long spacecraft = mission.get().getSpaceCraftId();
            final RestTemplate restTemplate = new RestTemplate();


            final ResponseEntity<SpacecraftDto> craftResponse = restTemplate
                    .exchange(spacecraftUrl + "/" + spacecraft,
                            HttpMethod.GET, headers, SpacecraftDto.class);
            return craftResponse.getBody();
        }
        else
            throw new MissionNotFoundException();
    }

    public MissionDto missionToDto(Mission mission){
        return MissionDto.builder()
                .name(mission.getName())
                .description(mission.getDescription())
                .customerId(mission.getCustomerId())
                .statusId(mission.getStatus().getId())
                .curatorId(mission.getCuratorId())
                .spaceCraftId(mission.getSpaceCraftId())
                .date(mission.getDate())
                .serviceId(mission.getServiceType().getId())
                .duration(mission.getDuration())
                .missionPrice(mission.getMissionPrice())
                .servicePrice(mission.getServicePrice())
                .build();
    }

    public Long createMission(String name, String description, Long customerId, Long spacecraftId,
                              Short statusId, Long curatorId, int payloadWeigh,
                              Date date, int duration, Byte serviceTypeId) throws MissionAlreadyExistsException {
        missionRepository.findByName(name).ifPresent(mission -> {throw new MissionAlreadyExistsException();});
        Mission.MissionBuilder builder = Mission.builder();
        if (curatorId != null) builder = builder.curatorId(curatorId);
        if(spacecraftId != null) builder = builder.spaceCraftId(spacecraftId);
        if(statusId != null) builder = builder.status(statusRepository
                .findById(statusId).orElseThrow(StatusNotFoundException::new));
        if(date != null) builder = builder.date(date);
        Mission mission = builder
                .name(name)
                .description(description)
                .customerId(customerId)
                .serviceType(serviceTypeRepository.findById(serviceTypeId).orElseThrow(ServiceNotFoundExeption::new))
                .payloadWeigh(payloadWeigh)
                .duration(duration).build();
                Mission saveMission = missionRepository.save(mission);
                return saveMission.getId();
    }
    public Long updateMission(long id, String name, String description, Long customerId, Long spacecraftId,
                              Short statusId, Long curatorId, Integer payloadWeigh,
                              Date date, Integer duration, Byte serviceTypeId ) throws MissionNotFoundException {
        final Optional<Mission> maybeMission = missionRepository.findById(id);
        final Mission mission = maybeMission.orElseThrow(MissionNotFoundException::new);
        if (name != null && !name.isBlank()) mission.setName(name);
        if (description != null && !description.isBlank()) mission.setDescription(description);
        if(customerId != null) {mission.setCustomerId(customerId);}
        if (curatorId != null) {
            mission.setCuratorId(curatorId);}
        if (spacecraftId != null) {
            mission.setSpaceCraftId(spacecraftId);}
        if(statusId != null) {
            final Optional<Status> status = statusRepository.findById(statusId);
            status.ifPresent(mission::setStatus);
        }
        if(serviceTypeId != null) {
            final Optional<ServiceType> serviceType = serviceTypeRepository.findById(serviceTypeId);
            serviceType.ifPresent(mission::setServiceType);
        }
        if(payloadWeigh != null) {
            mission.setPayloadWeigh(payloadWeigh);
        }
        if(date != null) {
            mission.setDate(date);
        }
        if(duration != null) {
            mission.setDuration(duration);
        }
        Mission saveMission = missionRepository.save(mission);
        return saveMission.getId();


    }

    public void deleteMission(Long id) {
        missionRepository.deleteById(id);
    }
}





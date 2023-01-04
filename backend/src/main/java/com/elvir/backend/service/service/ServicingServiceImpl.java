package com.elvir.backend.service.service;

import com.elvir.backend.model.dto.ServicingDto;
import com.elvir.backend.model.entity.Servicing;
import com.elvir.backend.model.mapper.ServicingMapper;
import com.elvir.backend.model.repo.ServicingRepository;
import com.elvir.backend.model.request.ServicingInfo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicingServiceImpl implements ServicingService {

    private final ServicingRepository servicingRepository;
    private final ServicingMapper MAPPER = Mappers.getMapper(ServicingMapper.class);

    @Override
    @Transactional
    public void create(ServicingInfo servicingInfo) {
        final Servicing servicing = MAPPER.servicingInfoToServicing(servicingInfo);
        servicingRepository.save(servicing);
    }

    @Override
    @Transactional
    public void update(UUID uuid, ServicingInfo servicingInfo) {
        final Servicing servicing = servicingRepository.findById(uuid).orElseThrow(RuntimeException::new);
        servicing.updateByServicingInfo(servicingInfo);
        servicingRepository.save(servicing);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        servicingRepository.deleteById(uuid);
    }

    @Override
    @Transactional(readOnly = true)
    public ServicingDto get(UUID uuid) {
        final Servicing servicing = servicingRepository.findById(uuid).orElseThrow(RuntimeException::new);
        return MAPPER.servicingToServicingDto(servicing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ServicingDto> getList() {
        return servicingRepository.findAll().stream()
                .map(MAPPER::servicingToServicingDto)
                .collect(Collectors.toList());
    }
}

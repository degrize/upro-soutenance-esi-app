package com.esi.uppro.service.impl;

import com.esi.uppro.domain.Genie;
import com.esi.uppro.repository.GenieRepository;
import com.esi.uppro.service.GenieService;
import com.esi.uppro.service.dto.GenieDTO;
import com.esi.uppro.service.mapper.GenieMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Genie}.
 */
@Service
@Transactional
public class GenieServiceImpl implements GenieService {

    private final Logger log = LoggerFactory.getLogger(GenieServiceImpl.class);

    private final GenieRepository genieRepository;

    private final GenieMapper genieMapper;

    public GenieServiceImpl(GenieRepository genieRepository, GenieMapper genieMapper) {
        this.genieRepository = genieRepository;
        this.genieMapper = genieMapper;
    }

    @Override
    public GenieDTO save(GenieDTO genieDTO) {
        log.debug("Request to save Genie : {}", genieDTO);
        Genie genie = genieMapper.toEntity(genieDTO);
        genie = genieRepository.save(genie);
        return genieMapper.toDto(genie);
    }

    @Override
    public GenieDTO update(GenieDTO genieDTO) {
        log.debug("Request to save Genie : {}", genieDTO);
        Genie genie = genieMapper.toEntity(genieDTO);
        genie = genieRepository.save(genie);
        return genieMapper.toDto(genie);
    }

    @Override
    public Optional<GenieDTO> partialUpdate(GenieDTO genieDTO) {
        log.debug("Request to partially update Genie : {}", genieDTO);

        return genieRepository
            .findById(genieDTO.getId())
            .map(existingGenie -> {
                genieMapper.partialUpdate(existingGenie, genieDTO);

                return existingGenie;
            })
            .map(genieRepository::save)
            .map(genieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GenieDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Genies");
        return genieRepository.findAll(pageable).map(genieMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenieDTO> findOne(Long id) {
        log.debug("Request to get Genie : {}", id);
        return genieRepository.findById(id).map(genieMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Genie : {}", id);
        genieRepository.deleteById(id);
    }
}

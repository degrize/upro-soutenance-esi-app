package com.esi.uppro.service.impl;

import com.esi.uppro.domain.Eleve;
import com.esi.uppro.domain.Soutenance;
import com.esi.uppro.repository.EleveRepository;
import com.esi.uppro.repository.SoutenanceRepository;
import com.esi.uppro.repository.UserRepository;
import com.esi.uppro.service.EleveService;
import com.esi.uppro.service.dto.EleveDTO;
import com.esi.uppro.service.dto.SoutenanceDTO;
import com.esi.uppro.service.mapper.EleveMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Eleve}.
 */
@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveServiceImpl.class);

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SoutenanceRepository soutenanceRepository;

    public EleveServiceImpl(EleveRepository eleveRepository, EleveMapper eleveMapper) {
        this.eleveRepository = eleveRepository;
        this.eleveMapper = eleveMapper;
    }

    @Override
    public EleveDTO save(EleveDTO eleveDTO) {
        log.debug("Request to save Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);

        userRepository.saveEleve(eleve.getId());
        return eleveMapper.toDto(eleve);
    }

    @Override
    public EleveDTO update(EleveDTO eleveDTO) {
        log.debug("Request to save Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);
        return eleveMapper.toDto(eleve);
    }

    @Override
    public Optional<EleveDTO> partialUpdate(EleveDTO eleveDTO) {
        log.debug("Request to partially update Eleve : {}", eleveDTO);

        return eleveRepository
            .findById(eleveDTO.getId())
            .map(existingEleve -> {
                eleveMapper.partialUpdate(existingEleve, eleveDTO);

                return existingEleve;
            })
            .map(eleveRepository::save)
            .map(eleveMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EleveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eleves");
        return eleveRepository.findAll(pageable).map(eleveMapper::toDto);
    }

    public Page<EleveDTO> findAllWithEagerRelationships(Pageable pageable) {
        return eleveRepository.findAllWithEagerRelationships(pageable).map(eleveMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EleveDTO> findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        return eleveRepository.findOneWithEagerRelationships(id).map(eleveMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.deleteById(id);
    }

    @Override
    public List<Eleve> getEleves() {
        List<Soutenance> soutenances = soutenanceRepository.soutenancesvalide();
        List<Eleve> eleves = eleveRepository.getEleves();
        List<Eleve> etudiants = new ArrayList<Eleve>();

        int notYet = 0;

        for (Eleve eleve : eleves) {
            notYet = 0;
            for (Soutenance souten : soutenances) {
                if (souten.getProjet().getId() == eleve.getProjet().getId()) {
                    notYet++;
                }
            }
            if (notYet == 0) {
                etudiants.add(eleve);
            }
        }

        return etudiants;
    }
}

package com.esi.uppro.service.impl;

import com.esi.uppro.domain.AnneeAcademique;
import com.esi.uppro.repository.AnneeAcademiqueRepository;
import com.esi.uppro.service.AnneeAcademiqueService;
import com.esi.uppro.service.dto.AnneeAcademiqueDTO;
import com.esi.uppro.service.mapper.AnneeAcademiqueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link AnneeAcademique}.
 */
@Service
@Transactional
public class AnneeAcademiqueServiceImpl implements AnneeAcademiqueService {

    private final Logger log = LoggerFactory.getLogger(AnneeAcademiqueServiceImpl.class);

    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    private final AnneeAcademiqueMapper anneeAcademiqueMapper;

    public AnneeAcademiqueServiceImpl(AnneeAcademiqueRepository anneeAcademiqueRepository, AnneeAcademiqueMapper anneeAcademiqueMapper) {
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
        this.anneeAcademiqueMapper = anneeAcademiqueMapper;
    }

    @Override
    public AnneeAcademiqueDTO save(AnneeAcademiqueDTO anneeAcademiqueDTO) {
        log.debug("Request to save AnneeAcademique : {}", anneeAcademiqueDTO);
        AnneeAcademique anneeAcademique = anneeAcademiqueMapper.toEntity(anneeAcademiqueDTO);
        anneeAcademique = anneeAcademiqueRepository.save(anneeAcademique);
        return anneeAcademiqueMapper.toDto(anneeAcademique);
    }

    @Override
    public AnneeAcademiqueDTO update(AnneeAcademiqueDTO anneeAcademiqueDTO) {
        log.debug("Request to save AnneeAcademique : {}", anneeAcademiqueDTO);
        AnneeAcademique anneeAcademique = anneeAcademiqueMapper.toEntity(anneeAcademiqueDTO);
        anneeAcademique = anneeAcademiqueRepository.save(anneeAcademique);
        return anneeAcademiqueMapper.toDto(anneeAcademique);
    }

    @Override
    public Optional<AnneeAcademiqueDTO> partialUpdate(AnneeAcademiqueDTO anneeAcademiqueDTO) {
        log.debug("Request to partially update AnneeAcademique : {}", anneeAcademiqueDTO);

        return anneeAcademiqueRepository
            .findById(anneeAcademiqueDTO.getId())
            .map(existingAnneeAcademique -> {
                anneeAcademiqueMapper.partialUpdate(existingAnneeAcademique, anneeAcademiqueDTO);

                return existingAnneeAcademique;
            })
            .map(anneeAcademiqueRepository::save)
            .map(anneeAcademiqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnneeAcademiqueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnneeAcademiques");
        return anneeAcademiqueRepository.findAll(pageable).map(anneeAcademiqueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnneeAcademiqueDTO> findOne(Long id) {
        log.debug("Request to get AnneeAcademique : {}", id);
        return anneeAcademiqueRepository.findById(id).map(anneeAcademiqueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnneeAcademique : {}", id);
        anneeAcademiqueRepository.deleteById(id);
    }
}

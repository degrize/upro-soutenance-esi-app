package com.esi.uppro.service.impl;

import com.esi.uppro.domain.NoteEntrepriseRapport;
import com.esi.uppro.repository.NoteEntrepriseRapportRepository;
import com.esi.uppro.service.NoteEntrepriseRapportService;
import com.esi.uppro.service.dto.NoteEntrepriseRapportDTO;
import com.esi.uppro.service.mapper.NoteEntrepriseRapportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NoteEntrepriseRapport}.
 */
@Service
@Transactional
public class NoteEntrepriseRapportServiceImpl implements NoteEntrepriseRapportService {

    private final Logger log = LoggerFactory.getLogger(NoteEntrepriseRapportServiceImpl.class);

    private final NoteEntrepriseRapportRepository noteEntrepriseRapportRepository;

    private final NoteEntrepriseRapportMapper noteEntrepriseRapportMapper;

    public NoteEntrepriseRapportServiceImpl(
        NoteEntrepriseRapportRepository noteEntrepriseRapportRepository,
        NoteEntrepriseRapportMapper noteEntrepriseRapportMapper
    ) {
        this.noteEntrepriseRapportRepository = noteEntrepriseRapportRepository;
        this.noteEntrepriseRapportMapper = noteEntrepriseRapportMapper;
    }

    @Override
    public NoteEntrepriseRapportDTO save(NoteEntrepriseRapportDTO noteEntrepriseRapportDTO) {
        log.debug("Request to save NoteEntrepriseRapport : {}", noteEntrepriseRapportDTO);
        NoteEntrepriseRapport noteEntrepriseRapport = noteEntrepriseRapportMapper.toEntity(noteEntrepriseRapportDTO);
        noteEntrepriseRapport = noteEntrepriseRapportRepository.save(noteEntrepriseRapport);
        return noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);
    }

    @Override
    public NoteEntrepriseRapportDTO update(NoteEntrepriseRapportDTO noteEntrepriseRapportDTO) {
        log.debug("Request to save NoteEntrepriseRapport : {}", noteEntrepriseRapportDTO);
        NoteEntrepriseRapport noteEntrepriseRapport = noteEntrepriseRapportMapper.toEntity(noteEntrepriseRapportDTO);
        noteEntrepriseRapport = noteEntrepriseRapportRepository.save(noteEntrepriseRapport);
        return noteEntrepriseRapportMapper.toDto(noteEntrepriseRapport);
    }

    @Override
    public Optional<NoteEntrepriseRapportDTO> partialUpdate(NoteEntrepriseRapportDTO noteEntrepriseRapportDTO) {
        log.debug("Request to partially update NoteEntrepriseRapport : {}", noteEntrepriseRapportDTO);

        return noteEntrepriseRapportRepository
            .findById(noteEntrepriseRapportDTO.getId())
            .map(existingNoteEntrepriseRapport -> {
                noteEntrepriseRapportMapper.partialUpdate(existingNoteEntrepriseRapport, noteEntrepriseRapportDTO);

                return existingNoteEntrepriseRapport;
            })
            .map(noteEntrepriseRapportRepository::save)
            .map(noteEntrepriseRapportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NoteEntrepriseRapportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all NoteEntrepriseRapports");
        return noteEntrepriseRapportRepository.findAll(pageable).map(noteEntrepriseRapportMapper::toDto);
    }

    public Page<NoteEntrepriseRapportDTO> findAllWithEagerRelationships(Pageable pageable) {
        return noteEntrepriseRapportRepository.findAllWithEagerRelationships(pageable).map(noteEntrepriseRapportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NoteEntrepriseRapportDTO> findOne(Long id) {
        log.debug("Request to get NoteEntrepriseRapport : {}", id);
        return noteEntrepriseRapportRepository.findOneWithEagerRelationships(id).map(noteEntrepriseRapportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NoteEntrepriseRapport : {}", id);
        noteEntrepriseRapportRepository.deleteById(id);
    }
}

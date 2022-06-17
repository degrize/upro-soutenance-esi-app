package com.esi.uppro.service.mapper;

import com.esi.uppro.domain.Genie;
import com.esi.uppro.service.dto.GenieDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Genie} and its DTO {@link GenieDTO}.
 */
@Mapper(componentModel = "spring")
public interface GenieMapper extends EntityMapper<GenieDTO, Genie> {}

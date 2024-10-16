package com.leap.document.service.mapper;

import com.leap.document.domain.*;
import com.leap.document.service.dto.DocumentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Document} and its DTO {@link DocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = { DocumentTypeMapper.class })
public interface DocumentMapper extends EntityMapper<DocumentDTO, Document> {
    @Mapping(target = "documentType", source = "documentType", qualifiedByName = "id")
    DocumentDTO toDto(Document s);
}

package com.leap.document.service.mapper;

import com.leap.document.domain.*;
import com.leap.document.service.dto.DocumentTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DocumentType} and its DTO {@link DocumentTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DocumentTypeMapper extends EntityMapper<DocumentTypeDTO, DocumentType> {
    @Named("documentTypeName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "documentTypeName", source = "documentTypeName")
    DocumentTypeDTO toDtoDocumentTypeName(DocumentType documentType);
}

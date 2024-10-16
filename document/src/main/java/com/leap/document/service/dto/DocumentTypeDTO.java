package com.leap.document.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.leap.document.domain.DocumentType} entity.
 */
public class DocumentTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String documentTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentTypeName() {
        return documentTypeName;
    }

    public void setDocumentTypeName(String documentTypeName) {
        this.documentTypeName = documentTypeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocumentTypeDTO)) {
            return false;
        }

        DocumentTypeDTO documentTypeDTO = (DocumentTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, documentTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocumentTypeDTO{" +
            "id=" + getId() +
            ", documentTypeName='" + getDocumentTypeName() + "'" +
            "}";
    }
}

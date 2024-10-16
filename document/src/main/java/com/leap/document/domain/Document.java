package com.leap.document.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Document.
 */
@Entity
@Table(name = "document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "document_name", nullable = false)
    private String documentName;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @ManyToOne
    private DocumentType documentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Document id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return this.documentName;
    }

    public Document documentName(String documentName) {
        this.setDocumentName(documentName);
        return this;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDescription() {
        return this.description;
    }

    public Document description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getEmployeeId() {
        return this.employeeId;
    }

    public Document employeeId(Long employeeId) {
        this.setEmployeeId(employeeId);
        return this;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public DocumentType getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public Document documentType(DocumentType documentType) {
        this.setDocumentType(documentType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document)) {
            return false;
        }
        return id != null && id.equals(((Document) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Document{" +
            "id=" + getId() +
            ", documentName='" + getDocumentName() + "'" +
            ", description='" + getDescription() + "'" +
            ", employeeId=" + getEmployeeId() +
            "}";
    }
}

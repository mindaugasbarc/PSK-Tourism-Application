package com.tourism.psk.model;

import com.tourism.psk.constants.DocumentStatus;
import com.tourism.psk.constants.DocumentType;
import com.tourism.psk.model.response.TripResponse;

import javax.persistence.*;

@Entity
public class Document {

    @Id
    @GeneratedValue
    private long id;
    DocumentType documentType;
    private DocumentStatus documentStatus;
    private String json;

    public Document() {
    }

    public Document(DocumentStatus documentStatus, DocumentType documentType, String json) {
        this.documentType = documentType;
        this.documentStatus = documentStatus;
        this.json = json;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}

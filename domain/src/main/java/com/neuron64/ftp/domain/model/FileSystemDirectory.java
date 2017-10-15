package com.neuron64.ftp.domain.model;

import com.neuron64.ftp.domain.repository.FileSystemRepository;

/**
 * Created by yks-11 on 10/13/17.
 */

public class FileSystemDirectory {

    private String title;
    private String documentId;
    private String availableBytes;
    private String type;

    public FileSystemDirectory(String title, String documentId, String availableBytes, String type) {
        this.title = title;
        this.documentId = documentId;
        this.availableBytes = availableBytes;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getAvailableBytes() {
        return availableBytes;
    }

    public void setAvailableBytes(String availableBytes) {
        this.availableBytes = availableBytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

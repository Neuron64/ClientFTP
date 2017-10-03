package com.neuron64.ftp.client.demo;

/**
 * Created by yks-11 on 10/3/17.
 */

public class Root {

    private DocumentsProvider documentsProvider;

    private String id;
    private String documentId;
    private String title;
    private int icon;
    private long availableBytes;
    private int flags;

    public Root(DocumentsProvider documentsProvider, String id, String documentId, String title, int icon, long availableBytes, int flags) {
        this.documentsProvider = documentsProvider;
        this.id = id;
        this.documentId = documentId;
        this.title = title;
        this.icon = icon;
        this.availableBytes = availableBytes;
        this.flags = flags;
    }

    public DocumentsProvider getDocumentsProvider() {
        return documentsProvider;
    }

    public void setDocumentsProvider(DocumentsProvider documentsProvider) {
        this.documentsProvider = documentsProvider;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public long getAvailableBytes() {
        return availableBytes;
    }

    public void setAvailableBytes(long availableBytes) {
        this.availableBytes = availableBytes;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }
}

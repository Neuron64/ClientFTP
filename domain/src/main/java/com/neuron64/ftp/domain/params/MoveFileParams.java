package com.neuron64.ftp.domain.params;

/**
 * Created by yks-11 on 11/17/17.
 */

public class MoveFileParams {

    private String parentDocumentId;

    private String documentId;

    private String targetDocumentId;

    public MoveFileParams(String parentDocumentId, String documentId, String targetDocumentId) {
        this.parentDocumentId = parentDocumentId;
        this.documentId = documentId;
        this.targetDocumentId = targetDocumentId;
    }

    public String getParentDocumentId() {
        return parentDocumentId;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getTargetDocumentId() {
        return targetDocumentId;
    }
}

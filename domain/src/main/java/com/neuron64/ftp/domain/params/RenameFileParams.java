package com.neuron64.ftp.domain.params;

/**
 * Created by yks-11 on 11/16/17.
 */

public class RenameFileParams {

    private String newName;
    private String idDocument;

    public RenameFileParams(String newName, String idDocument) {
        this.newName = newName;
        this.idDocument = idDocument;
    }

    public String getNewName() {
        return newName;
    }

    public String getIdDocument() {
        return idDocument;
    }
}

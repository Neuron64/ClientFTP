package com.neuron64.ftp.client.demo.docprovider;

/**
 * Created by yks-11 on 10/5/17.
 */

public class RootInfo {

    private String rootId;
    private String title;
    private String docId;

    public RootInfo(String rootId, String title, String docId) {
        this.rootId = rootId;
        this.title = title;
        this.docId = docId;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}

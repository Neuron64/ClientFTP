package com.neuron64.ftp.client.demo;

import android.provider.DocumentsContract;
import android.webkit.MimeTypeMap;

import java.io.File;

public abstract class DocumentAccessor<T> {

    protected abstract String getDocumentId(T item);

    protected abstract String getMimeType(T item);

    protected abstract String getDisplayName(T item);

    protected abstract String getSummary(T item);

    protected abstract long getLastModified(T item);

    protected abstract int getIcon(T item);

    protected abstract int getFlags(T item);

    protected abstract long getSize(T item);

    protected boolean isFolder(T item) {
        return getMimeType(item).equals(DocumentsContract.Document.MIME_TYPE_DIR);
    }

}

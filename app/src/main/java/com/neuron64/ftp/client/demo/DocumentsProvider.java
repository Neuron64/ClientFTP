package com.neuron64.ftp.client.demo;

import android.content.Context;
import android.database.Cursor;
import android.provider.DocumentsContract;

import java.io.FileNotFoundException;
import java.util.Collection;

/**
 * Created by yks-11 on 10/3/17.
 */

public abstract class DocumentsProvider {

    private Context mContext;

    public Context getContext(){
        return mContext;
    }

    public DocumentsProvider(Context context){
        this.mContext = context;
    }

    public abstract Collection<? extends Root> getRoots() throws FileNotFoundException;

    public abstract String getId();

    public abstract Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder, String mimeFilter) throws FileNotFoundException;

}

package com.neuron64.ftp.client.demo;

import android.database.Cursor;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yks-11 on 10/3/17.
 */

public class DocumentsProviderRegistry {

    private static final String TAG = "DocumentsProviderRegist";

    private Map<String, DocumentsProvider> mProviderMap;

    public DocumentsProviderRegistry(){
        mProviderMap = new LinkedHashMap<>();
    }

    public void register(String id, DocumentsProvider provider){
        mProviderMap.put(id, provider);
    }

    public DocumentsProvider getProvider(String id) {
        return mProviderMap.get(id);
    }

    public Collection<DocumentsProvider> getAll(){
        return mProviderMap.values();
    }

    public List<Root> getAllRoots(){
        List<Root> rootList = new ArrayList<>();
        for (DocumentsProvider documentsProvider : mProviderMap.values()) {
            try {
                rootList.addAll(documentsProvider.getRoots());
            }catch (FileNotFoundException e){
                Log.e(TAG, "getAllRoots: ", e);
            }
        }
        return rootList;
    }

    private static DocumentsProviderRegistry instance;

    public static DocumentsProviderRegistry get() {
        if (instance == null) {
            instance = new DocumentsProviderRegistry();
        }
        return instance;
    }
}


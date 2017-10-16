package com.neuron64.ftp.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;

import com.neuron64.ftp.data.content_provider.ExternalStorageProvider;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by yks-11 on 10/13/17.
 */

public class FileSystemDataRepository implements FileSystemRepository{

    private static final String TAG = "FileSystemDataRepositor";

    private Context context;

    private String nowDirectory;

    @Inject
    public FileSystemDataRepository(Context context){
        this.context = context;
        this.nowDirectory = "emulated:";
    }

    @Override
    public Single<List<FileSystemDirectory>> getExternalStorageFiles(String directoryId) {
        return Single.fromCallable(() -> {
            List<FileSystemDirectory> fileSystemDirectories = new ArrayList<>();
            String rootDirectory = directoryId == null ? nowDirectory : directoryId;
            nowDirectory = rootDirectory;

            Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, rootDirectory);
            Cursor cursor = context.getContentResolver().query(contentsUri, null, null, null);

            if(cursor != null && cursor.moveToFirst()){
                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME));
                    String documentId = cursor.getString(cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DOCUMENT_ID));
                    String mimeTypes = cursor.getString(cursor.getColumnIndex(DocumentsContract.Document.COLUMN_MIME_TYPE));
                    String avalibleBytes = cursor.getString(cursor.getColumnIndex(DocumentsContract.Document.COLUMN_SIZE));
                    fileSystemDirectories.add(new FileSystemDirectory(title, documentId, avalibleBytes, mimeTypes));
                }
            }

            return fileSystemDirectories;
        });
    }

}

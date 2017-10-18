package com.neuron64.ftp.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;

import com.neuron64.ftp.data.content_provider.ExternalStorageProvider;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.model.FileSystemDirectory;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.annotations.NonNull;

/**
 * Created by yks-11 on 10/13/17.
 */

public class FileSystemDataRepository implements FileSystemRepository{

    private static final String TAG = "FileSystemDataRepositor";

    private Context context;

    private List<String> previousFolder;


    @Inject
    public FileSystemDataRepository(Context context){
        this.context = context;
        previousFolder = new ArrayList<>();
        previousFolder.add("emulated:");
    }

    @Override
    public Single<List<FileSystemDirectory>> getExternalStorageFiles() {
       return Single.fromCallable(() -> {
           Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, previousFolder.get(previousFolder.size() - 1));
           return getDirectoryFiles(contentsUri);
        });
    }

    @Override
    public Single<List<FileSystemDirectory>> getNextFiles(@NonNull String directoryId) {
        return Single.fromCallable(() -> {
            previousFolder.add(directoryId);
            Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, directoryId);
            return getDirectoryFiles(contentsUri);
        });
    }

    @Override
    public Single<List<FileSystemDirectory>> getPreviousFiles() {
        if(previousFolder.size() > 1){
            return Single.fromCallable(() -> {
                previousFolder.remove(previousFolder.size() - 1);
                Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, previousFolder.get(previousFolder.size() - 1));
                return getDirectoryFiles(contentsUri);
            });
        }else {
            return Single.fromCallable(() -> {
                throw new ErrorThisIsRootDirectory();
            });
        }
    }

    private List<FileSystemDirectory> getDirectoryFiles(Uri contentsUri){
        List<FileSystemDirectory> fileSystemDirectories = new ArrayList<>();
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
    }
}

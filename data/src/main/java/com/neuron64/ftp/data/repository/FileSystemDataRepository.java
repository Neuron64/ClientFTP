package com.neuron64.ftp.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;

import com.neuron64.ftp.data.content_provider.CustomDocumentColumn;
import com.neuron64.ftp.data.content_provider.ExternalStorageProvider;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.model.FileInfo;
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
    public Single<List<FileInfo>> getExternalStorageFiles() {
       return Single.fromCallable(() -> {
           Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, previousFolder.get(previousFolder.size() - 1));
           return getDirectoryFiles(contentsUri);
        });
    }

    @Override
    public Single<List<FileInfo>> getNextFiles(@NonNull String directoryId) {
        return Single.fromCallable(() -> {
            previousFolder.add(directoryId);
            Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, directoryId);
            return getDirectoryFiles(contentsUri);
        });
    }

    @Override
    public Single<List<FileInfo>> getPreviousFiles() {
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

    private List<FileInfo> getDirectoryFiles(Uri contentsUri){
        List<FileInfo> fileSystemDirectories = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(contentsUri, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(CustomDocumentColumn.COLUMN_DISPLAY_NAME));
                String documentId = cursor.getString(cursor.getColumnIndex(CustomDocumentColumn.COLUMN_DOCUMENT_ID));
                String mimeTypes = cursor.getString(cursor.getColumnIndex(CustomDocumentColumn.COLUMN_MIME_TYPE));
                String avalibleBytes = cursor.getString(cursor.getColumnIndex(CustomDocumentColumn.COLUMN_SIZE));
                boolean isDirectory = cursor.getInt(cursor.getColumnIndex(CustomDocumentColumn.COLUMN_IS_DIRECTORY)) > 0;
                fileSystemDirectories.add(new FileInfo(title, documentId, avalibleBytes, mimeTypes, isDirectory));
            }
        }
        return fileSystemDirectories;
    }
}

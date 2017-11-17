package com.neuron64.ftp.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.neuron64.ftp.data.content_provider.DocumentsContract;
import com.neuron64.ftp.data.content_provider.DocumentsContract.*;
import com.neuron64.ftp.data.content_provider.ExternalStorageProvider;
import com.neuron64.ftp.data.exception.ErrorDeleteDocument;
import com.neuron64.ftp.data.exception.ErrorThisIsRootDirectory;
import com.neuron64.ftp.domain.interactor.CompletableUseCase;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.repository.FileSystemRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;

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
        previousFolder.add(ExternalStorageProvider.ROOT_ID_PRIMARY_EMULATED + ":");
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

    @Override
    public Completable deleteFile(String documentId) {
        return Completable.fromAction(() -> {
            boolean isDeleted = DocumentsContract.deleteDocument(context.getContentResolver(), deriveFields(documentId));
            if(!isDeleted){
                throw new ErrorDeleteDocument();
            }
        });
    }

    @Override
    public Completable renameConnection(@NonNull String idDocument, @NonNull String newName) {
        return Completable.fromAction(() ->
                DocumentsContract.renameDocument(context.getContentResolver(), deriveFields(idDocument), newName));
    }

    @Override
    public Completable moveDocument(String idDocument, String idFolder) {
        return Completable.fromAction(() -> {
            Uri nowDoc = deriveFields(idDocument);
            Uri parentFolder = deriveFields(previousFolder.get(previousFolder.size() - 1));
            String idTargetFolder = "primary:DCIM";
            Uri targetFolder = deriveFields(idTargetFolder);
            Uri newParent = DocumentsContract.moveDocument(context.getContentResolver(), nowDoc, parentFolder, targetFolder);
        });
    }

    private Uri deriveFields(String documentId) {
        return DocumentsContract.buildDocumentUri(ExternalStorageProvider.AUTHORITY, documentId);
    }

    private List<FileInfo> getDirectoryFiles(Uri contentsUri){
        List<FileInfo> fileSystemDirectories = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(contentsUri, null, null, null, null);

        if(cursor != null && cursor.moveToFirst()){
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(Document.COLUMN_DISPLAY_NAME));
                String documentId = cursor.getString(cursor.getColumnIndex(Document.COLUMN_DOCUMENT_ID));
                String mimeTypes = cursor.getString(cursor.getColumnIndex(Document.COLUMN_MIME_TYPE));
                long avalibleBytes = cursor.getLong(cursor.getColumnIndex(Document.COLUMN_SIZE));
                boolean isDirectory = cursor.getInt(cursor.getColumnIndex(Document.COLUMN_IS_DIRECTORY)) > 0;
                String pathFile = cursor.getString(cursor.getColumnIndex(Document.COLUMN_PATH_FILE));
                Date dateLastModification = new Date(cursor.getLong(cursor.getColumnIndex(Document.COLUMN_LAST_MODIFIED)));
                fileSystemDirectories.add(new FileInfo(title, documentId, avalibleBytes, mimeTypes, isDirectory, dateLastModification, pathFile));
            }
        }
        return fileSystemDirectories;
    }
}

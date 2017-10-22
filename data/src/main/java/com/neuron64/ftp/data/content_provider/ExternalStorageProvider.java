package com.neuron64.ftp.data.content_provider;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.provider.DocumentsContract.Document;
import android.provider.DocumentsContract.Root;
import android.util.ArrayMap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by yks-11 on 10/5/17.
 */

public class ExternalStorageProvider extends StorageProvider {

    private static final String TAG = "ExternalStorageProvider";

    public static String AUTHORITY = ApplicationInfo.APPLICATION_ID + ".externalstorage.documents";

    public static final String[] DEFAULT_ROOT_PROJECTION = {
            Root.COLUMN_ROOT_ID, Root.COLUMN_FLAGS, Root.COLUMN_ICON, Root.COLUMN_TITLE,
            Root.COLUMN_DOCUMENT_ID, Root.COLUMN_AVAILABLE_BYTES,
    };

    private static final String[] DEFAULT_DOCUMENT_PROJECTION = new String[] {
            CustomDocumentColumn.COLUMN_DOCUMENT_ID, CustomDocumentColumn.COLUMN_MIME_TYPE, CustomDocumentColumn.COLUMN_DISPLAY_NAME,
            CustomDocumentColumn.COLUMN_LAST_MODIFIED, CustomDocumentColumn.COLUMN_FLAGS, CustomDocumentColumn.COLUMN_SIZE, CustomDocumentColumn.COLUMN_SUMMARY,
            CustomDocumentColumn.COLUMN_IS_DIRECTORY, CustomDocumentColumn.COLUMN_PATH_FILE
    };

    private ArrayMap<String, VolumeInfo> roots = new ArrayMap<>();

    @Override
    public boolean onCreate() {
        updateRoots();
        return true;
    }

    @Override
    public void updateRoots() {
        updateVolumesLocked();
    }

    private void updateVolumesLocked() {
        StorageManager storageManager = (StorageManager) getContext().getSystemService(Context.STORAGE_SERVICE);
        List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();

        StorageVolumeUtils storageUtils = new StorageVolumeUtils();

        for (StorageVolume storageVolume : storageVolumes) {
            VolumeInfo volumeInfo = storageUtils.parseStorageVolume(storageVolume);
            roots.put(volumeInfo.getId(), volumeInfo);
        }
    }

    @Override
    public Cursor queryRoots(String[] projection) throws FileNotFoundException {
        final MatrixCursor result = new MatrixCursor(resolveRootProjection(projection));
        for (VolumeInfo volumeInfo : roots.values()) {
            final MatrixCursor.RowBuilder row = result.newRow();

            row.add(Root.COLUMN_ROOT_ID, volumeInfo.getId());
            row.add(Root.COLUMN_TITLE, volumeInfo.getDescription());
            row.add(Root.COLUMN_DOCUMENT_ID, volumeInfo.getId());
            row.add(Root.COLUMN_AVAILABLE_BYTES, volumeInfo.getPath().getFreeSpace());
            row.add(Root.COLUMN_MIME_TYPES, volumeInfo.getPath().getFreeSpace());
        }

        return result;
    }

    @Override
    public Cursor queryDocument(String documentId, String[] projection) throws FileNotFoundException {
        Log.d(TAG, "queryDocument: ");
        return null;
    }

    @Override
    public Cursor queryChildDocuments(String parentDocumentId, String[] projection, String sortOrder) throws FileNotFoundException {
        final File parent = getFileForDocId(parentDocumentId);
        final MatrixCursor result = new MatrixCursor(resolveDocumentProjection(projection));

        for (File file : parent.listFiles()) {
            includeFile(result, null, file);
        }
        return result;
    }

    private File getFileForDocId(String docId) throws FileNotFoundException {
        final int splitIndex = docId.indexOf(':', 1);
        final String tag = docId.substring(0, splitIndex);
        final String path = docId.substring(splitIndex + 1);

        VolumeInfo root;

        root = roots.get(tag);

        if (root == null) {
            throw new FileNotFoundException("No root for " + tag);
        }

        File target = root.getPath();
        if (target == null) {
            return null;
        }
        if (!target.exists()) {
            target.mkdirs();
        }
        target = new File(target, path);
        if (!target.exists()) {
            throw new FileNotFoundException("Missing file for " + docId + " at " + target);
        }
        return target;
    }

    private String getDocIdForFileMaybeCreate(File file, boolean createNewDir)
            throws FileNotFoundException {
        String path = file.getAbsolutePath();

        // Find the most-specific root path
        String mostSpecificId = null;
        String mostSpecificPath = null;
        for (int i = 0; i < roots.size(); i++) {
            final String rootId = roots.keyAt(i);
            final String rootPath = roots.valueAt(i).getPath().getAbsolutePath();
            if (path.startsWith(rootPath) && (mostSpecificPath == null
                    || rootPath.length() > mostSpecificPath.length())) {
                mostSpecificId = rootId;
                mostSpecificPath = rootPath;
            }
        }

        if (mostSpecificPath == null) {
            throw new FileNotFoundException("Failed to find root that contains " + path);
        }

        // Start at first char of path under root
        final String rootPath = mostSpecificPath;
        if (rootPath.equals(path)) {
            path = "";
        } else if (rootPath.endsWith("/")) {
            path = path.substring(rootPath.length());
        } else {
            path = path.substring(rootPath.length() + 1);
        }

        if (!file.exists() && createNewDir) {
            Log.i(TAG, "Creating new directory " + file);
            if (!file.mkdir()) {
                Log.e(TAG, "Could not create directory " + file);
            }
        }

        return mostSpecificId + ':' + path;
    }

    private String getDocIdForFile(File file) throws FileNotFoundException {
        return getDocIdForFileMaybeCreate(file, false);
    }

    @Override
    public ParcelFileDescriptor openDocument(String documentId, String mode, CancellationSignal signal) throws FileNotFoundException {
        Log.d(TAG, "openDocument: ");
        return null;
    }

    private static String[] resolveRootProjection(String[] projection) {
        return projection != null ? projection : DEFAULT_ROOT_PROJECTION;
    }

    private static String[] resolveDocumentProjection(String[] projection) {
        return projection != null ? projection : DEFAULT_DOCUMENT_PROJECTION;
    }

    private void includeFile(MatrixCursor result, String docId, File file)
            throws FileNotFoundException {
        if (docId == null) {
            docId = getDocIdForFile(file);
        } else {
            file = getFileForDocId(docId);
        }

        int flags = 0;

        final MatrixCursor.RowBuilder row = result.newRow();
        row.add(CustomDocumentColumn.COLUMN_DOCUMENT_ID, docId);
        row.add(CustomDocumentColumn.COLUMN_DISPLAY_NAME, file.getName());
        row.add(CustomDocumentColumn.COLUMN_SIZE, file.length());
        row.add(CustomDocumentColumn.COLUMN_MIME_TYPE, null);
        row.add(CustomDocumentColumn.COLUMN_FLAGS, flags);
        row.add(CustomDocumentColumn.COLUMN_LAST_MODIFIED, file.lastModified());
        row.add(CustomDocumentColumn.COLUMN_IS_DIRECTORY, file.isDirectory() ? 1 : 0);
        row.add(CustomDocumentColumn.COLUMN_PATH_FILE, file.getAbsoluteFile());
    }
}

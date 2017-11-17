/*
 *  Copyright (C) 2013 The Android Open Source Project
 *  Copyright (C) 2017 Vladimir Korolev
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
*/

package com.neuron64.ftp.data.content_provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.neuron64.ftp.data.content_provider.DocumentsContract.*;
import com.neuron64.ftp.data.util.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import client.ftp.neuron64.com.data.R;

public class ExternalStorageProvider extends StorageProvider {

    private static final String TAG = "ExternalStorageProvider";

    public static final String ROOT_ID_PRIMARY_EMULATED = "primary";
    public static final String ROOT_ID_HOME = "home";
    public static final String ROOT_ID_SECONDARY = "secondary";

    public static String AUTHORITY = ApplicationInfo.APPLICATION_ID + ".externalstorage.documents";

    private static final Uri BASE_URI =
            new Uri.Builder().scheme(ContentResolver.SCHEME_CONTENT).authority(AUTHORITY).build();


    public static final String[] DEFAULT_ROOT_PROJECTION = {
            Root.COLUMN_ROOT_ID, Root.COLUMN_FLAGS, Root.COLUMN_ICON, Root.COLUMN_TITLE,
            Root.COLUMN_DOCUMENT_ID, Root.COLUMN_AVAILABLE_BYTES,
    };

    private static final String[] DEFAULT_DOCUMENT_PROJECTION = new String[] {
            Document.COLUMN_DOCUMENT_ID, Document.COLUMN_MIME_TYPE, Document.COLUMN_DISPLAY_NAME,
            Document.COLUMN_LAST_MODIFIED, Document.COLUMN_FLAGS, Document.COLUMN_SIZE, Document.COLUMN_SUMMARY,
            Document.COLUMN_IS_DIRECTORY, Document.COLUMN_PATH_FILE
    };

    private ArrayMap<String, RootInfo> mRoots = new ArrayMap<>();

    private final Object mRootsLock = new Object();

    private DocumentArchiveHelper mArchiveHelper;

    private Handler mHandler;

    private StorageManager mStorageManager;

    private static class RootInfo {
        public String rootId;
        public int flags;
        public String title;
        public String docId;
        public File visiblePath;
        public File path;
        public boolean reportAvailableBytes = true;
    }

    @Override
    public boolean onCreate() {
        mStorageManager = (StorageManager) getContext().getSystemService(Context.STORAGE_SERVICE);
        mHandler = new Handler();
        mArchiveHelper = new DocumentArchiveHelper(this, (char) 0);
        updateVolumes();
        return true;
    }

    @Override
    public void updateRoots() {
        updateVolumesLocked();
    }

    private void updateVolumes(){
        synchronized (mRootsLock) {
            updateVolumesLocked();
        }
    }

    //TODO: Support <23 version API
//    private void updateVolumesLocked() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        StorageManager storageManager = (StorageManager) getContext().getSystemService(Context.STORAGE_SERVICE);
//        Method method = StorageManager.class.getDeclaredMethod("getVolumeList");
//        method.setAccessible(true);
//        final List<StorageVolume> storageVolumes = (List<StorageVolume>) method.invoke(storageManager);
//
//        StorageVolumeUtils storageUtils = new StorageVolumeUtils();
//
//        for (StorageVolume storageVolume : storageVolumes) {
//            VolumeInfo volumeInfo = storageUtils.getStorageMounts(storageVolume);
//            mRoots.put(volumeInfo.getId(), volumeInfo);
//        }
//    }

    private void updateVolumesLocked()  {
        mRoots.clear();

        VolumeInfo primaryVolume = null;
        UtilsStorageManager utilsStorageManager = new UtilsStorageManager();

        final int userId = utilsStorageManager.getHandleUesrId();

        final List<VolumeInfo> volumes =  utilsStorageManager.getVolumes(mStorageManager);

        for (VolumeInfo volume : volumes) {
            if (!volume.isMountedReadable()) continue;
            final String rootId;
            final String title;
            if (volume.getType() == VolumeInfo.TYPE_EMULATED) {

                // We currently only support a single emulated volume mounted at
                // a time, and it's always considered the primary
                rootId = ROOT_ID_PRIMARY_EMULATED;

                if (VolumeInfo.ID_EMULATED_INTERNAL.equals(volume.getId())) {
                    // This is basically the user's primary device storage.
                    // Use device name for the volume since this is likely same thing
                    // the user sees when they mount their phone on another device.
                    String deviceName = Settings.Global.getString(
                            getContext().getContentResolver(), Settings.Global.DEVICE_NAME);
                    // Device name should always be set. In case it isn't, though,
                    // fall back to a localized "Internal Storage" string.
                    title = !TextUtils.isEmpty(deviceName)
                            ? deviceName
                            : getContext().getString(R.string.root_internal_storage);
                } else {
                    // This should cover all other storage devices, like an SD card
                    // or USB OTG drive plugged in. Using getBestVolumeDescription()
                    // will give us a nice string like "Samsung SD card" or "SanDisk USB drive"
                    final VolumeInfo privateVol = utilsStorageManager.findPrivateForEmulated(mStorageManager, volume);
                    title = utilsStorageManager.getBestVolumeDescription(mStorageManager, privateVol);
                }
            } else if (volume.getType() == VolumeInfo.TYPE_PUBLIC
                    && volume.getMountUserId() == userId) {
                rootId = volume.getFsUuid();
                title = utilsStorageManager.getBestVolumeDescription(mStorageManager, volume);
            } else {
                // Unsupported volume; ignore
                continue;
            }
            if (TextUtils.isEmpty(rootId)) {
                Log.d(TAG, "Missing UUID for " + volume.getId() + "; skipping");
                continue;
            }
            if (mRoots.containsKey(rootId)) {
                Log.w(TAG, "Duplicate UUID " + rootId + " for " + volume.getId() + "; skipping");
                continue;
            }
            final RootInfo root = new RootInfo();
            mRoots.put(rootId, root);
            root.rootId = rootId;
            root.flags = Root.FLAG_LOCAL_ONLY
                    | Root.FLAG_SUPPORTS_SEARCH | Root.FLAG_SUPPORTS_IS_CHILD;
            final DiskInfo disk = volume.getDisk();

            if (disk != null && disk.isSd()) {
                root.flags |= Root.FLAG_REMOVABLE_SD;
            } else if (disk != null && disk.isUsb()) {
                root.flags |= Root.FLAG_REMOVABLE_USB;
            }
            if (volume.isPrimary()) {
                // save off the primary volume for subsequent "Home" dir initialization.
                primaryVolume = volume;
                root.flags |= Root.FLAG_ADVANCED;
            }
            // Dunno when this would NOT be the case, but never hurts to be correct.
            if (volume.isMountedWritable()) {
                root.flags |= Root.FLAG_SUPPORTS_CREATE;
            }
            root.title = title;
            if (volume.getType() == VolumeInfo.TYPE_PUBLIC) {
                root.flags |= Root.FLAG_HAS_SETTINGS;
            }
            if (volume.isVisibleForRead(userId)) {
                root.visiblePath = volume.getPathForUser(userId);
            } else {
                root.visiblePath = null;
            }
            root.path = volume.getInternalPathForUser(userId);
            try {
                root.docId = getDocIdForFile(root.path);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        // Finally, if primary storage is available we add the "Documents" directory.
        // If I recall correctly the actual directory is created on demand
        // by calling either getPathForUser, or getInternalPathForUser.
        if (primaryVolume != null && primaryVolume.isVisible()) {
            final RootInfo root = new RootInfo();
            root.rootId = ROOT_ID_HOME;
            mRoots.put(root.rootId, root);
            root.title = getContext().getString(R.string.root_documents);
            // Only report bytes on *volumes*...as a matter of policy.
            root.reportAvailableBytes = false;
            root.flags = Root.FLAG_LOCAL_ONLY | Root.FLAG_SUPPORTS_SEARCH
                    | Root.FLAG_SUPPORTS_IS_CHILD;
            // Dunno when this would NOT be the case, but never hurts to be correct.
            if (primaryVolume.isMountedWritable()) {
                root.flags |= Root.FLAG_SUPPORTS_CREATE;
            }
            // Create the "Documents" directory on disk (don't use the localized title).
            root.visiblePath = new File(
                    primaryVolume.getPathForUser(userId), Environment.DIRECTORY_DOCUMENTS);
            root.path = new File(
                    primaryVolume.getInternalPathForUser(userId), Environment.DIRECTORY_DOCUMENTS);
            try {
                root.docId = getDocIdForFile(root.path);
            } catch (FileNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }

        Log.d(TAG, "After updating volumes, found " + mRoots.size() + " active roots");

        // Note this affects content://com.android.externalstorage.documents/root/39BD-07C5
        // as well as content://com.android.externalstorage.documents/document/*/children,
        // so just notify on content://com.android.externalstorage.documents/.
        getContext().getContentResolver().notifyChange(BASE_URI, null, false);
    }

    @Override
        public Cursor queryRoots(String[] projection) throws FileNotFoundException {
            final MatrixCursor result = new MatrixCursor(resolveRootProjection(projection));
            synchronized (mRootsLock) {
                for (RootInfo root : mRoots.values()) {
                    final MatrixCursor.RowBuilder row = result.newRow();
                    row.add(Root.COLUMN_ROOT_ID, root.rootId);
                    row.add(Root.COLUMN_FLAGS, root.flags);
                    row.add(Root.COLUMN_TITLE, root.title);
                    row.add(Root.COLUMN_DOCUMENT_ID, root.docId);
                    row.add(Root.COLUMN_AVAILABLE_BYTES,
                            root.reportAvailableBytes ? root.path.getFreeSpace() : -1);
                }
            }

            return result;
    }

    @Override
    public String renameDocument(String docId, String displayName) throws FileNotFoundException {
        // Since this provider treats renames as generating a completely new
        // docId, we're okay with letting the MIME type change.
        displayName = FileUtils.buildValidFatFilename(displayName);
        final File before = getFileForDocId(docId);
        final File after = FileUtils.buildUniqueFile(before.getParentFile(), displayName);
        if (!before.renameTo(after)) {
            throw new IllegalStateException("Failed to rename to " + after);
        }
        final String afterDocId = getDocIdForFile(after);
        if (!TextUtils.equals(docId, afterDocId)) {
            return afterDocId;
        } else {
            return null;
        }
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

    @Override
    public void deleteDocument(String documentId) throws FileNotFoundException {
        final File file = getFileForDocId(documentId);
        final boolean isDirectory = file.isDirectory();

        if (isDirectory) {
            FileUtils.deleteContents(file);
        }

        if (!file.delete()) {
            throw new IllegalStateException("Failed to delete " + file);
        }

        final ContentResolver resolver = getContext().getContentResolver();
        final Uri externalUri = MediaStore.Files.getContentUri("external");

        // Remove media store entries for any files inside this directory, using
        // path prefix match. Logic borrowed from MtpDatabase.
        if (isDirectory) {
            final String path = file.getAbsolutePath() + "/";
            resolver.delete(externalUri,
                    "_data LIKE ?1 AND lower(substr(_data,1,?2))=lower(?3)",
                    new String[] { path + "%", Integer.toString(path.length()), path });
        }

        // Remove media store entry for this exact file.
        final String path = file.getAbsolutePath();
        resolver.delete(externalUri,
                "_data LIKE ?1 AND lower(_data)=lower(?2)",
                new String[] { path, path });

        notifyDocumentsChanged(documentId);
    }

    @Override
    public String moveDocument(String sourceDocumentId, String sourceParentDocumentId,
                               String targetParentDocumentId)
            throws FileNotFoundException {

        final File before = getFileForDocId(sourceDocumentId);
        final File after = new File(getFileForDocId(targetParentDocumentId), before.getName());

        if (after.exists()) {
            throw new IllegalStateException("Already exists " + after);
        }

        if (!before.renameTo(after)) {
            throw new IllegalStateException("Failed to move to " + after);
        }

        return getDocIdForFile(after);
    }

    @Override
    public Cursor querySearchDocuments(String rootId, String query, String[] projection)
            throws FileNotFoundException {
        final MatrixCursor result = new MatrixCursor(resolveDocumentProjection(projection));
        query = query.toLowerCase();
        final File parent;
        synchronized (mRootsLock) {
            parent = mRoots.get(rootId).path;
        }
        final LinkedList<File> pending = new LinkedList<File>();
        pending.add(parent);
        while (!pending.isEmpty() && result.getCount() < 24) {
            final File file = pending.removeFirst();
            if (file.isDirectory()) {
                pending.addAll(Arrays.asList(file.listFiles()));
            }
            if (file.getName().toLowerCase().contains(query)) {
                includeFile(result, null, file);
            }
        }
        return result;
    }

    private File getFileForDocId(String docId) throws FileNotFoundException {
        return getFileForDocId(docId, false);
    }

    private File getFileForDocId(String docId, boolean visible) throws FileNotFoundException {
        final int splitIndex = docId.indexOf(':', 1);
        final String tag = docId.substring(0, splitIndex);
        final String path = docId.substring(splitIndex + 1);
        RootInfo root;
        synchronized (mRootsLock) {
            root = mRoots.get(tag);
        }
        if (root == null) {
            throw new FileNotFoundException("No root for " + tag);
        }
        File target = visible ? root.visiblePath : root.path;
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
        synchronized (mRootsLock) {
            for (int i = 0; i < mRoots.size(); i++) {
                final String rootId = mRoots.keyAt(i);
                final String rootPath = mRoots.valueAt(i).path.getAbsolutePath();
                if (path.startsWith(rootPath) && (mostSpecificPath == null
                        || rootPath.length() > mostSpecificPath.length())) {
                    mostSpecificId = rootId;
                    mostSpecificPath = rootPath;
                }
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
        if (file.canWrite()) {
            if (file.isDirectory()) {
                flags |= Document.FLAG_DIR_SUPPORTS_CREATE;
                flags |= Document.FLAG_SUPPORTS_DELETE;
                flags |= Document.FLAG_SUPPORTS_RENAME;
                flags |= Document.FLAG_SUPPORTS_MOVE;
            } else {
                flags |= Document.FLAG_SUPPORTS_WRITE;
                flags |= Document.FLAG_SUPPORTS_DELETE;
                flags |= Document.FLAG_SUPPORTS_RENAME;
                flags |= Document.FLAG_SUPPORTS_MOVE;
            }
        }
        final String mimeType = getTypeForFile(file);
        if (mArchiveHelper.isSupportedArchiveType(mimeType)) {
            flags |= Document.FLAG_ARCHIVE;
        }
        final String displayName = file.getName();
        if (mimeType.startsWith("image/")) {
            flags |= Document.FLAG_SUPPORTS_THUMBNAIL;
        }
        final MatrixCursor.RowBuilder row = result.newRow();
        row.add(Document.COLUMN_DOCUMENT_ID, docId);
        row.add(Document.COLUMN_DISPLAY_NAME, displayName);
        row.add(Document.COLUMN_SIZE, file.length());
        row.add(Document.COLUMN_MIME_TYPE, mimeType);
        row.add(Document.COLUMN_FLAGS, flags);
        row.add(Document.COLUMN_IS_DIRECTORY, file.isDirectory() ? 1 : 0);
        row.add(DocumentArchiveHelper.COLUMN_LOCAL_FILE_PATH, file.getPath());
        // Only publish dates reasonably after epoch
        long lastModified = file.lastModified();
        if (lastModified > 31536000000L) {
            row.add(Document.COLUMN_LAST_MODIFIED, lastModified);
        }
    }

    private static String getTypeForFile(File file) {
        if (file.isDirectory()) {
            return Document.MIME_TYPE_DIR;
        } else {
            return getTypeForName(file.getName());
        }
    }

    private static String getTypeForName(String name) {
        final int lastDot = name.lastIndexOf('.');
        if (lastDot >= 0) {
            final String extension = name.substring(lastDot + 1).toLowerCase();
            final String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            if (mime != null) {
                return mime;
            }
        }
        return "application/octet-stream";
    }

    //TODO: Check notify
    private void notifyDocumentsChanged(String docId){
        getContext().getContentResolver().notifyChange(BASE_URI, null, false);
    }
}

package com.neuron64.ftp.client.demo.docprovider;

import android.os.UserHandle;
import android.os.storage.StorageVolume;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by yks-11 on 10/5/17.
 */

public class StorageVolumeUtils {

    private static final String TAG = "StorageVolumeUtils";

    public StorageVolumeUtils(){/*Empty*/}

    public VolumeInfo parseStorageVolume(StorageVolume storageVolume){
        String id = getString("mId", storageVolume);
        Integer storageId = getInteger("mStorageId", storageVolume);
        File path = getFile("mPath", storageVolume);
        String description = getString("mDescription", storageVolume);
        boolean primary = getBoolean("mPrimary", storageVolume);
        boolean removable = getBoolean("mRemovable", storageVolume);
        boolean emulated = getBoolean("mEmulated", storageVolume);
        long mtpReserveSize = getLong("mMtpReserveSize", storageVolume);
        boolean allowMassStorage = getBoolean("mAllowMassStorage", storageVolume);
        long maxFileSize = getLong("mMaxFileSize", storageVolume);
        String fsUuid = getString("mFsUuid", storageVolume);
        String state = getString("mState", storageVolume);

        return new VolumeInfo(id, storageId, path, description, primary, removable, emulated, mtpReserveSize, allowMassStorage, maxFileSize, fsUuid, state);
    }

    private long getLong(String fieldName, StorageVolume storageVolume)  {
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            return (long) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return 0;
    }

    private boolean getBoolean(String fieldName, StorageVolume storageVolume)  {
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            return (boolean) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return false;
    }

    private File getFile(String fieldName, StorageVolume storageVolume)  {
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            return (File) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return null;
    }

    private int getInteger(String fieldName, StorageVolume storageVolume){
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            return (int) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return 0;
    }

    private String getString(String fieldName, StorageVolume storageVolume)  {
        try {
            Field field = getField(fieldName);
            field.setAccessible(true);
            return (String) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return null;
    }

    private Field getField(String fieldName) throws NoSuchFieldException {
        return StorageVolume.class.getDeclaredField(fieldName);
    }
}

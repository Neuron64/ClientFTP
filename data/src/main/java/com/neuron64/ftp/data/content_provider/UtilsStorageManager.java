/*
 * Copyright (C) 2014 Vladimir Korolev
 * Copyright (C) 2014 Hari Krishna Dulipudi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neuron64.ftp.data.content_provider;

import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Neuron on 29.10.2017.
 */

public class UtilsStorageManager {

    private static final String TAG = "UtilsStorageManager";

    public List<VolumeInfo> getVolumes(StorageManager storageManager) {
        List<VolumeInfo> volumeInfos = new ArrayList<>();
        try {
            Method method = StorageManager.class.getDeclaredMethod("getVolumes");
            method.setAccessible(true);
            List<Object> invokes = (List<Object>) method.invoke(storageManager);

            for (Object invoke : invokes) {
                String id = getString("id", invoke);
                int type = getInteger("type", invoke);
                DiskInfo disk = getDiskInfo(invoke);
                String partGuid = getString("id", invoke);
                String path = getString("path", invoke);
                int mountUserId = getInteger("mountUserId", invoke);
                int state = getInteger("state", invoke);
                String fsType = getString("fsType", invoke);
                int mountFlags = getInteger("mountFlags", invoke);
                String fsUuid = getString("fsUuid", invoke);
                String fsLabel = getString("fsLabel", invoke);
                String internalPath = getString("internalPath", invoke);

                VolumeInfo volumeInfo = new VolumeInfo(id, type, disk, partGuid);
                volumeInfo.mountFlags = mountFlags;
                volumeInfo.fsLabel = fsLabel;
                volumeInfo.path = path;
                volumeInfo.mountUserId = mountUserId;
                volumeInfo.state = state;
                volumeInfo.fsType = fsType;
                volumeInfo.fsUuid = fsUuid;
                volumeInfo.internalPath = internalPath;

                volumeInfos.add(volumeInfo);
            }
        } catch (IllegalAccessException | NoSuchMethodException| InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return volumeInfos;
    }

    private DiskInfo getDiskInfo(Object object) {
        String path = "";
        DiskInfo diskInfo = null;

        try {
            Field mPath = object.getClass().getDeclaredField("disk");
            mPath.setAccessible(true);
            Object obj = mPath.get(object);

            int flags = getInteger("flags", obj);
            int volumeCount = getInteger("volumeCount", obj);
            String sysPath = getString("sysPath", obj);
            String id = getString("id", obj);
            long size = getLong("size", obj);
            String label = getString("label", obj);

            diskInfo = new DiskInfo(id, flags);
            diskInfo.size = size;
            diskInfo.label = label;
            diskInfo.volumeCount = volumeCount;
            diskInfo.sysPath = sysPath;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return diskInfo;
    }

    public int getHandleUesrId()  {
        Method methodUserHandle = null;
        try {
            methodUserHandle = UserHandle.class.getDeclaredMethod("myUserId");
            methodUserHandle.setAccessible(true);
            return (int) methodUserHandle.invoke(UserHandle.class);
        } catch (IllegalAccessException | NoSuchMethodException| InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public VolumeInfo findPrivateForEmulated(StorageManager storageManager, VolumeInfo volume) {
        try {
            Method method = StorageManager.class.getDeclaredMethod("findPrivateForEmulated");
            method.setAccessible(true);
            return (VolumeInfo) method.invoke(storageManager, volume);
        } catch (IllegalAccessException | NoSuchMethodException| InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getBestVolumeDescription(StorageManager storageManager, VolumeInfo volume) {
        try {
            Method method = StorageManager.class.getDeclaredMethod("getBestVolumeDescription");
            method.setAccessible(true);
            return (String) method.invoke(storageManager, volume);
        } catch (IllegalAccessException | NoSuchMethodException| InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long getLong(String fieldName, Object storageVolume)  {
        try {
            Field field = getField(fieldName, storageVolume);
            field.setAccessible(true);
            return (long) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return 0;
    }

    private boolean getBoolean(String fieldName, Object storageVolume)  {
        try {
            Field field = getField(fieldName, storageVolume);
            field.setAccessible(true);
            return (boolean) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return false;
    }

    private File getFile(String fieldName, Object storageVolume)  {
        try {
            Field field = getField(fieldName, storageVolume);
            field.setAccessible(true);
            return (File) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return null;
    }

    private int getInteger(String fieldName, Object storageVolume){
        try {
            Field field = getField(fieldName, storageVolume);
            field.setAccessible(true);
            return (int) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return 0;
    }

    private String getString(String fieldName, Object storageVolume)  {
        try {
            Field field = getField(fieldName, storageVolume);
            field.setAccessible(true);
            return (String) field.get(storageVolume);
        } catch (Exception e) {
            Log.e(TAG, "getString: ", e);
        }
        return null;
    }

    private Field getField(String fieldName, Object obj) throws NoSuchFieldException {
        return obj.getClass().getDeclaredField(fieldName);
    }
}

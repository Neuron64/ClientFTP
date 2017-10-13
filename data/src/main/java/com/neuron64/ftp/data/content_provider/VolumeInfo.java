package com.neuron64.ftp.data.content_provider;

import android.os.UserHandle;

import java.io.File;

/**
 * Created by yks-11 on 10/5/17.
 */

public class VolumeInfo {

    private String id;
    private int storageId;
    private File path;
    private String description;
    private boolean primary;
    private boolean removable;
    private boolean emulated;
    private long mtpReserveSize;
    private boolean allowMassStorage;
    private long maxFileSize;
    private String fsUuid;
    private String mSstate;

    public VolumeInfo(String id, int storageId, File path, String description, boolean primary, boolean removable, boolean emulated, long mtpReserveSize, boolean allowMassStorage, long maxFileSize, String fsUuid, String mSstate) {
        this.id = id;
        this.storageId = storageId;
        this.path = path;
        this.description = description;
        this.primary = primary;
        this.removable = removable;
        this.emulated = emulated;
        this.mtpReserveSize = mtpReserveSize;
        this.allowMassStorage = allowMassStorage;
        this.maxFileSize = maxFileSize;
        this.fsUuid = fsUuid;
        this.mSstate = mSstate;
    }

    public VolumeInfo(){/*Empty*/}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStorageId() {
        return storageId;
    }

    public void setStorageId(int storageId) {
        this.storageId = storageId;
    }

    public File getPath() {
        return path;
    }

    public void setPath(File path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public boolean isRemovable() {
        return removable;
    }

    public void setRemovable(boolean removable) {
        this.removable = removable;
    }

    public boolean isEmulated() {
        return emulated;
    }

    public void setEmulated(boolean emulated) {
        this.emulated = emulated;
    }

    public long getMtpReserveSize() {
        return mtpReserveSize;
    }

    public void setMtpReserveSize(long mtpReserveSize) {
        this.mtpReserveSize = mtpReserveSize;
    }

    public boolean isAllowMassStorage() {
        return allowMassStorage;
    }

    public void setAllowMassStorage(boolean allowMassStorage) {
        this.allowMassStorage = allowMassStorage;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }


    public String getFsUuid() {
        return fsUuid;
    }

    public void setFsUuid(String fsUuid) {
        this.fsUuid = fsUuid;
    }

    public String getmSstate() {
        return mSstate;
    }

    public void setmSstate(String mSstate) {
        this.mSstate = mSstate;
    }

    @Override
    public String toString() {
        return "VolumeInfo{" +
                "id='" + id + '\'' +
                ", storageId=" + storageId +
                ", path=" + path +
                ", description='" + description + '\'' +
                ", primary=" + primary +
                ", removable=" + removable +
                ", emulated=" + emulated +
                ", mtpReserveSize=" + mtpReserveSize +
                ", allowMassStorage=" + allowMassStorage +
                ", maxFileSize=" + maxFileSize +
                ", fsUuid='" + fsUuid + '\'' +
                ", mSstate='" + mSstate + '\'' +
                '}';
    }
}

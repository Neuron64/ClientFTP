/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.content.Intent;
import android.content.res.Resources;
import android.os.Environment;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.io.File;
import java.util.Comparator;

import client.ftp.neuron64.com.data.R;

/**
 * Information about a storage volume that may be mounted. A volume may be a
 * partition on a physical {@link DiskInfo}, an emulated volume above some other
 * storage medium, or a standalone container like an ASEC or OBB.
 * <p>
 * Volumes may be mounted with various flags:
 * <ul>
 * <li>{@link #MOUNT_FLAG_PRIMARY} means the volume provides primary external
 * storage, historically found at {@code /sdcard}.
 * <li>{@link #MOUNT_FLAG_VISIBLE} means the volume is visible to third-party
 * apps for direct filesystem access. The system should send out relevant
 * storage broadcasts and index any media on visible volumes. Visible volumes
 * are considered a more stable part of the device, which is why we take the
 * time to index them. In particular, transient volumes like USB OTG devices
 * <em>should not</em> be marked as visible; their contents should be surfaced
 * to apps through the Storage Access Framework.
 * </ul>
 *
 */
public class VolumeInfo {

    public static final String ACTION_VOLUME_STATE_CHANGED =
            "android.os.storage.action.VOLUME_STATE_CHANGED";
    public static final String EXTRA_VOLUME_ID =
            "android.os.storage.extra.VOLUME_ID";
    public static final String EXTRA_VOLUME_STATE =
            "android.os.storage.extra.VOLUME_STATE";

    /** Stub volume representing internal private storage */
    public static final String ID_PRIVATE_INTERNAL = "private";

    /** Real volume representing internal emulated storage */
    public static final String ID_EMULATED_INTERNAL = "emulated";

    public static final int TYPE_PUBLIC = 0;
    public static final int TYPE_PRIVATE = 1;
    public static final int TYPE_EMULATED = 2;
    public static final int TYPE_ASEC = 3;
    public static final int TYPE_OBB = 4;
    public static final int STATE_UNMOUNTED = 0;
    public static final int STATE_CHECKING = 1;
    public static final int STATE_MOUNTED = 2;
    public static final int STATE_MOUNTED_READ_ONLY = 3;
    public static final int STATE_FORMATTING = 4;
    public static final int STATE_EJECTING = 5;
    public static final int STATE_UNMOUNTABLE = 6;
    public static final int STATE_REMOVED = 7;
    public static final int STATE_BAD_REMOVAL = 8;
    public static final int MOUNT_FLAG_PRIMARY = 1 << 0;
    public static final int MOUNT_FLAG_VISIBLE = 1 << 1;

    private static SparseArray<String> sStateToEnvironment = new SparseArray<>();

    private static ArrayMap<String, String> sEnvironmentToBroadcast = new ArrayMap<>();

    private static SparseIntArray sStateToDescrip = new SparseIntArray();

    private static final Comparator<VolumeInfo>
            sDescriptionComparator = new Comparator<VolumeInfo>() {

        @Override
        public int compare(VolumeInfo lhs, VolumeInfo rhs) {
            if (VolumeInfo.ID_PRIVATE_INTERNAL.equals(lhs.getId())) {
                return -1;
            } else if (lhs.getDescription() == null) {
                return 1;
            } else if (rhs.getDescription() == null) {
                return -1;
            } else {
                return lhs.getDescription().compareTo(rhs.getDescription());
            }
        }
    };

    static {
        sStateToEnvironment.put(VolumeInfo.STATE_UNMOUNTED, Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(VolumeInfo.STATE_CHECKING, Environment.MEDIA_CHECKING);
        sStateToEnvironment.put(VolumeInfo.STATE_MOUNTED, Environment.MEDIA_MOUNTED);
        sStateToEnvironment.put(VolumeInfo.STATE_MOUNTED_READ_ONLY, Environment.MEDIA_MOUNTED_READ_ONLY);
        sStateToEnvironment.put(VolumeInfo.STATE_FORMATTING, Environment.MEDIA_UNMOUNTED);
        sStateToEnvironment.put(VolumeInfo.STATE_EJECTING, Environment.MEDIA_EJECTING);
        sStateToEnvironment.put(VolumeInfo.STATE_UNMOUNTABLE, Environment.MEDIA_UNMOUNTABLE);
        sStateToEnvironment.put(VolumeInfo.STATE_REMOVED, Environment.MEDIA_REMOVED);
        sStateToEnvironment.put(VolumeInfo.STATE_BAD_REMOVAL, Environment.MEDIA_BAD_REMOVAL);
        sEnvironmentToBroadcast.put(Environment.MEDIA_UNMOUNTED, Intent.ACTION_MEDIA_UNMOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_CHECKING, Intent.ACTION_MEDIA_CHECKING);
        sEnvironmentToBroadcast.put(Environment.MEDIA_MOUNTED, Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_MOUNTED_READ_ONLY, Intent.ACTION_MEDIA_MOUNTED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_EJECTING, Intent.ACTION_MEDIA_EJECT);
        sEnvironmentToBroadcast.put(Environment.MEDIA_UNMOUNTABLE, Intent.ACTION_MEDIA_UNMOUNTABLE);
        sEnvironmentToBroadcast.put(Environment.MEDIA_REMOVED, Intent.ACTION_MEDIA_REMOVED);
        sEnvironmentToBroadcast.put(Environment.MEDIA_BAD_REMOVAL, Intent.ACTION_MEDIA_BAD_REMOVAL);
        sStateToDescrip.put(VolumeInfo.STATE_UNMOUNTED, R.string.ext_media_status_unmounted);
        sStateToDescrip.put(VolumeInfo.STATE_CHECKING, R.string.ext_media_status_checking);
        sStateToDescrip.put(VolumeInfo.STATE_MOUNTED, R.string.ext_media_status_mounted);
        sStateToDescrip.put(VolumeInfo.STATE_MOUNTED_READ_ONLY, R.string.ext_media_status_mounted_ro);
        sStateToDescrip.put(VolumeInfo.STATE_FORMATTING, R.string.ext_media_status_formatting);
        sStateToDescrip.put(VolumeInfo.STATE_EJECTING, R.string.ext_media_status_ejecting);
        sStateToDescrip.put(VolumeInfo.STATE_UNMOUNTABLE, R.string.ext_media_status_unmountable);
        sStateToDescrip.put(VolumeInfo.STATE_REMOVED, R.string.ext_media_status_removed);
        sStateToDescrip.put(VolumeInfo.STATE_BAD_REMOVAL, R.string.ext_media_status_bad_removal);
    }

    /** vold state */
    public final String id;
    public final int type;
    public final DiskInfo disk;
    public final String partGuid;
    public int mountFlags = 0;
    public int mountUserId = -1;
    public int state = STATE_UNMOUNTED;
    public String fsType;
    public String fsUuid;
    public String fsLabel;
    public String path;
    public String internalPath;

    public VolumeInfo(String id, int type, DiskInfo disk, String partGuid) {
        this.id = Preconditions.checkNotNull(id);
        this.type = type;
        this.disk = disk;
        this.partGuid = partGuid;
    }

    public VolumeInfo(Parcel parcel) {
        id = parcel.readString();
        type = parcel.readInt();

        if (parcel.readInt() != 0) {
            disk = DiskInfo.CREATOR.createFromParcel(parcel);
        } else {
            disk = null;
        }

        partGuid = parcel.readString();
        mountFlags = parcel.readInt();
        mountUserId = parcel.readInt();
        state = parcel.readInt();
        fsType = parcel.readString();
        fsUuid = parcel.readString();
        fsLabel = parcel.readString();
        path = parcel.readString();
        internalPath = parcel.readString();
    }

    public static @NonNull String getEnvironmentForState(int state) {
        final String envState = sStateToEnvironment.get(state);
        if (envState != null) {
            return envState;
        } else {
            return Environment.MEDIA_UNKNOWN;
        }
    }

    public static @Nullable String getBroadcastForEnvironment(String envState) {
        return sEnvironmentToBroadcast.get(envState);
    }

    public static @Nullable String getBroadcastForState(int state) {
        return getBroadcastForEnvironment(getEnvironmentForState(state));
    }

    public static @NonNull Comparator<VolumeInfo> getDescriptionComparator() {
        return sDescriptionComparator;
    }

    public @NonNull String getId() {
        return id;
    }

    public @Nullable DiskInfo getDisk() {
        return disk;
    }

    public @Nullable String getDiskId() {
        return (disk != null) ? disk.id : null;
    }

    public int getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    public int getStateDescription() {
        return sStateToDescrip.get(state, 0);
    }

    public @Nullable String getFsUuid() {
        return fsUuid;
    }

    public int getMountUserId() {
        return mountUserId;
    }

    public @Nullable String getDescription() {
        if (ID_PRIVATE_INTERNAL.equals(id) || ID_EMULATED_INTERNAL.equals(id)) {
            return Resources.getSystem().getString(R.string.storage_internal);
        } else if (!TextUtils.isEmpty(fsLabel)) {
            return fsLabel;
        } else {
            return null;
        }
    }

    public boolean isMountedReadable() {
        return state == STATE_MOUNTED || state == STATE_MOUNTED_READ_ONLY;
    }

    public boolean isMountedWritable() {
        return state == STATE_MOUNTED;
    }

    public boolean isPrimary() {
        return (mountFlags & MOUNT_FLAG_PRIMARY) != 0;
    }

    public boolean isPrimaryPhysical() {
        return isPrimary() && (getType() == TYPE_PUBLIC);
    }

    public boolean isVisible() {
        return (mountFlags & MOUNT_FLAG_VISIBLE) != 0;
    }

    public boolean isVisibleForRead(int userId) {
        if (type == TYPE_PUBLIC) {
            if (isPrimary() && mountUserId != userId) {
                // Primary physical is only visible to single user
                return false;
            } else {
                return isVisible();
            }
        } else if (type == TYPE_EMULATED) {
            return isVisible();
        } else {
            return false;
        }
    }

    public boolean isVisibleForWrite(int userId) {
        if (type == TYPE_PUBLIC && mountUserId == userId) {
            return isVisible();
        } else if (type == TYPE_EMULATED) {
            return isVisible();
        } else {
            return false;
        }
    }

    public File getPath() {
        return (path != null) ? new File(path) : null;
    }

    public File getInternalPath() {
        return (internalPath != null) ? new File(internalPath) : null;
    }

    public File getPathForUser(int userId) {
        if (path == null) {
            return null;
        } else if (type == TYPE_PUBLIC) {
            return new File(path);
        } else if (type == TYPE_EMULATED) {
            return new File(path, Integer.toString(userId));
        } else {
            return null;
        }
    }

    /**
     * Path which is accessible to apps holding
     */
    public File getInternalPathForUser(int userId) {
        if (type == TYPE_PUBLIC) {
            // TODO: plumb through cleaner path from vold
            return new File(path.replace("/storage/", "/mnt/media_rw/"));
        } else {
            return getPathForUser(userId);
        }
    }
}
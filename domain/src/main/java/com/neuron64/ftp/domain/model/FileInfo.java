package com.neuron64.ftp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by yks-11 on 10/13/17.
 */

public class FileInfo implements Parcelable{

    private String title;
    private String documentId;
    private long availableBytes;
    private String type;
    private boolean isDirectory;
    private Date dateLastModification;
    private String path;

    public FileInfo(String title, String documentId, long availableBytes, String type, boolean isDirectory, Date dateLastModification, String path) {
        this.title = title;
        this.documentId = documentId;
        this.availableBytes = availableBytes;
        this.type = type;
        this.isDirectory = isDirectory;
        this.dateLastModification = dateLastModification;
        this.path = path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public long getAvailableBytes() {
        return availableBytes;
    }

    public void setAvailableBytes(long availableBytes) {
        this.availableBytes = availableBytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDateLastModification(Date dateLastModification) {
        this.dateLastModification = dateLastModification;
    }

    public Date getDateLastModification() {
        return dateLastModification;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.documentId);
        dest.writeLong(this.availableBytes);
        dest.writeString(this.type);
        dest.writeByte(this.isDirectory ? (byte) 1 : (byte) 0);
        dest.writeLong(this.dateLastModification != null ? this.dateLastModification.getTime() : -1);
        dest.writeString(this.path);
    }

    protected FileInfo(Parcel in) {
        this.title = in.readString();
        this.documentId = in.readString();
        this.availableBytes = in.readLong();
        this.type = in.readString();
        this.isDirectory = in.readByte() != 0;
        long tmpDateLastModification = in.readLong();
        this.dateLastModification = tmpDateLastModification == -1 ? null : new Date(tmpDateLastModification);
        this.path = in.readString();
    }

    public static final Creator<FileInfo> CREATOR = new Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel source) {
            return new FileInfo(source);
        }

        @Override
        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };
}

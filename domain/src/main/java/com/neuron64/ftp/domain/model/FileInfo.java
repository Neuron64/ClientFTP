package com.neuron64.ftp.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yks-11 on 10/13/17.
 */

public class FileInfo implements Parcelable{

    private String title;
    private String documentId;
    private String availableBytes;
    private String type;
    private boolean isDirectory;

    public FileInfo(String title, String documentId, String availableBytes, String type, boolean isDirectory) {
        this.title = title;
        this.documentId = documentId;
        this.availableBytes = availableBytes;
        this.type = type;
        this.isDirectory = isDirectory;
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

    public String getAvailableBytes() {
        return availableBytes;
    }

    public void setAvailableBytes(String availableBytes) {
        this.availableBytes = availableBytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.documentId);
        dest.writeString(this.availableBytes);
        dest.writeString(this.type);
        dest.writeByte(this.isDirectory ? (byte) 1 : (byte) 0);
    }

    protected FileInfo(Parcel in) {
        this.title = in.readString();
        this.documentId = in.readString();
        this.availableBytes = in.readString();
        this.type = in.readString();
        this.isDirectory = in.readByte() != 0;
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

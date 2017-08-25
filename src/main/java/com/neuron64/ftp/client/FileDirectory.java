package com.neuron64.ftp.client;

import org.apache.commons.net.ftp.FTPFile;

import java.util.List;

/**
 * Created by yks-11 on 25.08.17.
 */
public class FileDirectory {

    private FTPFile rootDirectory;

    private List<FileDirectory> fileDirectory;
    private List<FTPFile> ftpFiles;

    public FileDirectory() {

    }

    public FTPFile getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(FTPFile rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public List<FileDirectory> getFileDirectory() {
        return fileDirectory;
    }

    public void setFileDirectory(List<FileDirectory> fileDirectory) {
        this.fileDirectory = fileDirectory;
    }

    public List<FTPFile> getFtpFiles() {
        return ftpFiles;
    }

    public void setFtpFiles(List<FTPFile> ftpFiles) {
        this.ftpFiles = ftpFiles;
    }
}

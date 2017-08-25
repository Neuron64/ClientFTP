package com.neuron64.ftp.client;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yks-11 on 25.08.17.
 */
public class FTPApi {
    private static FTPApi ourInstance = new FTPApi();

    public static FTPApi getInstance() {
        return ourInstance;
    }

    private FTPClient ftpClient;

    private FTPApi() {
        this.ftpClient = new FTPClient();
    }

    public void login(String server, int port, String user, String pass) throws IOException {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    public void login(String server, String user, String pass) throws IOException {
        ftpClient.connect(server, 21);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    public FTPFile[] getFilesByDirectory(FTPFile fileDirectory) throws IOException {
        return ftpClient.listFiles(fileDirectory.getName());
    }

    public FTPFile[] getFilesByDirectoryName(String name) throws IOException {
        return ftpClient.listFiles(name);
    }

    public FileDirectory getAllFilesAndDirectory() throws IOException {
        FTPFile[] ftpFiles = ftpClient.listFiles();
        FileDirectory fileDirectory = new FileDirectory();
        getAllDirectoryList(fileDirectory, ftpFiles);
        return fileDirectory;
    }

    private void getAllDirectoryList(FileDirectory fileDirectory, FTPFile[] ftpFiles) throws IOException {
        List<FTPFile> files = new ArrayList<>();
        List<FileDirectory> fileDirectories = new ArrayList<>();
        for (FTPFile ftpFile : ftpFiles) {
            if(ftpFile.getType() == FTPFile.DIRECTORY_TYPE) {
                FileDirectory directory = new FileDirectory();
                directory.setRootDirectory(ftpFile);
                fileDirectories.add(directory);
                System.out.println("" + ftpFile.getName());
                getAllDirectoryList(directory, getFilesByDirectoryName(ftpFile.getName()));
            }else {
                files.add(ftpFile);
            }
        }
        fileDirectory.setFileDirectory(fileDirectories);
        fileDirectory.setFtpFiles(files);
    }
}

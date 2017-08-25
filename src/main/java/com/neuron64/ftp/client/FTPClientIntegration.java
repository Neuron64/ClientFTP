package com.neuron64.ftp.client;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

/**
 * Created by yks-11 on 23.08.17.
 */
public class FTPClientIntegration {

    private FTPClient ftpClient;
    private String server = "demo.wftpserver.com";
    private int port = 21;
    private String user = "demo-user";
    private String pass = "demo-user";

    public FTPClientIntegration() {
        initFtp();
    }

    private void initFtp(){
        ftpClient = new FTPClient();
    }

    public void login() throws IOException {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
    }

    public void getFiles() throws IOException {
        FTPFile[] files = ftpClient.listFiles();
        for (FTPFile file : files) {
            if(file.getType() == FTPFile.DIRECTORY_TYPE) {
                System.out.println("getPath: " + file.toString());
                getFile(file);
            }
        }
    }

    private void getFile(FTPFile fileDirectory) throws IOException {
        FTPFile[] files = ftpClient.listFiles(fileDirectory.getName());
        for (FTPFile file : files) {
            if(file.getType() == FTPFile.DIRECTORY_TYPE) {
                System.out.println("getPath: " + file.toString());
                getFile(file);
            }
        }
    }
}
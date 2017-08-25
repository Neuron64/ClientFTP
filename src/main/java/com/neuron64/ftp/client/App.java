package com.neuron64.ftp.client;

import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

/**
 * Created by yks-11 on 23.08.17.
 */
public class App {

    public static void main(String[] args){
//        com.neuron64.ftp.client.FTPClientIntegration ftpClient = new com.neuron64.ftp.client.FTPClientIntegration();
//        ftpClient.login();
//        ftpClient.getFiles();
        try {
            FTPApi ftpApi = FTPApi.getInstance();
            UserSetting userSetting = new UserSetting("demo.wftpserver.com", 21, "demo-user", "demo-user");
            ftpApi.login(userSetting.getServer(), userSetting.getPort(), userSetting.getUser(), userSetting.getPass());
            printFileDirectory(ftpApi.getAllFilesAndDirectory(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printFileDirectory(FileDirectory fileDirectory, int lvl){
        int newLvl = lvl;
        if(fileDirectory.getRootDirectory() != null) {
            System.out.println("lvl: " + lvl + " RootDirectory: " + fileDirectory.getRootDirectory().getName());
        }
        for (FTPFile ftpFile : fileDirectory.getFtpFiles()) {
            System.out.println("lvl: " + lvl + " ftpFile " + ftpFile.getName());
        }
        if(fileDirectory.getFileDirectory() != null){
            for (FileDirectory directory : fileDirectory.getFileDirectory()) {
                printFileDirectory(directory, ++newLvl);
                newLvl = lvl;
            }
        }
    }
}

package com.neuron64.ftp.client;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Created by yks-11 on 25.08.17.
 */
public enum FileTypeFtp {

    DIRECTORY_TYPE(FTPFile.DIRECTORY_TYPE),
    FILE_TYPE(FTPFile.FILE_TYPE),
    SYMBOLIC_LINK_TYPE(FTPFile.SYMBOLIC_LINK_TYPE),
    UNKNOWN_TYPE(FTPFile.UNKNOWN_TYPE);

    public int type;

    FileTypeFtp(int type) {
        this.type = type;
    }

    public static FileTypeFtp getType(int type){
        switch (type){
            case FTPFile.DIRECTORY_TYPE: return FileTypeFtp.DIRECTORY_TYPE;
            case FTPFile.FILE_TYPE: return FileTypeFtp.FILE_TYPE;
            case FTPFile.SYMBOLIC_LINK_TYPE: return FileTypeFtp.SYMBOLIC_LINK_TYPE;
            case FTPFile.UNKNOWN_TYPE: return FileTypeFtp.UNKNOWN_TYPE;
        }
        return null;
    }
}

package com.neuron64.ftp.data.mapper;

import com.neuron64.ftp.data.exception.ErrorMappingUnavailable;
import com.neuron64.ftp.domain.model.FileInfo;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Created by yks-11 on 10/16/17.
 */

public class FtpFileMapper extends Mapper<FileInfo, FTPFile>{

    @Override
    public FileInfo map(FTPFile ftpFile) {
        return new FileInfo(ftpFile.getName(), ftpFile.getName(), String.valueOf(ftpFile.getSize()), String.valueOf(ftpFile.getType()), ftpFile.isDirectory());
    }

    @Override
    public FTPFile reverseMap(FileInfo directory) {
        throw new ErrorMappingUnavailable();
    }
}

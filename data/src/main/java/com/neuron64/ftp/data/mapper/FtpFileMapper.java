package com.neuron64.ftp.data.mapper;

import com.neuron64.ftp.data.exception.ErrorMappingUnavailable;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Created by yks-11 on 10/16/17.
 */

public class FtpFileMapper extends Mapper<FileSystemDirectory, FTPFile>{

    @Override
    public FileSystemDirectory map(FTPFile ftpFile) {
        return new FileSystemDirectory(ftpFile.getName(), ftpFile.getName(), String.valueOf(ftpFile.getSize()), String.valueOf(ftpFile.getType()));
    }

    @Override
    public FTPFile reverseMap(FileSystemDirectory directory) {
        throw new ErrorMappingUnavailable();
    }
}

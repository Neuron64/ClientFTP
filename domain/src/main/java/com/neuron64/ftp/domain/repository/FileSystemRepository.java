package com.neuron64.ftp.domain.repository;

import com.neuron64.ftp.domain.model.FileSystemDirectory;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.annotations.Nullable;

/**
 * Created by yks-11 on 10/13/17.
 */

public interface FileSystemRepository {

    Single<List<FileSystemDirectory>> getExternalStorageFiles();

    Single<List<FileSystemDirectory>> getNextFiles(@Nullable String directoryId);

    Single<List<FileSystemDirectory>> getPreviousFiles();

}

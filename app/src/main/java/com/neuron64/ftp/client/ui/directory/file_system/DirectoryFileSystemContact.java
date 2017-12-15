package com.neuron64.ftp.client.ui.directory.file_system;

import com.neuron64.ftp.client.ui.directory.DirectoryContact;
import com.neuron64.ftp.domain.model.FileInfo;

/**
 * Created by Neuron on 01.10.2017.
 */

public interface DirectoryFileSystemContact extends DirectoryContact {

    interface View extends DirectoryContact.BaseDirectoryView<DirectoryFileSystemContact.Presenter>{

    }

    interface Presenter extends DirectoryContact.BaseDirectoryPresenter<DirectoryFileSystemContact.View>{

        void uploadFile(FileInfo fileInfo);

    }
}

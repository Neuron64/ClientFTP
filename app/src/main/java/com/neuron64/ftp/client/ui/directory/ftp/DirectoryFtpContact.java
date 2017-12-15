package com.neuron64.ftp.client.ui.directory.ftp;

import com.neuron64.ftp.client.ui.directory.DirectoryContact;
import com.neuron64.ftp.domain.model.FileInfo;
import com.neuron64.ftp.domain.model.UserConnection;

/**
 * Created by yks-11 on 10/17/17.
 */

public interface DirectoryFtpContact extends DirectoryContact {

    interface View extends DirectoryContact.BaseDirectoryView<DirectoryFtpContact.Presenter>{

        UserConnection getExtraUserConnection();

    }

    interface Presenter extends DirectoryContact.BaseDirectoryPresenter<DirectoryFtpContact.View>{

        void downloadFile(FileInfo fileInfo);

    }
}

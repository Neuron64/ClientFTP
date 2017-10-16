package com.neuron64.ftp.client.ui.directory;

import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import java.util.List;

/**
 * Created by Neuron on 01.10.2017.
 */

public interface DirectoryContact {

    interface View extends BaseView<Presenter>{

        void showFiles(List<FileSystemDirectory> files);

        void showError();

        void showEmptyList();

        void hideEmptyList();

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void finishActivity();

        void clearRecyclerView();
    }

    interface Presenter extends BasePresenter<View>{

        RxBus getEventBus();

        void clickFile(FileSystemDirectory file);

        void clickHome();
    }

}

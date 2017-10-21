package com.neuron64.ftp.client.ui.directory;

import com.neuron64.ftp.client.ui.base.BasePresenter;
import com.neuron64.ftp.client.ui.base.BaseView;
import com.neuron64.ftp.client.ui.base.bus.RxBus;
import com.neuron64.ftp.domain.model.FileSystemDirectory;

import java.util.List;

/**
 * Created by yks-11 on 10/17/17.
 */

public interface DirectoryContact{

    interface BaseDirectoryView<P extends BaseDirectoryPresenter<?>> extends BaseView<P> {

        void showFiles(List<FileSystemDirectory> files);

        void showError();

        void showEmptyList();

        void hideEmptyList();

        void showLoadingIndicator();

        void hideLoadingIndicator();

        void finishActivity();

        void clearRecyclerView();
    }

    interface BaseDirectoryPresenter<V extends BaseView> extends BasePresenter<V> {

        RxBus getEventBus();

        void clickFile(FileSystemDirectory file);

        void clickHome();
    }
}

package com.neuron64.ftp.client.ui.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding2.InitialValueObservable;
import com.jakewharton.rxbinding2.support.design.widget.RxTextInputLayout;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerViewComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.domain.model.UserConnection;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by Neuron on 17.09.2017.
 */

public class CreateConnectionFragment extends BaseFragment implements CreateConnectionContract.View{

    @BindView(R.id.text_input_title) TextInputLayout textInputTitle;
    @BindView(R.id.text_input_host) TextInputLayout textInputHost;
    @BindView(R.id.text_input_username) TextInputLayout textInputUsername;
    @BindView(R.id.text_input_password) TextInputLayout textInputPassword;
    @BindView(R.id.text_input_port) TextInputLayout textInputPort;

    private CreateConnectionContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Observable<Boolean> passwordObs = RxTextView.afterTextChangeEvents(textInputPassword.getEditText())
                .map(presenter.isPasswordValue());
        passwordObs.subscribe(presenter.updatePasswordValue());

        Observable<Boolean> usernameObs = RxTextView.afterTextChangeEvents(textInputUsername.getEditText())
                .map(presenter.isUsernameValue());
        usernameObs.subscribe(presenter.updateUsernameValue());

        Observable<Boolean> portObs = RxTextView.afterTextChangeEvents(textInputPort.getEditText())
                .map(presenter.isPortValue());
        portObs.subscribe(presenter.updatePortValue());

        Observable<Boolean> hostObs = RxTextView.afterTextChangeEvents(textInputHost.getEditText())
                .map(presenter.isHostValue());
        hostObs.subscribe(presenter.updateHostValue());

        Observable<Boolean> titleObs = RxTextView.afterTextChangeEvents(textInputTitle.getEditText())
                .map(presenter.isTitleValue());
        titleObs.subscribe(presenter.updateTitleValue());

        Observable.combineLatest(passwordObs, usernameObs, portObs, hostObs, titleObs, presenter.isFormValid()).subscribe(this::updateSubmitButtonViewState);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void bind(View root) {
        super.bind(root);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_create_connection;
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.getAppInstance().getAppComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void attachPresenter(@NonNull LoginContract.Presenter presenter) {

    }

    @Override
    public void setPresenter(CreateConnectionContract.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void fillingFields(UserConnection userConnection) {

    }

    @Override
    public void pickConnection() {

    }

    @Override
    public boolean onValidate() {
        return false;
    }

    @Override
    public void updateSubmitButtonViewState(boolean enabled) {

    }
}

package com.neuron64.ftp.client.ui.connection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.di.component.DaggerViewComponent;
import com.neuron64.ftp.client.di.module.PresenterModule;
import com.neuron64.ftp.client.ui.base.BaseFragment;
import com.neuron64.ftp.client.util.Preconditions;
import com.neuron64.ftp.client.util.ViewMessage;
import com.neuron64.ftp.domain.model.UserConnection;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Neuron on 17.09.2017.
 */

public class CreateConnectionFragment extends BaseFragment implements CreateConnectionContract.View{

    private static final String PARAM_CONNECTION = "param_connection";

    @BindView(R.id.text_input_title) TextInputLayout textInputTitle;
    @BindView(R.id.text_input_host) TextInputLayout textInputHost;
    @BindView(R.id.text_input_username) TextInputLayout textInputUsername;
    @BindView(R.id.text_input_password) TextInputLayout textInputPassword;
    @BindView(R.id.text_input_port) TextInputLayout textInputPort;
    @BindView(R.id.ll_root) LinearLayout llRoot;

    private CreateConnectionContract.Presenter presenter;

    public static CreateConnectionFragment newInstance(){
        return new CreateConnectionFragment();
    }

    public static CreateConnectionFragment newInstance(UserConnection userConnection){
        CreateConnectionFragment fragment = new CreateConnectionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(PARAM_CONNECTION, userConnection);
        fragment.setArguments(bundle);
        return new CreateConnectionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(presenter != null){
            presenter.unsubscribe();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter!=null){
            presenter.subscribe();
        }
    }

    @Inject @Override
    public void attachPresenter(@NonNull CreateConnectionContract.Presenter presenter) {
        this.presenter = Preconditions.checkNotNull(presenter);
        presenter.attachView(this);
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
    public void fillingFields(String userName, String password, String host, String title, String port) {
        if(title != null) putStringToInputLayout(textInputTitle, title);
        else putStringToInputLayout(textInputTitle, getString(R.string.new_connection));
        putStringToInputLayout(textInputUsername, userName);
        putStringToInputLayout(textInputPort, port);
        putStringToInputLayout(textInputPassword, password);
        putStringToInputLayout(textInputHost, host);
    }

    @Override
    public void pickConnection() {
        String username = getStringFromInputLayout(textInputUsername);
        String port = getStringFromInputLayout(textInputPort);
        String password = getStringFromInputLayout(textInputPassword);
        String title = getStringFromInputLayout(textInputTitle);
        String host = getStringFromInputLayout(textInputHost);

        presenter.sendConnection(username, password, host, title, port);
    }

    @Override
    public boolean onValidate() {
        return false;
    }

    @Override
    public void updateSubmitButtonViewState(boolean enabled) {

    }

    @Override
    public void showSnackBar(int message) {
        ViewMessage.initSnackBarShort(llRoot, message);
    }

    @Override
    public UserConnection currentArgConnection() {
        if(getArguments() != null && getArguments().containsKey(PARAM_CONNECTION)) {
            return getArguments().getParcelable(PARAM_CONNECTION);
        }else{
            return null;
        }
    }

    private String getStringFromInputLayout(TextInputLayout textInputLayout){
        return textInputLayout.getEditText() != null
                ? textInputLayout.getEditText().getText().toString()
                : null;
    }

    private void putStringToInputLayout(TextInputLayout textInputLayout, String value){
        if(textInputLayout.getEditText() != null) {
            textInputLayout.getEditText().setText(value);
        }
    }
}

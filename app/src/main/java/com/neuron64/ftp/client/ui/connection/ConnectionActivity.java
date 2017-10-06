package com.neuron64.ftp.client.ui.connection;

import android.Manifest;
import android.content.ContentProviderClient;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.demo.DocumentCursor;
import com.neuron64.ftp.client.demo.DocumentsProvider;
import com.neuron64.ftp.client.demo.DocumentsProviderRegistry;
import com.neuron64.ftp.client.demo.FileSystemProvider;
import com.neuron64.ftp.client.demo.Root;
import com.neuron64.ftp.client.demo.docprovider.ExternalStorageProvider;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.ui.base.Navigator;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.base.bus.event.FragmentEvent;
import com.neuron64.ftp.client.ui.base.bus.event.NavigateEvent;
import com.neuron64.ftp.client.util.ActivityUtils;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.domain.model.UserConnection;

import java.io.FileNotFoundException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Neuron on 02.09.2017.
 */

public class ConnectionActivity extends BaseActivity {

    private static final String TAG = "ConnectionActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private Unbinder unbinder;

    private static final String FILTER = "application/pdf";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        if(savedInstanceState == null){
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), ConnectionsFragment.newInstance(), R.id.contentFrame, ConnectionsFragment.TAG);
        }

//        getContentResolver().query(ExternalStorageProvider.AUTHORITY, null, )
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS,
//                    Manifest.permission.READ_EXTERNAL_STORAGE};
//            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
//        }else{
//        }

        ContentProviderClient mExternalStorageClient =
                getContentResolver().acquireContentProviderClient(ExternalStorageProvider.AUTHORITY);

        Uri contentsUri = DocumentsContract.buildChildDocumentsUri(ExternalStorageProvider.AUTHORITY, "emulated:");
        try {
            Cursor cursor = getContentResolver().query(contentsUri, null, null, null);
            Cursor cursor1 = mExternalStorageClient.query(contentsUri, null, null, null);
            if(cursor != null && cursor.moveToFirst()){
                while (cursor.moveToNext()) {
                    String title = cursor.getString(cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME));
                    Log.d(TAG, "onCreate: " + title);
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
//        FileSystemProvider.register(this);



//        List<Root> rootList = DocumentsProviderRegistry.get().getAllRoots();
//
//        for (Root root : rootList) {
//            DocumentsProvider documentsProvider = DocumentsProviderRegistry.get().getProvider(root.getDocumentsProvider().getId());
//            try {
//                 Cursor cursor = documentsProvider.queryChildDocuments(root.getDocumentId(), null, DocumentsContract.Document.COLUMN_DISPLAY_NAME, null);
//                int name = cursor.getColumnIndex(DocumentsContract.Document.COLUMN_DISPLAY_NAME);
//                cursor.moveToFirst();
//                DocumentCursor documentCursor = new DocumentCursor(cursor);
//                String nameColumn = documentCursor.getName();
//                Log.d(TAG, "onCreate: " + cursor.toString());
//                Log.d(TAG, "onCreate: " + nameColumn);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//        }

    }

    @OnClick(R.id.fab)
    void onClickFab(){
        eventBus.send(ButtonEvent.buttonEvent(ButtonEvent.TypeButtonEvent.FLOATING_BN));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        if(event instanceof FragmentEvent){
            FragmentEvent exposeEvent = (FragmentEvent) event;
            switch (exposeEvent.code){
                case FragmentEvent.CREATE_CONNECTION: {
                    CreateConnectionFragment fragment;
                    if(exposeEvent.data != null && exposeEvent.data.containsKey(Constans.EXTRA_USER_CONNECTION)){
                        UserConnection connection = exposeEvent.data.getParcelable(Constans.EXTRA_USER_CONNECTION);
                        fragment = CreateConnectionFragment.newInstance(connection);
                    }else{
                        fragment = CreateConnectionFragment.newInstance();
                    }

                    ActivityUtils.addFragmentToActivityWithBackStack(getSupportFragmentManager(), fragment, R.id.contentFrame, null, CreateConnectionFragment.TAG);
                    break;
                }
                case FragmentEvent.SHOW_CONNECTIONS: {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentFrame);
                    if(fragment instanceof CreateConnectionFragment){
                        getSupportFragmentManager().popBackStack();
                    }
                    break;
                }
            }
        }else if(event instanceof NavigateEvent){
            NavigateEvent navigateEvent = (NavigateEvent) event;

            Navigator navigator = new Navigator();
            navigator.navigate(this, navigateEvent);
        }
    }

    @Override
    public void inject() {
        App.getAppInstance().getAppComponent().inject(this);
    }
}

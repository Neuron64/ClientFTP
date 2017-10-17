package com.neuron64.ftp.client.ui.directory;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.neuron64.ftp.client.App;
import com.neuron64.ftp.client.R;
import com.neuron64.ftp.client.ui.base.BaseActivity;
import com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent;
import com.neuron64.ftp.client.ui.directory.file_system.DirectoryFileSystemFragment;
import com.neuron64.ftp.client.ui.directory.file_system.DirectoryFileSystemPresenter;
import com.neuron64.ftp.client.ui.directory.ftp.DirectoryFtpFragment;
import com.neuron64.ftp.client.ui.directory.ftp.DirectoryFtpSystemPresenter;
import com.neuron64.ftp.client.util.Constans;
import com.neuron64.ftp.domain.model.UserConnection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.neuron64.ftp.client.ui.base.bus.event.ButtonEvent.buttonEvent;

/**
 * Created by Neuron on 01.10.2017.
 */

public class DirectoryActivity extends BaseActivity{

    private Unbinder unbinder;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.pager_content)
    ViewPager viewPager;

    private PagerContentAdapter pagerContentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.directory_activity);
        unbinder = ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MANAGE_DOCUMENTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
        }

        if(savedInstanceState == null){
            updateUI();
        }
    }


    public void updateUI(){
        UserConnection userConnection = getExtraUserConnection();
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(pagerContentAdapter == null) {
            pagerContentAdapter = new PagerContentAdapter(fragmentManager, userConnection);
        }

        viewPager.setAdapter(pagerContentAdapter);
    }

    private UserConnection getExtraUserConnection(){
        Bundle bundle = getIntent().getExtras();

        if(bundle != null && bundle.containsKey(Constans.EXTRA_DATA)) {
            Bundle bundle1 = bundle.getBundle(Constans.EXTRA_DATA);
            if (bundle1 != null && bundle1.containsKey(Constans.EXTRA_USER_CONNECTION)) {
                return bundle1.getParcelable(Constans.EXTRA_USER_CONNECTION);
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        String nowClass = pagerContentAdapter.getNowClass();
        eventBus.send(buttonEvent(ButtonEvent.TypeButtonEvent.ON_BACK_PRESSED, nowClass));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        //Empty
    }

    @Override
    public void inject() {
        App.getAppInstance().getAppComponent().inject(this);
    }

    class PagerContentAdapter extends FragmentPagerAdapter {

        private final int[] titles;

        private DirectoryFragment directorySystem;
        private DirectoryFragment directoryFtp;
        private int nowPosition;

        public PagerContentAdapter(FragmentManager fm, UserConnection userConnection) {
            super(fm);
            titles = new int[]{R.string.ftp, R.string.file_system};
            directoryFtp = DirectoryFtpFragment.newInstance(userConnection);
            directorySystem = DirectoryFileSystemFragment.newInstance();
        }

        @Override
        public Fragment getItem(int position) {
            nowPosition = position;
            if(position == 0){
                return directoryFtp;
            }else{
                return directorySystem;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        public String getNowClass(){
            if(nowPosition == 0){
                return DirectoryFtpSystemPresenter.class.getSimpleName();
            }else{
                return DirectoryFileSystemPresenter.class.getSimpleName();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(titles[position]);
        }
    }
}

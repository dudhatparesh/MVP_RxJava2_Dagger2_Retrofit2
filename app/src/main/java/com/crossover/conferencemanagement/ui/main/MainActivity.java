package com.crossover.conferencemanagement.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.authentication.AuthenticationActivity;
import com.crossover.conferencemanagement.ui.base.BaseActivity;
import com.crossover.conferencemanagement.ui.main.adminhome.AdminHomeFragment;
import com.crossover.conferencemanagement.ui.main.doctorhome.DoctorHomeFragment;
import com.crossover.conferencemanagement.ui.option.OptionActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class MainActivity extends BaseActivity implements MainMvpView {
    @Inject
    MainPresenter mMainPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        getComponent().inject(this);
        mMainPresenter.attachView(this);
        toolbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        mMainPresenter.checkLoggedinInUserType();
    }

    @Override
    protected void onDestroy() {
        mMainPresenter.detachView();
        super.onDestroy();
    }

    public void displayFragment(Fragment fragment, boolean isAddToBackstack) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_container, fragment, "");
        if (isAddToBackstack) {
            transaction.addToBackStack("");
        }
        transaction.commit();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_admin;
    }

    @Override
    public void displayError(Throwable e) {
        Toast.makeText(this, e.getLocalizedMessage(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadDoctorHomeFragment() {
        displayFragment(new DoctorHomeFragment(), false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            mMainPresenter.logout();
        }
        return true;
    }

    @Override
    public void loadAdminHomeFragment() {
        displayFragment(new AdminHomeFragment(), false);
    }

    @Override
    public void exit() {
        Intent intent = new Intent(this, OptionActivity.class);
        startActivity(intent);
        finish();
    }
}

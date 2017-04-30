package com.crossover.conferencemanagement.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.crossover.conferencemanagement.MainApplication;
import com.crossover.conferencemanagement.di.component.ActivityComponent;
import com.crossover.conferencemanagement.di.component.DaggerActivityComponent;

import butterknife.ButterKnife;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
    }

    protected abstract int getContentView();

    public void displayProgressDialog(String title, String message, boolean cancelable) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (!isFinishing() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public ActivityComponent getComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(MainApplication.get(this).getApplicationComponent())
                .build();
    }
}

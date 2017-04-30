package com.crossover.conferencemanagement.ui.option;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.authentication.AuthenticationActivity;
import com.crossover.conferencemanagement.ui.base.BaseActivity;
import com.crossover.conferencemanagement.util.Constant;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by PareshDudhat on 10-03-2017.
 */

public class OptionActivity extends BaseActivity implements OptionMvpView {
    @Inject
    OptionPresenter mOptionPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
        mOptionPresenter.attachView(this);
        mOptionPresenter.checkUserLogin();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_option;
    }

    @Override
    public void openActivity(Class activityClass, Bundle bundle) {
        Intent intent = new Intent(this, activityClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    @Override
    public void displayError(Throwable e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.btn_admin, R.id.btn_doctor})
    void onClick(Button btn) {
        Bundle bundle = new Bundle();
        switch (btn.getId()) {
            case R.id.btn_doctor:
                bundle.putInt(Constant.Key.USER_TYPE, Constant.UserType.Doctor);
                break;
            case R.id.btn_admin:
                bundle.putInt(Constant.Key.USER_TYPE, Constant.UserType.Admin);
                break;
        }
        openActivity(AuthenticationActivity.class, bundle);
    }

    @Override
    protected void onDestroy() {
        mOptionPresenter.detachView();
        super.onDestroy();
    }
}

package com.crossover.conferencemanagement.ui.authentication.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.base.BaseFragment;
import com.crossover.conferencemanagement.util.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class LoginFragment extends BaseFragment implements LoginMvpView {
    View view;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;

    @Inject
    LoginPresenter mLoginPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_login, container, false);
            ButterKnife.bind(this, view);
            getComponent().inject(this);
            mLoginPresenter.attachView(this);
        }
        return view;
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayToast(int messageResourceId) {
        Toast.makeText(getContext(), messageResourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void openActivity(Class activityClass) {
        Intent intent = new Intent(getContext(), activityClass);
        startActivity(intent);
    }

    @OnClick(R.id.btn_login)
    void onClick() {
        boolean isValid = mLoginPresenter.validateData(etUsername.getText().toString(),
                etPassword.getText().toString());
        if (isValid) {
            mLoginPresenter.login(etUsername.getText().toString(),
                    etPassword.getText().toString(), getActivity().getIntent().getExtras().getInt(
                            Constant.Key.USER_TYPE
                    ));
        }
    }

    @Override
    public void onDetach() {
        mLoginPresenter.detachView();
        super.onDetach();
    }
}

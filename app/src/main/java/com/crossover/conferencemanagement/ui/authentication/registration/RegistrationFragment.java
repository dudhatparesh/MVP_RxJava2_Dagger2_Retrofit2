package com.crossover.conferencemanagement.ui.authentication.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class RegistrationFragment extends BaseFragment implements RegistrationMvpView {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_experience)
    EditText etExperience;
    @BindView(R.id.et_expertise)
    EditText etExpertise;
    @BindView(R.id.ll_doctor_data)
    LinearLayout llDoctorData;
    View view;
    @Inject
    RegistrationPresenter registrationPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_register, container, false);
            ButterKnife.bind(this, view);
            getComponent().inject(this);
            registrationPresenter.attachView(this);
            if (getActivity().getIntent().getExtras().getInt(
                    Constant.Key.USER_TYPE
            ) == Constant.UserType.Doctor) {
                llDoctorData.setVisibility(View.VISIBLE);
            } else {
                llDoctorData.setVisibility(View.GONE);
            }
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

    @OnClick(R.id.btn_register)
    void onClick() {
        boolean isValid = registrationPresenter.validateData(etUsername.getText().toString(),
                etPassword.getText().toString(),
                etExpertise.getText().toString(),
                etExperience.getText().toString(),
                getActivity().getIntent().getExtras().getInt(
                        Constant.Key.USER_TYPE
                ));
        if (isValid) {
            registrationPresenter.register(etUsername.getText().toString(),
                    etPassword.getText().toString(),
                    etExperience.getText().toString(),
                    etExpertise.getText().toString(),
                    etName.getText().toString(),
                    getActivity().getIntent().getExtras().getInt(
                            Constant.Key.USER_TYPE
                    ));
        }
    }

    @Override
    public void onDestroy() {
        registrationPresenter.detachView();
        super.onDestroy();
    }
}

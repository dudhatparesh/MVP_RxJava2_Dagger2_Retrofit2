package com.crossover.conferencemanagement.ui.base;

import android.support.v4.app.Fragment;

import com.crossover.conferencemanagement.MainApplication;
import com.crossover.conferencemanagement.di.component.DaggerFragmentComponent;
import com.crossover.conferencemanagement.di.component.FragmentComponent;

/**
 * Created by PareshDudhat on 11-03-2017.
 */

public class BaseFragment extends Fragment {
    public void displayProgressDialog(String title, String message, boolean isCancelable) {
        ((BaseActivity) getActivity()).displayProgressDialog(title, message, isCancelable);
    }

    public void dismissProgressDialog() {
        ((BaseActivity) getActivity()).dismissProgressDialog();
    }

    public FragmentComponent getComponent() {
        return DaggerFragmentComponent.builder()
                .applicationComponent(MainApplication.get(getContext()).getApplicationComponent())
                .build();
    }
}

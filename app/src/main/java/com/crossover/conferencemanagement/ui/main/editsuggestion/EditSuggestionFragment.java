package com.crossover.conferencemanagement.ui.main.editsuggestion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.ui.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class EditSuggestionFragment extends BaseFragment implements EditSuggestionMvpView {

    View view;
    @BindView(R.id.et_topic)
    EditText etTopic;
    @BindView(R.id.et_detail)
    EditText etDetail;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @Inject
    EditSuggestionPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_edit_suggestion,
                    container, false);
            ButterKnife.bind(this, view);
            getComponent().inject(this);
            mPresenter.attachView(this);
        }
        return view;
    }


    @OnClick({R.id.btn_submit})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                mPresenter.submitSuggestion(etTopic.getText().toString(),
                        etDetail.getText().toString());
                break;
        }
    }

    @Override
    public void suggestionSubmitted() {
        Toast.makeText(getContext(), R.string.suggestion_submitted, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void displayError(Throwable e) {
        Toast.makeText(getContext(), e.getLocalizedMessage(),
                Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}

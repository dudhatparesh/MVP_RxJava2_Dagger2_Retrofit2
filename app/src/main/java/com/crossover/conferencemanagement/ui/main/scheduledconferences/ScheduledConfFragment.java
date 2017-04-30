package com.crossover.conferencemanagement.ui.main.scheduledconferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.ui.base.BaseFragment;
import com.crossover.conferencemanagement.ui.main.conferencelist.ConferenceAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class ScheduledConfFragment extends BaseFragment implements ScheduledConfMvpView {
    View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @Inject
    ScheduledConfPresenter presenter;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
            ButterKnife.bind(this, view);
            getComponent().inject(this);
            presenter.attachView(this);
            fab.setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadConferences();
    }

    @Override
    public void displayEmptyList() {
        tvMessage.setText(R.string.no_data_available);
        tvMessage.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void displayError(int message) {
        tvMessage.setText(message);
        tvMessage.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void displayError(String message) {
        tvMessage.setText(message);
        tvMessage.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void displayConferences(List<Conference> conferences) {
        ConferenceAdapter conferenceAdapter = new ConferenceAdapter(conferences, null);
        recyclerView.setAdapter(conferenceAdapter);
        tvMessage.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

}

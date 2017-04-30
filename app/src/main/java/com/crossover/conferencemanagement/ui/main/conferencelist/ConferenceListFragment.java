package com.crossover.conferencemanagement.ui.main.conferencelist;

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
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.main.editconference.EditConferenceFragment;
import com.crossover.conferencemanagement.util.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class ConferenceListFragment extends BaseFragment
        implements ConferenceListMvpView {
    View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @Inject
    ConferenceListPresenter presenter;

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
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        presenter.loadConferences();
        return view;
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
        ConferenceClickListener listener = new ConferenceClickListener() {
            @Override
            public void onClick(Conference conference) {
                EditConferenceFragment fragment = new EditConferenceFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.Key.CONFERENCE, conference);
                fragment.setArguments(bundle);
                ((MainActivity) getActivity()).displayFragment(fragment, true);
            }
        };
        ConferenceAdapter conferenceAdapter = new ConferenceAdapter(conferences, listener);
        recyclerView.setAdapter(conferenceAdapter);
        tvMessage.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fab)
    void onClick() {
        EditConferenceFragment fragment = new EditConferenceFragment();
        ((MainActivity) getActivity()).displayFragment(fragment, true);
    }
}

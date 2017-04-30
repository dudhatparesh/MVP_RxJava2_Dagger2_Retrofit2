package com.crossover.conferencemanagement.ui.main.suggestionlist;

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
import com.crossover.conferencemanagement.data.model.Suggestion;
import com.crossover.conferencemanagement.ui.base.BaseFragment;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.main.editsuggestion.EditSuggestionFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class SuggestionListFragment extends BaseFragment
        implements SuggestionListMvpView {
    View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @Inject
    SuggestionListPresenter presenter;
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
            presenter.getUserType();
        }
        presenter.loadSuggestions();
        return view;
    }

    @Override
    public void displayEmptyList() {
        tvMessage.setText(R.string.no_data_available);
        tvMessage.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(GONE);
    }

    @Override
    public void displayError(int message) {
        tvMessage.setText(message);
        tvMessage.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(GONE);
    }

    @Override
    public void displayError(String message) {
        tvMessage.setText(message);
        tvMessage.setVisibility(View.VISIBLE);
        btnRefresh.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(GONE);
    }

    @Override
    public void displaySuggestions(List<Suggestion> suggestions) {
        SuggestionClickListener listener = new SuggestionClickListener() {
            @Override
            public void onClick(Conference conference) {

            }
        };
        SuggestionAdapter conferenceAdapter = new SuggestionAdapter(suggestions);
        recyclerView.setAdapter(conferenceAdapter);
        tvMessage.setVisibility(GONE);
        btnRefresh.setVisibility(GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayFab() {
        fab.setVisibility(VISIBLE);
    }

    @Override
    public void hideFab() {
        fab.setVisibility(GONE);
    }

    @OnClick(R.id.fab)
    void onClick() {
        EditSuggestionFragment fragment = new EditSuggestionFragment();
        ((MainActivity) getActivity()).displayFragment(fragment, true);
    }
}

package com.crossover.conferencemanagement.ui.main.invites;

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
import android.widget.Toast;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.model.Conference;
import com.crossover.conferencemanagement.ui.base.BaseFragment;
import com.crossover.conferencemanagement.ui.main.MainActivity;
import com.crossover.conferencemanagement.ui.main.editsuggestion.EditSuggestionFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class InvitesFragment extends BaseFragment implements InvitesMvpView {

    View view;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.btn_refresh)
    Button btnRefresh;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @Inject
    InvitesPresenter presenter;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
            ButterKnife.bind(this, view);
            fab.setVisibility(View.GONE);
            getComponent().inject(this);
            presenter.attachView(this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        presenter.loadInvites();
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
    public void displayInvites(List<Conference> conferences) {
        InviteClickListener listener = new InviteClickListener() {
            @Override
            public void onClick(Conference conference) {
            }

            @Override
            public void accept(Conference conference) {

                presenter.acceptInvite(conference);
                Toast.makeText(getContext(), "You accepted:" + conference.getTopic()
                        , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void reject(Conference conference) {
                presenter.rejectInvite(conference);
                Toast.makeText(getContext(), "You rejected:" + conference.getTopic()
                        , Toast.LENGTH_SHORT).show();
            }
        };
        InviteAdapter inviteAdapter = new InviteAdapter(conferences, listener);
        recyclerView.setAdapter(inviteAdapter);
        tvMessage.setVisibility(View.GONE);
        btnRefresh.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayToast(int messageID) {
        Toast.makeText(getContext(), messageID, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.fab)
    void onClick() {
        EditSuggestionFragment fragment = new EditSuggestionFragment();
        ((MainActivity) getActivity()).displayFragment(fragment, true);
    }
}

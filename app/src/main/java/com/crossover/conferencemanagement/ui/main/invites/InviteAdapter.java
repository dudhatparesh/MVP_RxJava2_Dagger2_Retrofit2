package com.crossover.conferencemanagement.ui.main.invites;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.model.Conference;

import java.util.List;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class InviteAdapter extends RecyclerView.Adapter<InviteViewHolder> {
    private List<Conference> conferences;
    private InviteClickListener clickListener;

    public InviteAdapter(List<Conference> conferences, InviteClickListener clickListener) {
        this.conferences = conferences;
        this.clickListener = clickListener;
    }

    @Override
    public InviteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_invite, parent, false);
        return new InviteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InviteViewHolder holder,
                                 int position) {
        final Conference conference = conferences.get(position);
        holder.tvTopic.setText(conference.getTopic());
        holder.tvDate.setText(conference.getDate());
        holder.tvDetail.setText(conference.getDetail());
        holder.tvStatus.setText(conference.getStatus());
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.accept(conference);
            }
        });
        holder.btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.reject(conference);
            }
        });
    }

    @Override
    public int getItemCount() {
        return conferences.size();
    }
}

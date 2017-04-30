package com.crossover.conferencemanagement.ui.main.conferencelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.model.Conference;

import java.util.List;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class ConferenceAdapter extends RecyclerView.Adapter<ConferenceViewHolder> {
    private List<Conference> conferences;
    private ConferenceClickListener listener;

    public ConferenceAdapter(List<Conference> conferences, ConferenceClickListener listener) {
        this.conferences = conferences;
        this.listener = listener;
    }

    @Override
    public ConferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_conference, parent, false);
        return new ConferenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ConferenceViewHolder holder, int position) {
        Conference conference = conferences.get(position);
        holder.tvTopic.setText(conference.getTopic());
        holder.tvDate.setText(conference.getDate());
        holder.tvDetail.setText(conference.getDetail());
        holder.tvStatus.setText(conference.getStatus());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.onClick(conferences.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return conferences.size();
    }
}

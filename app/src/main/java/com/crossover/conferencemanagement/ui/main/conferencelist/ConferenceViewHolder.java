package com.crossover.conferencemanagement.ui.main.conferencelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.crossover.conferencemanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class ConferenceViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    public ConferenceViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

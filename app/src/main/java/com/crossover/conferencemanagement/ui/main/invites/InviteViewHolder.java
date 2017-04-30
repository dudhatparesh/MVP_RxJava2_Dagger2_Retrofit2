package com.crossover.conferencemanagement.ui.main.invites;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crossover.conferencemanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by PareshDudhat on 13-03-2017.
 */

public class InviteViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv_topic)
    TextView tvTopic;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.btn_reject)
    Button btnReject;

    public InviteViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}

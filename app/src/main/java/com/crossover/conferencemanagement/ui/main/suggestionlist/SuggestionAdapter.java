package com.crossover.conferencemanagement.ui.main.suggestionlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crossover.conferencemanagement.R;
import com.crossover.conferencemanagement.data.model.Suggestion;

import java.util.List;

/**
 * Created by PareshDudhat on 12-03-2017.
 */

public class SuggestionAdapter extends RecyclerView.Adapter<SuggestionViewHolder> {
    private List<Suggestion> suggestions;

    public SuggestionAdapter(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public SuggestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_holder_suggestion, parent, false);
        return new SuggestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SuggestionViewHolder holder, int position) {
        Suggestion suggestion = suggestions.get(position);
        holder.tvTopic.setText(suggestion.getTopic());
        //holder.tvDate.setText(suggestion.getDate());
        holder.tvDetail.setText(suggestion.getDetail());
        //holder.tvStatus.setText(suggestion.getStatus());
    }

    @Override
    public int getItemCount() {
        return suggestions.size();
    }
}

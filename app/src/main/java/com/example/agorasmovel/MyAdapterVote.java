package com.example.agorasmovel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.square.android.expandabletextview.ExpandableTextView;


import java.util.List;

public class MyAdapterVote extends RecyclerView.Adapter {
    VoteActivity voteActivity;
    List<ItemVote> itensVote;

    public MyAdapterVote(VoteActivity voteActivity, List<ItemVote> itensVote){
        this.voteActivity = voteActivity;
        this.itensVote = itensVote;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(voteActivity);
        View v = null;
        v = inflater.inflate(R.layout.item_vote, parent, false);

        return new MyViewHolderVote(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemVote vote = itensVote.get(position);
        View v =holder.itemView;

        TextView tvtitleTheme = v.findViewById(R.id.tvtitleTheme);
        tvtitleTheme.setText(vote.Name);

        TextView exText_descDebate = v.findViewById(R.id.expand_text_viewDebates).findViewById(R.id.expandable_text);
        exText_descDebate.setText(vote.descDebate);

        //ExpandableTextView expTv2 = (ExpandableTextView) findViewById(R.id.expand_text_viewDebates);
        //expTv2.setText(dropdownText);

    }

    @Override
    public int getItemCount() {
        return itensVote.size();
    }
}

package com.example.agorasmovel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class VoteViewModel extends ViewModel {
    List<ItemVote> itensVote = new ArrayList<>();

    public VoteViewModel(){
        ItemVote i0 = new ItemVote();
        i0.Name = R.string.tvtitleTheme;
        i0.descDebate = R.string.tvdescTheme;
        i0.likes = 10;

        itensVote.add(i0);
    }
    public List<ItemVote> getVoteItens() {
        return itensVote;
    }
}

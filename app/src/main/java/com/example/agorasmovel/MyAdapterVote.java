package com.example.agorasmovel;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ms.square.android.expandabletextview.ExpandableTextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        tvtitleTheme.setText(vote.getTitulo());

        TextView tvdescDebate = v.findViewById(R.id.tvDescDebateVote);
        tvdescDebate.setText(vote.getDesc());

        TextView tvNameUser = v.findViewById(R.id.tvNomeUserVote);
        String s=vote.getNomeUser();
        s="Tema sugerido por: "+s;
        tvNameUser.setHint(s);

        final String login = Config.getLogin(v.getContext());
        final String id_tema = vote.getId_tema();

        holder.itemView.findViewById(R.id.imgbtLike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = Config.getLogin(v.getContext());
                final String id_tema = vote.getId_tema();

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "curtida.php", "POST", "UTF-8");
                        httpRequest.addParam("login",login);
                        httpRequest.addParam("id_tema",id_tema);

                        try{
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if(success==1){
                                voteActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(v.getContext(),"Voto carregado com sucesso", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }else{
                                final String error = jsonObject.getString("error");
                                voteActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(v.getContext(), error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }});


    }

    @Override
    public int getItemCount() {
        return itensVote.size();
    }
}
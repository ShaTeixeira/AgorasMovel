package com.example.agorasmovel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {

    HomeActivity homeActivity;
    List<Item> itens;

    public MyAdapter(HomeActivity homeActivity, List<Item> itens) {
        this.homeActivity = homeActivity;
        this.itens = itens;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(homeActivity);
        View v = null;
        if (viewType == 0){
            v = inflater.inflate(R.layout.item_argument, parent, false);
        }
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = itens.get(position);
        View v = holder.itemView;

        ImageView imgPhoto = v.findViewById(R.id.imgPhoto);
        imgPhoto.setImageResource(item.photoPerfil);

        TextView tvNameItem = v.findViewById(R.id.tvNameItem);
        tvNameItem.setText(item.nomePerfil);

        TextView tvContent = v.findViewById(R.id.tvContent);
        tvContent.setText(item.conteudo);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    @Override
    public int getItemViewType(int position) {
        return itens.get(position).tipo;
    }
}

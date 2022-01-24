package com.example.agorasmovel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapterComentario extends RecyclerView.Adapter {

    Context context;
    List<Comentario> comentarios;

    public MyAdapterComentario(HomeActivity context, List<Comentario> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.item_argument, parent, false);
        return new MyViewHolderComentario(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Comentario comentario = this.comentarios.get(position);

        ImageView imageView = holder.itemView.findViewById(R.id.imgPhoto);
        imageView.setImageBitmap(comentario.getPhotoPerfil());

        TextView tvNameItem = holder.itemView.findViewById(R.id.tvNameItem);
        tvNameItem.setText(comentario.getNomePerfil());

        TextView tvContent = holder.itemView.findViewById(R.id.tvContent);
        tvContent.setText(comentario.getConteudo());

        TextView tvUserName = holder.itemView.findViewById(R.id.tvUserNameItem);
        String s = comentario.getNameUser();
        tvUserName.setHint('@'+s);

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,HomeActivity.class);
                i.putExtra("id_comentario",comentario.getId_comentario());
                context.startActivity(i);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

}

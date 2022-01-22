package com.example.agorasmovel;

import android.graphics.Bitmap;

public class Comentario {

    Bitmap photoPerfil;
    String nomePerfil;
    String conteudo;
    int id_comentario;

    public Comentario(int id_comentario, String conteudo, String nomePerfil){
        this.id_comentario = id_comentario;
        this.conteudo = conteudo;
        this.nomePerfil = nomePerfil;
    }

    public Comentario(String conteudo, String nomePerfil, Bitmap photo, int id_comentario){
        this.id_comentario = id_comentario;
        this.conteudo = conteudo;
        this.photoPerfil = photo;
        this.nomePerfil = nomePerfil;
    }

    public Bitmap getPhotoPerfil(){return photoPerfil;}

    public String getNomePerfil(){return nomePerfil;}

    public String getConteudo(){return conteudo;}

    public int getId_comentario(){return id_comentario;}

}

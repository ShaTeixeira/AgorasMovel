package com.example.agorasmovel;

import android.graphics.Bitmap;

public class Comentario {

    Bitmap photoPerfil;
    String nomePerfil;
    String conteudo;
    String nomeUser;
    int id_comentario;

    public Comentario(int id_comentario, String conteudo, String nomePerfil){
        this.id_comentario = id_comentario;
        this.conteudo = conteudo;
        this.nomePerfil = nomePerfil;
        this.nomeUser = nomeUser;
    }

    public Comentario(String conteudo, String nomePerfil, Bitmap photo, int id_comentario, String nomeUser){
        this.id_comentario = id_comentario;
        this.conteudo = conteudo;
        this.photoPerfil = photo;
        this.nomePerfil = nomePerfil;
        this.nomeUser = nomeUser;
    }

    public Bitmap getPhotoPerfil(){return photoPerfil;}

    public String getNomePerfil(){return nomePerfil;}

    public String getConteudo(){return conteudo;}

    public int getId_comentario(){return id_comentario;}

    public String getNameUser(){return nomeUser;}

}

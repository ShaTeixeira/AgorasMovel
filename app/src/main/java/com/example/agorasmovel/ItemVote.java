package com.example.agorasmovel;

public class ItemVote {
    public String titulo;
    public String desc;
    public int likes;
    public String nomeUser;

    public ItemVote(String titulo,String desc,String nomeUser){
        this.titulo = titulo;
        this.desc = desc;
        this.nomeUser = nomeUser;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDesc() {
        return desc;
    }

    public int getLikes() {
        return likes;
    }

    public String getNomeUser() {
        return nomeUser;
    }
}
package com.example.agorasmovel;

public class ItemVote {
    public String titulo;
    public String desc;
    public int likes;
    public String nomeUser;
    public String id_tema;

    public ItemVote(String titulo,String desc,String nomeUser, String id_tema){
        this.titulo = titulo;
        this.desc = desc;
        this.nomeUser = nomeUser;
        this.id_tema = id_tema;
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

    public String getId_tema(){return id_tema;}
}
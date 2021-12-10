package com.example.agorasmovel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    List<Comentario> itens = new ArrayList<>();

    public HomeViewModel() {
        Comentario i0 = new Comentario();
        i0.photoPerfil = R.drawable.feliz;
        i0.nomePerfil = "Charles";
        i0.conteudo = "Não concordo";

        Comentario i1 = new Comentario();
        i1.photoPerfil = R.drawable.perfil;
        i1.nomePerfil = "Shayane";
        i1.conteudo = "Isso se da devido ao (...)";

        Comentario i2 = new Comentario();
        i2.photoPerfil = R.drawable.pessoas;
        i2.nomePerfil = "Lorena";
        i2.conteudo = "Já ouviram aquela do legiao urbana?";

        itens.add(i0);
        itens.add(i1);
        itens.add(i2);
    }

    public List<Comentario> getItens() {
        return itens;
    }
}

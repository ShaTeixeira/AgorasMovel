package com.example.agorasmovel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    List<Item> itens = new ArrayList<>();

    public HomeViewModel() {
        Item i0 = new Item();
        i0.photoPerfil = R.drawable.feliz;
        i0.nomePerfil = "Charles";
        i0.tipo = 1; // questao
        i0.conteudo = "Não concordo";

        Item i1 = new Item();
        i1.photoPerfil = R.drawable.perfil;
        i1.nomePerfil = "Shayane";
        i1.tipo = 2; // dados
        i1.conteudo = "Isso se da devido ao (...)";

        Item i2 = new Item();
        i2.photoPerfil = R.drawable.pessoas;
        i2.nomePerfil = "Lorena";
        i2.tipo = 0; // argumentos
        i2.conteudo = "Já ouviram aquela do legiao urbana?";

        itens.add(i0);
        itens.add(i1);
        itens.add(i2);
    }

    public List<Item> getItens() {
        return itens;
    }
}

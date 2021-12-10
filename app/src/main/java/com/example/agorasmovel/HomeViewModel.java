package com.example.agorasmovel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    List<Comentario> itens = new ArrayList<>();

    public HomeViewModel() {
        Comentario i0 = new Comentario();
        i0.photoPerfil = R.drawable.pfp_ex1;
        i0.nomePerfil = "Charles";
        i0.conteudo = "Não concordo";

        Comentario i1 = new Comentario();
        i1.photoPerfil = R.drawable.pfp_ex3;
        i1.nomePerfil = "Shayane";
        i1.conteudo = "A Associação Brasileira de Anunciantes (ABA) apresentou ao mercado o “Guia para Representação Responsável de Gênero na" +
                "Publicidade”, que busca inspirar profissionais de marketing a trabalharem com menos estereótipos em suas campanhas, uma discussão que está" +
                "cada vez mais ganhando relevância global, com a adesão de grandes anunciantes como Unilever, P&G, Mars, Diageo, J&J e Mattel.";

        Comentario i2 = new Comentario();
        i2.photoPerfil = R.drawable.pfp_ex2;
        i2.nomePerfil = "Lorena";
        i2.conteudo = "Já ouviram aquela do legiao urbana?";

        Comentario i3 = new Comentario();
        i3.photoPerfil = R.drawable.pfp_ex4;
        i3.nomePerfil = "João";
        i3.conteudo = "É importante a discussão desse tópico, por que sofremos hoje devido a falta de acesso a informação e (...)";

        itens.add(i0);
        itens.add(i1);
        itens.add(i2);
        itens.add(i3);
    }

    public List<Comentario> getItens() {
        return itens;
    }
}

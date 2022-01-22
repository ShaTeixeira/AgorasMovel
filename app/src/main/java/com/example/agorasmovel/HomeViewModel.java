package com.example.agorasmovel;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeViewModel extends AndroidViewModel {

    //armazena a lista de comentarios
    MutableLiveData<List<Comentario>> comentarios;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Comentario>> getComentarios(){
        if(comentarios==null){
            comentarios = new MutableLiveData<List<Comentario>>();
            loadComentarios();
        }
        return comentarios;
    }

    //recarregar
    public void refreshProducts(){
        loadComentarios();
    }

    /*public HomeViewModel() {
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
    }*/

    //conexão com o servidor
    void loadComentarios(){

        final String login = Config.getLogin(getApplication());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Comentario> comentariosList = new ArrayList<>();

                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "get_all_coments.php", "POST", "UTF-8");
                httpRequest.addParam("login",login);

                try {
                   InputStream is = httpRequest.execute();
                   String result = Util.inputStream2String(is, "utf-8");
                    httpRequest.finish();
                    Log.d("HTTP_REQUEST_RESULT", result);

                    //arquivo em json
                    JSONObject jsonObject = new JSONObject(result);
                    int success = jsonObject.getInt("success");
                    if(success == 1){
                        JSONArray jsonArray = jsonObject.getJSONArray("comentario");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jProduct = jsonArray.getJSONObject(i);

                            int id_comentario = jProduct.getInt("id_comentario");
                            String texto = jProduct.getString("comentario");
                            String nomePerfil = jProduct.getString("nomePerfil");

                            String imgBase64 = jProduct.getString("img");
                            String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",")+1);
                            Bitmap img = Util.base642Bitmap((pureBase64Encoded));

                            //criando um produto
                            Comentario comentario = new Comentario(texto, nomePerfil, img, id_comentario);
                            comentariosList.add(comentario);
                        }
                        //armazenar nova list
                        comentarios.postValue(comentariosList);
                    }
                    ;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /*dados do comentario, valores da classe comentario.java

    String nomePerfil = jComentario.getString("nomePerfil");
    String conteudo = jComentario.getString("conteudo");

    //imagem  em base 64
    //String imgBase64 = jComentario.getString("img");
    //pegar primera parte da string
    //String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",") + 1);
    //Bitmap img = Util.base642Bitmap(pureBase64Encoded);

    //Comentario c = new Comentario(nomePerfil, conteudo);
    //itens.postValue(c);
    //Product p = new Product(name, price, description, img);
    //product.postValue(p);*/
}

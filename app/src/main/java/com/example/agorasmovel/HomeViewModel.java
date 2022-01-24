package com.example.agorasmovel;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

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

    //conexão com o servidor
    void loadComentarios(){

        final String login = Config.getLogin(getApplication());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Comentario> comentariosList = new ArrayList<>();

                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "get_all_coments.php", "GET", "UTF-8");
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
                            String nomeUser = jProduct.getString("nomeUser");

                            //imagem  em base 64
                            String imgBase64 = jProduct.getString("img");
                            // pegar primera parte da string
                            String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",")+1);
                            Bitmap img = Util.base642Bitmap((pureBase64Encoded));

                            //criando um produto
                            Comentario comentario = new Comentario(texto, nomePerfil, img, id_comentario, nomeUser);
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

}

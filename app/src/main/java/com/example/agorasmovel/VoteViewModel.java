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

public class VoteViewModel extends AndroidViewModel {
    MutableLiveData<List<ItemVote>> itemVote;

    public VoteViewModel(@NonNull Application application){super(application);}

    public LiveData<List<ItemVote>> getItemVote(){
        if(itemVote==null){
            itemVote = new MutableLiveData<List<ItemVote>>();
            loadVotes();
        }
        return itemVote;
    }

    //recarregar
    public void refreshVotes(){ loadVotes(); }

    void loadVotes(){
        final String login = Config.getLogin(getApplication());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<ItemVote> itemVoteList = new ArrayList<>();

                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "get_all_temas.php", "GET", "UTF-8");
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
                        JSONArray jsonArray = jsonObject.getJSONArray("tema");
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject jProduct = jsonArray.getJSONObject(i);

                            //int id_tema = jProduct.getInt("id_tema");
                            String titulo = jProduct.getString("titulo");
                            String desc = jProduct.getString("desc");
                            String nomeUser= jProduct.getString("nomeUsuario");
                            //likes

                            //criando um tema
                            ItemVote itemVote = new ItemVote(titulo,desc,nomeUser);
                            itemVoteList.add(itemVote);
                        }
                        //armazenar nova list
                        itemVote.postValue(itemVoteList);
                    }
                    ;
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /*public VoteViewModel(){
        ItemVote i0 = new ItemVote();
        i0.Name = R.string.tvtitleTheme;
        i0.descDebate = R.string.tvdescTheme;
        i0.likes = 10;

        ItemVote i1 = new ItemVote();
        i1.Name = R.string.tvtitleTheme2;
        i1.descDebate = R.string.tvdescTheme2;
        i1.likes = 5;

        itensVote.add(i0);
        itensVote.add(i1);
    }*/
}

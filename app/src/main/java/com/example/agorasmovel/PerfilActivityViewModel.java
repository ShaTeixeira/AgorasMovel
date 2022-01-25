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
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerfilActivityViewModel extends AndroidViewModel {

    public LiveData<String> getNamelv() {
        return namelv;
    }

    public LiveData<String> getUserNamelv() {
        return userNamelv;
    }

    public LiveData<String> getBiolv() {
        return biolv;
    }

    public LiveData<Bitmap> getImglv() {
        return imglv;
    }

    public LiveData<String> getDatelv(){return datelv;}

    MutableLiveData<String> namelv;
    MutableLiveData<String> userNamelv;
    MutableLiveData<String> biolv;
    MutableLiveData<Bitmap> imglv;
    MutableLiveData<String> datelv;

    public PerfilActivityViewModel(@NonNull Application application) {
        super(application);
        namelv = new MutableLiveData<>();
        userNamelv = new MutableLiveData<>();
        biolv = new MutableLiveData<>();
        imglv = new MutableLiveData<>();
        datelv = new MutableLiveData<>();
    }

    void loadPerfil(){
        final String login = Config.getLogin(getApplication());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "get_perfil.php", "GET", "UTF-8");
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
                            String name = jsonObject.getString("name");
                            String userName = jsonObject.getString("userName");
                            String bio = jsonObject.getString("bio");
                            String ingressou = jsonObject.getString("dataIngresso");

                            //imagem  em base 64
                            String imgBase64 = jsonObject.getString("img");
                            // pegar primera parte da string
                            String pureBase64Encoded = imgBase64.substring(imgBase64.indexOf(",")+1);
                            Bitmap img = Util.base642Bitmap((pureBase64Encoded));

                            namelv.postValue(name);
                            userNamelv.postValue(userName);
                            biolv.postValue(bio);
                            imglv.postValue(img);
                            datelv.postValue(ingressou);

                        }
                    } catch (JSONException jsonException) {
                    jsonException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                ;
            }
        });
    }
}

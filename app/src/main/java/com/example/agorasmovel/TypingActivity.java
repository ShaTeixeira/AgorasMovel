package com.example.agorasmovel;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TypingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typing);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TypingActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Button btnSendMessage = findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etcomentario = findViewById(R.id.etMessage);
                final String comentario = etcomentario.getText().toString();
                if(comentario.isEmpty()){
                    Toast.makeText(TypingActivity.this, "Digite um comentario", Toast.LENGTH_LONG).show();
                    return;
                }

                final String login = Config.getLogin(TypingActivity.this);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "comentario.php", "POST","UTF-8");
                        httpRequest.addParam("comentario",comentario);
                        httpRequest.addParam("login",login);

                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if (success==1){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Config.setComentario(TypingActivity.this, comentario);
                                        Toast.makeText(TypingActivity.this,"Comentario publicado com sucesso", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(TypingActivity.this, HomeActivity.class);
                                        startActivity(i);
                                    }
                                });
                            }else{
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(TypingActivity.this, error, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                        catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent i;
        switch (item.getItemId()){
            case R.id.perfil:
                i = new Intent(TypingActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;
            case R.id.vote:
                i = new Intent(TypingActivity.this, VoteActivity.class);
                startActivity(i);
                return true;

            case R.id.tema:
                i = new Intent(TypingActivity.this, SuggestionActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(TypingActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(TypingActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
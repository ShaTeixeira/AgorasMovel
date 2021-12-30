package com.example.agorasmovel;

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

public class SuggestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SuggestionActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Button btnSendTheme = findViewById(R.id.btnSendTheme);
        btnSendTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etTheme = findViewById(R.id.etSuggestionTheme);
                final String titulo = etTheme.getText().toString();
                if(titulo.isEmpty()){
                    Toast.makeText(SuggestionActivity.this, "De um titulo ao seu tema", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etDesc = findViewById(R.id.etDescTheme);
                final String descricao = etDesc.getText().toString();
                if(descricao.isEmpty()){
                    Toast.makeText(SuggestionActivity.this, "Faça uma breve descrição do tema", Toast.LENGTH_LONG).show();
                    return;
                }

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "tema.php", "POST", "UTF-8");
                        httpRequest.addParam("titulo",titulo);
                        httpRequest.addParam("descricao",descricao);

                        try{
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if(success == 1){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Config.setTitulo(SuggestionActivity.this, titulo);
                                        Config.setDesc(SuggestionActivity.this, descricao);
                                        Toast.makeText(SuggestionActivity.this,"Sugestão enviada com sucesso", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(SuggestionActivity.this, HomeActivity.class);
                                        startActivity(i);
                                    }
                                });
                            }
                            else{
                                final String error = jsonObject.getString("error");
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SuggestionActivity.this, error, Toast.LENGTH_LONG).show();
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
                i = new Intent(SuggestionActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;
            case R.id.vote:
                i = new Intent(SuggestionActivity.this, VoteActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(SuggestionActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(SuggestionActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
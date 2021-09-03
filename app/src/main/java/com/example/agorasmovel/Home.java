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

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.tbHome);
        setSupportActionBar(toolbar);

        Button button = findViewById(R.id.btVotar);
        /*PARA O BOTAO MUDAR DE PAGINA SUBSTITUA O "VOTAR" PELO NOME DA ACTIVITY
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, VOTAR.class);
                startActivity(i);
            }
        });*/

        Button button2 = findViewById(R.id.btDesc);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Debate.class);
                startActivity(i);
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
        switch (item.getItemId()){
            case R.id.perfil:
                //caso o botao perfil seja selecionado na toolbar
                return true;

            case R.id.tema:
                //caso sugestao de tema
                return true;

            case R.id.oldDebate:
                //debates antigos
                return true;

            case R.id.ajuda:
                //ajuda

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
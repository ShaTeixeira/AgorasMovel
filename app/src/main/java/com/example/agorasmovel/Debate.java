package com.example.agorasmovel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Debate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debate);
        Toolbar toolbar = findViewById(R.id.tbDebate);
        setSupportActionBar(toolbar);
        TextView yourTextView=findViewById(R.id.tvDescDebate);
        yourTextView.setMovementMethod(new ScrollingMovementMethod());
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
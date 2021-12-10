package com.example.agorasmovel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        String dropdownText = getResources().getString(R.string.tvdescTheme);
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view).findViewById(R.id.expand_text_view);
        expTv1.setText(dropdownText);


        HomeViewModel vm = new ViewModelProvider(this).get(HomeViewModel.class);
        List<Comentario> itens = vm.getItens();

        MyAdapter myAdapter = new MyAdapter(this, itens);

        RecyclerView rvDebates = findViewById(R.id.rvDebates);
        rvDebates.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvDebates.setLayoutManager(layoutManager);

        rvDebates.setAdapter(myAdapter);


        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, TypingActivity.class);
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
        Intent i;
        switch (item.getItemId()){
            case R.id.perfil:
                i = new Intent(HomeActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;

            case R.id.vote:
                i = new Intent(HomeActivity.this, VoteActivity.class);
                startActivity(i);
                return true;

            case R.id.tema:
                 i = new Intent(HomeActivity.this, SuggestionActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(HomeActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(HomeActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
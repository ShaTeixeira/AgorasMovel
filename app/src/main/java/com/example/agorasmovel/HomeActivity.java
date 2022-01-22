package com.example.agorasmovel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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
        ExpandableTextView expTv1 = (ExpandableTextView) findViewById(R.id.expand_text_view);
        expTv1.setText(dropdownText);

        RecyclerView rvDebates = findViewById(R.id.rvDebates);
        rvDebates.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvDebates.setLayoutManager(layoutManager);

        HomeViewModel vm = new ViewModelProvider(this).get(HomeViewModel.class);
        LiveData<List<Comentario>> comentarios = vm.getComentarios();

        comentarios.observe(this, new Observer<List<Comentario>>() {
            @Override
            public void onChanged(List<Comentario> comentarios) {
                //nova lista de comentarios
                MyAdapterComentario myAdapterComentario = new MyAdapterComentario(HomeActivity.this,comentarios);
                rvDebates.setAdapter(myAdapterComentario);
            }
        });

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

    static int ADD_PRODUCT_ACTIVITY_RESULT = 2;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_PRODUCT_ACTIVITY_RESULT){
            if(resultCode == Activity.RESULT_OK){
                HomeViewModel mainViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
                mainViewModel.refreshProducts();
            }
        }
    }
}
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
import android.widget.Button;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

public class VoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        VoteViewModel vtm = new ViewModelProvider(this).get(VoteViewModel.class);
        List<ItemVote> itensVote = vtm.getVoteItens();

        MyAdapterVote myAdapterVote = new MyAdapterVote(this,itensVote);

        RecyclerView rvVote = findViewById(R.id.rvVote);
        rvVote.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvVote.setLayoutManager(layoutManager);

        rvVote.setAdapter(myAdapterVote);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VoteActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });


        Button button = findViewById(R.id.btnVote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VoteActivity.this, HomeActivity.class);
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
                i = new Intent(VoteActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;

            case R.id.tema:
                i = new Intent(VoteActivity.this, SuggestionActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(VoteActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(VoteActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
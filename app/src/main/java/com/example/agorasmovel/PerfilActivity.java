package com.example.agorasmovel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        /* Atualizar perfil */

        PerfilActivityViewModel vm = new ViewModelProvider(this).get(PerfilActivityViewModel.class);
        vm.loadPerfil();
        LiveData<String> namelv = vm.getNamelv();
        namelv.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView tvName = findViewById(R.id.tvNameUser);
                tvName.setText(s);
            }
        });

        LiveData<String> userNamelv = vm.getUserNamelv();
        userNamelv.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView tvName = findViewById(R.id.tvName);
                tvName.setText(s);
            }
        });

        LiveData<String> biolv = vm.getBiolv();
        biolv.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                TextView tvName = findViewById(R.id.etBio);
                tvName.setText(s);
            }
        });

        LiveData<Bitmap> imglv = vm.getImglv();
        imglv.observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(Bitmap bitmap) {
                ImageView imvImg = findViewById(R.id.imgPhotoPerfil);
                imvImg.setImageBitmap(bitmap);
            }
        });


        Button btnEditPassword = findViewById(R.id.btnEditPassword);
        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilActivity.this, EditPasswordActivity.class);
                startActivity(i);
            }
        });

        ImageButton imEditPerfil = findViewById(R.id.imEditPerfil);
        imEditPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PerfilActivity.this, EditPerfilActivity.class);
                startActivity(i);
            }
        });


        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.setLogin(PerfilActivity.this, "");
                Config.setPassword(PerfilActivity.this, "");
                Intent i = new Intent(PerfilActivity.this, LoginActivity.class);
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
            case R.id.tema:
                i = new Intent(PerfilActivity.this, SuggestionActivity.class);
                startActivity(i);
                return true;
            case R.id.vote:
                i = new Intent(PerfilActivity.this, VoteActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(PerfilActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(PerfilActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
package com.example.agorasmovel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

public class EditPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassword);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditPasswordActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etOldPass = findViewById(R.id.etOldPassword);
                final String oldPass = etOldPass.getText().toString();
                if(oldPass.isEmpty()){
                    Toast.makeText(EditPasswordActivity.this, "Campo de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPass = findViewById(R.id.etNewPassword);
                final String newPass = etNewPass.getText().toString();
                if(newPass.isEmpty()){
                    Toast.makeText(EditPasswordActivity.this, "Campo de nova senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etNewPassCheck = findViewById(R.id.etSaveNewPassaword);
                final String passCheck = etNewPassCheck.getText().toString();
                if(passCheck.isEmpty()){
                    Toast.makeText(EditPasswordActivity.this, "Campo de confirmação de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!newPass.equals(passCheck)){
                    Toast.makeText(EditPasswordActivity.this, "Senha não confere", Toast.LENGTH_LONG).show();
                    return;
                }

                // continuar aqui

                Intent i = new Intent(EditPasswordActivity.this, PerfilActivity.class);
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
                i = new Intent(EditPasswordActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;

            case R.id.tema:
                i = new Intent(EditPasswordActivity.this, SuggestionActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(EditPasswordActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(EditPasswordActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
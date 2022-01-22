package com.example.agorasmovel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditPerfilActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editperfil);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        ImageButton imgEditPhoto = findViewById(R.id.imgEditPhoto);
        PhotoViewModel photoViewModel = new ViewModelProvider(this).get(PhotoViewModel.class);
        String currentPhotoPath = photoViewModel.getCurrentPhotoPath();
        //verificar se tem foto
        if(!currentPhotoPath.isEmpty()){
            ImageView imvPhoto = findViewById(R.id.imgEditPhoto);
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, imvPhoto.getWidth(), imvPhoto.getHeight());
            imvPhoto.setImageBitmap(bitmap);
        }

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditPerfilActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        Button btnPopup = findViewById(R.id.btnPop);
        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditPerfilActivity.this,Pop.class));

            }
        });

        Button btnEditSave = findViewById(R.id.btnEditSave);
        btnEditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                v.setEnabled(false);


                EditText etEditName = findViewById(R.id.etEditName);
                final String editName = etEditName.getText().toString();
                if(editName.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de nome não preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etEditUserName= findViewById(R.id.etEditUserName);
                final String editUser = etEditUserName.getText().toString();
                if(editUser.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de usuario não preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                EditText etEditEmail = findViewById(R.id.etEditEmail);
                final String editEmail = etEditEmail.getText().toString();
                if(editEmail.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }


                EditText etEditBio = findViewById(R.id.etEditBio);
                final String editBio = etEditBio.getText().toString();
                if(editBio.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de bio não preenchido", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }

                //VERIFICA SE A FOTO FOI ADICIONADA
                String currentPhotoPath = photoViewModel.getCurrentPhotoPath();
                if(currentPhotoPath.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Não foi enviada uma imagem", Toast.LENGTH_LONG).show();
                    v.setEnabled(true);
                    return;
                }


                //escalou a imagem antes
                try {
                    Util.scaleImage(currentPhotoPath, 1000, 300);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                final String login = Config.getLogin(EditPerfilActivity.this);

                ExecutorService executorService = Executors.newSingleThreadExecutor();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpRequest httpRequest = new HttpRequest(Config.SERVER_URL_BASE + "editPerfil.php", "POST", "UTF-8");
                        httpRequest.addParam("editName", editName);
                        httpRequest.addParam("editUser", editUser);
                        httpRequest.addParam("editEmail",editEmail);
                        httpRequest.addParam("editBio", editBio);
                        httpRequest.addFile("editImg", new File(currentPhotoPath));
                        httpRequest.addParam("login",login);


                        try {
                            InputStream is = httpRequest.execute();
                            String result = Util.inputStream2String(is, "UTF-8");
                            httpRequest.finish();

                            JSONObject jsonObject = new JSONObject(result);
                            final int success = jsonObject.getInt("success");
                            if(success==1){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Config.setLogin(EditPerfilActivity.this, editEmail);
                                        Toast.makeText(EditPerfilActivity.this,"Perfil atualizado com sucesso", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(EditPerfilActivity.this, PerfilActivity.class);
                                        startActivity(i);
                                    }
                                });
                            }
                        else{
                            final String error = jsonObject.getString("error");
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EditPerfilActivity.this, error, Toast.LENGTH_LONG).show();
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
                i = new Intent(EditPerfilActivity.this, PerfilActivity.class);
                startActivity(i);
                return true;

            case R.id.vote:
                i = new Intent(EditPerfilActivity.this, VoteActivity.class);
                startActivity(i);
                return true;

            case R.id.tema:
                i = new Intent(EditPerfilActivity.this, SuggestionActivity.class);
                startActivity(i);
                return true;

            case R.id.oldDebate:
                i = new Intent(EditPerfilActivity.this, OldDebatesActivity.class);
                startActivity(i);
                return true;

            case R.id.ajuda:
                i = new Intent(EditPerfilActivity.this, HelpActivity.class);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
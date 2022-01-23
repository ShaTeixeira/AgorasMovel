package com.example.agorasmovel;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditPerfilActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST =0;
    //private static final int RESULT_LOAD_IMAGE = 1;
    public static final int PICK_IMAGE = 1;
    static int RESULT_TAKE_PICTURE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST);
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editperfil);

        Toolbar toolbar = findViewById(R.id.tbMain);
        setSupportActionBar(toolbar);

        EditPerfilViewModel editPerfilViewModel = new ViewModelProvider(this).get(EditPerfilViewModel.class);
        String currentPhotoPath = editPerfilViewModel.getCurrentPhotoPath();
        //verificar se tem foto
        if(!currentPhotoPath.isEmpty()){
            ImageView imvPhoto = findViewById(R.id.imgEditPhoto);
            Bitmap bitmap = Util.getBitmap(currentPhotoPath);
            imvPhoto.setImageBitmap(bitmap);
        }else{
            File imageFile = null;
            try {
                imageFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            editPerfilViewModel.setCurrentPhotoPath(imageFile.getAbsolutePath());
        }

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

                EditText etEditName = findViewById(R.id.etEditName);
                final String editName = etEditName.getText().toString();
                if(editName.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de nome não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etEditUserName= findViewById(R.id.etEditUserName);
                final String editUser = etEditUserName.getText().toString();
                if(editUser.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de usuario não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText etEditEmail = findViewById(R.id.etEditEmail);
                final String editEmail = etEditEmail.getText().toString();
                if(editEmail.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }


                EditText etEditBio = findViewById(R.id.etEditBio);
                final String editBio = etEditBio.getText().toString();
                if(editBio.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Campo de bio não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                //VERIFICA SE A FOTO FOI ADICIONADA
                String currentPhotoPath = editPerfilViewModel.getCurrentPhotoPath();
                if(currentPhotoPath.isEmpty()){
                    Toast.makeText(EditPerfilActivity.this, "Não foi enviada uma imagem", Toast.LENGTH_LONG).show();
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

        ImageView imvLoadPhoto = findViewById(R.id.imgEditPhoto);
        imvLoadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });
    }

    //salvar arquivos
    private File createImageFile() throws IOException {
        //data para salvar, para não salvar as fotos por cima de outras
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storegeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storegeDir); //vai armazenar foto em extensao jpg
        return f; //retorna a foto
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permissões concedidas", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"Permissões não concedidas", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE){
            EditPerfilViewModel perfilViewModel = new ViewModelProvider(this).get(EditPerfilViewModel.class);
            String currentPhotoPath = perfilViewModel.getCurrentPhotoPath();
            //VERIFICAR SE FOI ENVIADA
            if(resultCode == Activity.RESULT_OK){
                Uri selectedImage = data.getData();
                ImageView imvLoadPhoto = findViewById(R.id.imgEditPhoto);
                Bitmap bitmap = null;
                try {
                    bitmap = Util.getBitmap(this,selectedImage);
                    imvLoadPhoto.setImageBitmap(bitmap);
                    Util.saveImage(bitmap,currentPhotoPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
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
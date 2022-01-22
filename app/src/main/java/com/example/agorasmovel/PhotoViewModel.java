package com.example.agorasmovel;

import androidx.lifecycle.ViewModel;

public class PhotoViewModel extends ViewModel {

    String currentPhotoPath = "" ;

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }
}
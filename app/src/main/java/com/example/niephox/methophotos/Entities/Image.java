package com.example.niephox.methophotos.Entities;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.drew.metadata.Metadata;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by Niephox on 3/30/2018.
 */

public class Image {

    public String storageLocationURL;
    public String downloadUrl;
    public String imageURI;
    public String name;
    public Album album;
    public Metadata metadata;
    public String description;
    ArrayList<String> imagesPath;



    public ArrayList<String> getImagesPath() {
        return imagesPath;
    }

    public void setImagesPath(ArrayList<String> imagesPath) {
        this.imagesPath = imagesPath;
    }

    public Image(String storageLocationURL, String downloadUrl, String name, Album album, Metadata metadata, String description) {
        this.storageLocationURL = storageLocationURL;
        this.downloadUrl = downloadUrl;
        this.name = name;
        this.album = album;
        this.metadata = metadata;
        this.description = description;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode(){
        //this.name = Objects.hashCode(this.imageURI);
        return 0;
    }
    public Image(String imageURI){
        this.imageURI = imageURI;
    }

    public Image (){}

    public Image(String storageLocationURL, String downloadUrl, String description) {
        this.storageLocationURL = storageLocationURL;
        this.downloadUrl = downloadUrl;
        this.description = description;
    }

    public String  getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        this.imageURI = imageURI;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    public String getStorageLocationURL() {
        return storageLocationURL;
    }

    public void setStorageLocationURL(String storageLocationURL) {
        this.storageLocationURL = storageLocationURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Metadata getMetadata() {
        return metadata;
    }



    public void setMetadata(Metadata metadata) {

        this.metadata = metadata;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

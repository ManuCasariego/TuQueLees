package com.scrapp.manuel.tuquelees.util;

import android.graphics.Bitmap;

/**
 * Created by Manuel on 07/05/2015.
 */
public class Book {
    private String title, autor, link, rating, image;



    private Bitmap imageLoad;

    public Book() {
    }

    //<editor-fold desc="GETTERS AND SETTERS">
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    //</editor-fold>

    public Bitmap getImageLoad() {
        return imageLoad;
    }

    public void setImageLoad(Bitmap imageLoad) {
        this.imageLoad = imageLoad;
    }
}

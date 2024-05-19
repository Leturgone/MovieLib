package com.example.cursework2_2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public class Movie {
    private int _id;
    private String movie_title;
    private String movie_director;
    private String movie_year;
    private String movie_description;
    private Bitmap movie_poster;
    private String movie_length;
    private String movie_genre;

    public Movie(int _id, String movie_title, String movie_director, String movie_year, String movie_description, Bitmap movie_poster, String movie_length, String movie_genre) {
        this._id = _id;
        this.movie_title = movie_title;
        this.movie_director = movie_director;
        this.movie_year = movie_year;
        this.movie_description = movie_description;
        this.movie_poster = movie_poster;
        this.movie_length = movie_length;
        this.movie_genre = movie_genre;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_director() {
        return movie_director;
    }

    public void setMovie_director(String movie_director) {
        this.movie_director = movie_director;
    }

    public String getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(String movie_year) {
        this.movie_year = movie_year;
    }

    public String getMovie_description() {
        return movie_description;
    }

    public void setMovie_description(String movie_description) {
        this.movie_description = movie_description;
    }

    public Bitmap getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(ImageView movie_poster) {
        BitmapDrawable drawable = (BitmapDrawable) movie_poster.getDrawable();
        this.movie_poster = drawable.getBitmap();
    }
    public void setMovie_poster(Bitmap movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getMovie_length() {
        return movie_length;
    }

    public void setMovie_length(String movie_length) {
        this.movie_length = movie_length;
    }

    public String getMovie_genre() {
        return movie_genre;
    }

    public void setMovie_genre(String movie_genre) {
        this.movie_genre = movie_genre;
    }
}

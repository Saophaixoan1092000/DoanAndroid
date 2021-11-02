package com.example.daltdd.Activity.base;

import android.widget.ImageView;

import com.example.daltdd.Activity.model.MoviePopular;
import com.example.daltdd.Activity.model.MovieUpcoming;

public interface IMovieOnLickListener {
    void onMovieClick(MoviePopular moviePopular, ImageView imageView);
    void onMovieClick1(MovieUpcoming movieUpcoming, ImageView imageView);
}

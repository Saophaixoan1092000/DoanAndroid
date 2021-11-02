package com.example.daltdd.Activity.network;

import com.example.daltdd.Activity.base.PathAPI;
import com.example.daltdd.Activity.model.GuestModel;
import com.example.daltdd.Activity.model.ListMoviePopular;
import com.example.daltdd.Activity.model.ListMovieUpcoming;
import com.example.daltdd.Activity.model.ListVideoInfo;


import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface HomeApiService {


    @GET("/authentication/guest_session/new?api_key="+PathAPI.API_KEY+"&language=vi-VN")
    Call<GuestModel> createGuest(
    );

    @GET("movie/popular?api_key="+PathAPI.API_KEY+"&language=vi-VN")
    Call<ListMoviePopular> getMoviePopular();


    @GET("movie/{movie_id}/recommendations?api_key="+PathAPI.API_KEY+"&language=vi-VN&page=1")
    Call<ListMoviePopular> getMovieRecommendById(
        @Path("movie_id") int id
    );

    @GET("movie/{movie_id}/videos?api_key="+PathAPI.API_KEY+"&language=en-US")
    Call<ListVideoInfo> getIdYoutubeByIdMovie(
        @Path("movie_id")int id
    );

    @GET("search/movie?api_key="+PathAPI.API_KEY+"&language=vi-VN")
    Call<ListMoviePopular> searchMovieByName(
            @Query("query") String query,
            @Query("page") int page
    );

    @GET("movie/upcoming?api_key="+PathAPI.API_KEY+"&language=vi-VN")
    Call<ListMovieUpcoming> getMovieUpcoming(
    );

}

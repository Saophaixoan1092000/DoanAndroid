package com.example.daltdd.Activity.screen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.daltdd.Activity.adapter.MovieRecyclerAdapter;
import com.example.daltdd.Activity.adapter.MovieUpcomingAdapter;
import com.example.daltdd.Activity.base.IMovieOnLickListener;
import com.example.daltdd.Activity.model.ListMoviePopular;
import com.example.daltdd.Activity.model.ListMovieUpcoming;
import com.example.daltdd.Activity.model.ListVideoInfo;
import com.example.daltdd.Activity.model.MoviePopular;
import com.example.daltdd.Activity.model.MovieUpcoming;
import com.example.daltdd.Activity.network.HomeApi;
import com.example.daltdd.Activity.network.HomeApiService;
import com.example.daltdd.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMovieActivity extends AppCompatActivity implements IMovieOnLickListener {

    TextView textViewPopularity;
    TextView textViewVoteCount;
    TextView textViewVoteAverage;

    TextView textViewNameMovie;
    TextView textViewCreateDay;
    TextView textViewViewCount;

    TextView textViewOverview;

    ImageView imageViewNavigatorLeading;
     RecyclerView MoviewRV;
    RecyclerView MoviewRVUpcoming;
     List<MoviePopular> listMoviewPopular;
     List<MovieUpcoming> listMoviewUpcoming;
     HomeApiService homeApiProvider;
     int idMovie;
     String idVideoYotube;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        final Intent intent = getIntent();



        String movieTitle =intent.getExtras().getString("title");
        String imageUrl = intent.getExtras().getString("image");
        String backgroundVideo = intent.getExtras().getString("backgroundVideo");
        ImageView imageView = findViewById(R.id.detailImageMovie);
        ImageView viewVideoImg = findViewById(R.id.viewVideoDetail);
        FloatingActionButton floatingActionButton = findViewById(R.id.floatbutton);
        bindingData();


        textViewPopularity.setText(String.valueOf(intent.getExtras().getDouble("popularity")));
        textViewVoteCount.setText(String.valueOf(intent.getExtras().getInt("vote_count")) );
        textViewVoteAverage.setText(String.valueOf(intent.getExtras().getDouble("vote_average")) );

        textViewNameMovie.setText(intent.getExtras().getString("name_movie"));
        textViewCreateDay.setText(intent.getExtras().getString("create_day"));
        textViewViewCount.setText(textViewPopularity.getText());


        textViewOverview.setText(intent.getExtras().getString("overview"));

        idMovie = intent.getExtras().getInt("idmovie");

        //Toast.makeText(this,movieTitle,Toast.LENGTH_LONG).show();
        Glide.with(this).load("https://image.tmdb.org/t/p/original/"+imageUrl)
                .placeholder(R.drawable.progess_animation)

                .centerCrop()
                .into(imageView);
        Glide.with(this).load("https://image.tmdb.org/t/p/original/"+backgroundVideo)

                .centerCrop()
                .into(viewVideoImg);

        imageViewNavigatorLeading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        addRecommendMovie();
        getIdVideoByIdMovie();

        viewVideoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getBaseContext(),VideoPlayActivity.class);
                intent1.putExtra("idVideo",idVideoYotube);

                startActivity(intent1);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getBaseContext(),VideoPlayActivity.class);
                intent1.putExtra("idVideo",idVideoYotube);

                startActivity(intent1);
            }
        });

    }
    void bindingData(){
        textViewPopularity = findViewById(R.id.popularity);
        textViewVoteCount=findViewById(R.id.vote_count);
        textViewVoteAverage= findViewById(R.id.vote_average);

        textViewNameMovie= findViewById(R.id.nameMovie);
        textViewCreateDay = findViewById(R.id.createDay);
        textViewViewCount = findViewById(R.id.viewCount);

        textViewOverview= findViewById(R.id.overview);

        imageViewNavigatorLeading = findViewById(R.id.navigatorLeading);

    }

    void addRecommendMovie(){
        listMoviewPopular = new ArrayList<>();
        listMoviewUpcoming = new ArrayList<>();
        final MovieRecyclerAdapter movieRecyclerAdapter = new MovieRecyclerAdapter(getBaseContext(),listMoviewPopular,this);
        final MovieUpcomingAdapter movieRecyclerAdapter1 = new MovieUpcomingAdapter(getBaseContext(),listMoviewUpcoming,this);
        homeApiProvider = HomeApi.getHomeApiProvider();

        homeApiProvider.getMovieRecommendById(idMovie).enqueue(
                new Callback<ListMoviePopular>() {
                    @Override
                    public void onResponse(Call<ListMoviePopular> call, Response<ListMoviePopular> response) {
                        ListMoviePopular res = response.body();

                        for(int i=0 ;i<res.getResults().size();i++){
                            if(res.getResults().get(i).getOverview().equals("")){

                            } else{
                               
                                listMoviewPopular.add(new MoviePopular(res.getResults().get(i)));
                            }
                        }

                        MoviewRV = findViewById(R.id.recycler_recommend_movies);

                        MoviewRV.setAdapter(movieRecyclerAdapter);

                        MoviewRV.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
                    }

                    @Override
                    public void onFailure(Call<ListMoviePopular> call, Throwable t) {
                        Log.d("AA",t.getMessage());
                    }
                }
        );


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMovieClick(MoviePopular moviePopular, ImageView imageView) {
        Intent intent = new Intent(this,DetailMovieActivity.class);

        intent.putExtra("title",moviePopular.getTitle());
        intent.putExtra("image",moviePopular.getPosterPath());
        intent.putExtra("backgroundVideo",moviePopular.getBackdropPath());

        intent.putExtra("popularity",moviePopular.getPopularity());
        intent.putExtra("vote_count",moviePopular.getVoteCount());
        intent.putExtra("vote_average",moviePopular.getVoteAverage());

        intent.putExtra("name_movie",moviePopular.getTitle());
        intent.putExtra("create_day",moviePopular.getReleaseDate());

        intent.putExtra("overview",moviePopular.getOverview());

        intent.putExtra("idmovie",moviePopular.getId());

        startActivity(intent);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,imageView,"sharedName");
        startActivity(intent,options.toBundle());
    }

    @Override
    public void onMovieClick1(MovieUpcoming movieUpcoming, ImageView imageView) {
        Intent intent = new Intent(this,DetailMovieActivity.class);

        intent.putExtra("title",movieUpcoming.getTitle());
        intent.putExtra("image",movieUpcoming.getPosterPath());
        intent.putExtra("backgroundVideo",movieUpcoming.getBackdropPath());

        intent.putExtra("popularity",movieUpcoming.getPopularity());
        intent.putExtra("vote_count",movieUpcoming.getVoteCount());
        intent.putExtra("vote_average",movieUpcoming.getVoteAverage());

        intent.putExtra("name_movie",movieUpcoming.getTitle());
        intent.putExtra("create_day",movieUpcoming.getReleaseDate());

        intent.putExtra("overview",movieUpcoming.getOverview());

        intent.putExtra("idmovie",movieUpcoming.getId());

        startActivity(intent);

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,imageView,"sharedName");
        startActivity(intent,options.toBundle());
    }


    void getIdVideoByIdMovie(){
        homeApiProvider.getIdYoutubeByIdMovie(idMovie).enqueue(
                new Callback<ListVideoInfo>() {
                    @Override
                    public void onResponse(Call<ListVideoInfo> call, Response<ListVideoInfo> response) {
                        idVideoYotube = response.body().getResults().get(0).getKey();
                    }

                    @Override
                    public void onFailure(Call<ListVideoInfo> call, Throwable t) {

                    }
                }
        );
    }
}

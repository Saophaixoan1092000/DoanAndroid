package com.example.daltdd.Activity.screen;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.daltdd.Activity.adapter.MovieRecyclerAdapter;
import com.example.daltdd.Activity.adapter.MovieUpcomingAdapter;
import com.example.daltdd.Activity.adapter.SlidePageAdapter;
import com.example.daltdd.Activity.base.IMovieOnLickListener;
import com.example.daltdd.Activity.base.PathAPI;
import com.example.daltdd.Activity.model.ListMoviePopular;
import com.example.daltdd.Activity.model.ListMovieUpcoming;
import com.example.daltdd.Activity.model.MovieCardInfo;

import com.example.daltdd.Activity.model.MoviePopular;
import com.example.daltdd.Activity.model.MovieUpcoming;
import com.example.daltdd.Activity.network.HomeApi;
import com.example.daltdd.Activity.network.HomeApiService;
import com.example.daltdd.Activity.widget.LoginActivity;
import com.example.daltdd.Activity.widget.RegistrationActivity;
import com.example.daltdd.Activity.widget.WelcomeActivity;
import com.example.daltdd.R;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActitivy extends AppCompatActivity implements IMovieOnLickListener {

    private List<MovieCardInfo> listMovieCardInfo;
    private List<MoviePopular> listMoviePopular;
    private List<MovieUpcoming> listMovieUpcoming;
    private ViewPager slidePage;
    private TabLayout indicator;
    ImageView imageView;
    private HomeApiService homeApiProvider;

    private RecyclerView MoviewRV;
    private RecyclerView MoviewRVComing;

    ImageView actionRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //header
        addPageViewOnActivity();

    }
    public void find(View view) {
        startActivity(new Intent(getBaseContext(),SearchActivity.class));
    }
    public void logout(View view) {
        startActivity(new Intent(HomeActitivy.this, WelcomeActivity.class));
    }

    void addPageViewOnActivity() {
        listMovieCardInfo = new ArrayList<>();
        listMoviePopular = new ArrayList<>();
        listMovieUpcoming = new ArrayList<>();

        final SlidePageAdapter adapter = new SlidePageAdapter(getBaseContext(), listMovieCardInfo);
        final MovieRecyclerAdapter movieRecyclerAdapter = new MovieRecyclerAdapter(getBaseContext(),listMoviePopular,this);
        final MovieUpcomingAdapter movieRecyclerAdapter1 = new MovieUpcomingAdapter(getBaseContext(), listMovieUpcoming, this);
        homeApiProvider = HomeApi.getHomeApiProvider();

        homeApiProvider.getMoviePopular().enqueue(
                new Callback<ListMoviePopular>() {
                    @Override
                    public void onResponse(Call<ListMoviePopular> call, Response<ListMoviePopular> response) {
                        if (response.isSuccessful()) {
                            ListMoviePopular res = response.body();

                            listMovieCardInfo.add(new MovieCardInfo("https://image.tmdb.org/t/p/original/" + res.getResults().get(0).getPosterPath(), res.getResults().get(0).getTitle()));
                            listMovieCardInfo.add(new MovieCardInfo("https://image.tmdb.org/t/p/original/" + res.getResults().get(1).getPosterPath(), res.getResults().get(1).getTitle()));
                            slidePage = findViewById(R.id.slider_pager);
                            listMovieCardInfo.add(new MovieCardInfo("https://image.tmdb.org/t/p/original/" + res.getResults().get(2).getPosterPath(), res.getResults().get(2).getTitle()));
                            slidePage.setAdapter(adapter);


                            indicator = findViewById(R.id.indicator);
                            //Setup time;
                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new SlideTimer(),4000, 6000);
                            indicator.setupWithViewPager(slidePage, true);

                            // add recyclview

                            for(int i=3,j=1;i<res.getResults().size() && j<=8;i++){
                                if(res.getResults().get(i).getOverview().equals("")){

                                }else{
                                    j++;
                                    listMoviePopular.add(new MoviePopular(res.getResults().get(i)));
                                }

                            }

                            MoviewRV = findViewById(R.id.Rv_movies);
                            MoviewRV.setAdapter(movieRecyclerAdapter);
                            MoviewRV.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
                        }
                    }

                    @Override
                    public void onFailure(Call<ListMoviePopular> call, Throwable t) {

                    }
                }

        );

        homeApiProvider.getMovieUpcoming().enqueue(
                new Callback<ListMovieUpcoming>() {
                    @Override
                    public void onResponse(Call<ListMovieUpcoming> call, Response<ListMovieUpcoming> response) {
                        if (response.isSuccessful()) {
                            ListMovieUpcoming res = response.body();

                            // add recyclview

                            for(int i=3,j=1;i<res.getResults().size() && j<=8;i++){
                                if(res.getResults().get(i).getOverview().equals("")){

                                }else{
                                    j++;
                                    listMovieUpcoming.add(new MovieUpcoming(res.getResults().get(i)));
                                }

                            }

                            MoviewRVComing = findViewById(R.id.Rv_movies_coming_soon);
                            MoviewRVComing.setAdapter(movieRecyclerAdapter1);
                            MoviewRVComing.setLayoutManager(new LinearLayoutManager(getBaseContext(),LinearLayoutManager.HORIZONTAL,false));
                        }
                    }

                    @Override
                    public void onFailure(Call<ListMovieUpcoming> call, Throwable t) {

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

       // startActivity(intent);
       // Toast.makeText(this,String.valueOf(moviePopular.getOverview()) ,Toast.LENGTH_LONG).show();

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

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,imageView,"sharedName");
        startActivity(intent,options.toBundle());
    }


    class SlideTimer extends TimerTask {

        @Override
        public void run() {
            HomeActitivy.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(slidePage.getCurrentItem() < listMovieCardInfo.size()-1) {
                        slidePage.setCurrentItem(slidePage.getCurrentItem()+1);
                    }
                    else
                        slidePage.setCurrentItem(0);
                }
            });
        }
    }
}

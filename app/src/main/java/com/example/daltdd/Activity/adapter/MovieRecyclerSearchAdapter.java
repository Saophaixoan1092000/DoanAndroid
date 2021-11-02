package com.example.daltdd.Activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.daltdd.Activity.base.IMovieOnLickListener;
import com.example.daltdd.Activity.model.MoviePopular;
import com.example.daltdd.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecyclerSearchAdapter extends RecyclerView.Adapter<MovieRecyclerSearchAdapter.MyViewHolder> {
    private Context context;
    private List<MoviePopular> mData;
    IMovieOnLickListener movieItemClick;
    public MovieRecyclerSearchAdapter(Context context,List<MoviePopular> mData,IMovieOnLickListener movieItemClick){
        this.context=context;
        this.mData=mData;
        this.movieItemClick =movieItemClick;
    }


    @NonNull
    @Override
    public MovieRecyclerSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.card_item_movie_search,parent,false);

        return new MovieRecyclerSearchAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mData.get(position).getTitle());
        holder.textViewPopular.setText(mData.get(position).getPopularity().toString());
        holder.textViewCountView.setText(mData.get(position).getVoteAverage().toString());
        holder.textViewOverview.setText(mData.get(position).getOverview());
        Glide.with(context).load("https://image.tmdb.org/t/p/original/"+mData.get(position)
                .getPosterPath())
                .centerCrop()
                .fitCenter()
                .into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;
        private TextView textViewPopular;
        private TextView textViewCountView;
        private TextView textViewOverview;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.nameMovieSearch);
            imageView =itemView.findViewById(R.id.imgMovieSearch);
            textViewPopular = itemView.findViewById(R.id.popularity);
            textViewCountView = itemView.findViewById(R.id.viewCount);
            textViewOverview = itemView.findViewById(R.id.overview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClick.onMovieClick(mData.get(getAdapterPosition()),imageView);
                }
            });

        }
    }
}

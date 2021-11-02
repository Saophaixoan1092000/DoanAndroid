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

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MyViewHolder> {

    private  Context context;
    private List<MoviePopular> mData;
    IMovieOnLickListener movieItemClick;
    public MovieRecyclerAdapter(Context context,List<MoviePopular> mData,IMovieOnLickListener movieItemClick){
        this.context=context;
        this.mData=mData;
        this.movieItemClick =movieItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(context).inflate(R.layout.movie_recycler_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView.setText(mData.get(position).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/original/"+mData.get(position)
                .getPosterPath()).
                placeholder(R.drawable.progess_animation)
.fitCenter()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.titleRecyclerMovie);
            imageView =itemView.findViewById(R.id.imageRecyclerMovie);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieItemClick.onMovieClick(mData.get(getAdapterPosition()),imageView);
                }
            });

        }
    }
}

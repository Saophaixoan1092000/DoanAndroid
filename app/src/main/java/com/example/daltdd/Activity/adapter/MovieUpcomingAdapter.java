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
import com.example.daltdd.Activity.model.MovieUpcoming;
import com.example.daltdd.R;

import java.util.List;

public class MovieUpcomingAdapter extends RecyclerView.Adapter<MovieUpcomingAdapter.MyViewHolder> {
    private Context context;
    private List<MovieUpcoming> mData1;
    IMovieOnLickListener movieItemClick1;

    public MovieUpcomingAdapter(Context context, List<MovieUpcoming> mData1, IMovieOnLickListener movieItemClick){
        this.context=context;
        this.mData1=mData1;
        this.movieItemClick1 =movieItemClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_recycler_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(mData1.get(position).getTitle());
        Glide.with(context).load("https://image.tmdb.org/t/p/original/"+mData1.get(position)
                .getPosterPath()).
                placeholder(R.drawable.progess_animation)
                .fitCenter()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mData1.size();
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
                    movieItemClick1.onMovieClick1(mData1.get(getAdapterPosition()),imageView);
                }
            });

        }
    }
}

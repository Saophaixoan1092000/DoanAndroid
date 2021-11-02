package com.example.daltdd.Activity.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.daltdd.Activity.model.MovieCardInfo;
import com.example.daltdd.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SlidePageAdapter extends PagerAdapter {

    private Context context;
    private List<MovieCardInfo> listMovie;

    public SlidePageAdapter(Context context,List<MovieCardInfo> listMovie){
        this.context=context;
        this.listMovie=listMovie;
    }

    @Override
    public int getCount() {
        return listMovie.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View slideLayout =inflater.inflate(R.layout.slide_item,null);
        ImageView slideImg =slideLayout.findViewById(R.id.imageView);

        Picasso.get().load(listMovie.get(position).getImage()).noFade().fit().centerCrop().into(slideImg);
       // slideImg.setImageResource(listMovie.get(position).getImage());

        TextView txtView = slideLayout.findViewById(R.id.txtTitle);
        txtView.setText(listMovie.get(position).getTitle());
        container.addView(slideLayout);
        return slideLayout;
    }
}

package com.finalwork.marvelview.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.finalwork.marvelview.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

public abstract class ImageLoadingListener implements RequestListener<Drawable> {// TODO GlideDrawable转Drawable


    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        onFailed(e);
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        onSuccess();
        return false;
    }

    public abstract void onSuccess();

    public abstract void onFailed(@NonNull Exception e);

    @BindingAdapter({"listener"})
    public static void loadListener(ImageView imageView,ImageLoadingListener loadingListener){
        return;//TODO
    }
}
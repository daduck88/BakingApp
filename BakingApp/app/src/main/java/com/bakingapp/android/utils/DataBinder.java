package com.bakingapp.android.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.opengl.Visibility;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Daduck on 8/1/17.
 */
public final class DataBinder {

    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void imageUrl(ImageView imageView, String url) {
        if(!url.isEmpty()) {
            Context context = imageView.getContext();
            Picasso.with(context)
                    .load(url)
                    .into(imageView);
        }
    }

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }
}
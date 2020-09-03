package com.manager.managester.ImagesBindings;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.squareup.picasso.Picasso;

import kotlin.jvm.JvmStatic;

public class ImagesBinding {

    @BindingAdapter("product_image")
    public static void getProductImage(ImageView view,String url){
        Picasso.get().load(url).into(view);
    }

    @BindingAdapter("product_icon")
    public static void getProductIcon(ImageView view,String url){
        Picasso.get().load(url).into(view);
    }

    //TODO: Set Up Notification Layout Images

    @BindingAdapter("notificationimage")
    public static void getNotificationImage(ImageView view,String url){
        Picasso.get().load(url).fit().into(view);
    }

    @BindingAdapter("notificationicon")
    public static void getNotificationIcon(ImageView view,String url){
        Picasso.get().load(url).fit().into(view);
    }
}

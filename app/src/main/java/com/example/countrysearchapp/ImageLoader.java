package com.example.countrysearchapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class ImageLoader {

    public static void loadImage(String url, ImageView imageView) {
        new Thread(() -> {
            try {
                InputStream input = new URL(url).openStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(input);
                new Handler(Looper.getMainLooper()).post(() ->
                        imageView.setImageBitmap(bitmap)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
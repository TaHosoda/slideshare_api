package com.example.hsd.slideshare;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsd on 2015/06/06.
 */
public class AsyncImage extends AsyncTask<String,Void,Bitmap> {
    private Activity mainActivity;

    public AsyncImage(Activity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... url) {
        HTTPConnection conn = new HTTPConnection();
        return conn.downloadImage(url[0]);
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        ImageView imageView = (ImageView)mainActivity.findViewById(R.id.imageView);
        imageView.setImageBitmap(bmp);
    }

}

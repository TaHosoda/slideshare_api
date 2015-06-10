package com.example.hsd.slideshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hsd on 2015/06/06.
 */
public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Log.i("DetailActivity", "id=" + id);
//        Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        getDetail(id);
    }

    public void getDetail(String slideshow_id) {
        APISlideShow.get(this, slideshow_id);
    }
}

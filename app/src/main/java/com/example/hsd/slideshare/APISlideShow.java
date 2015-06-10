package com.example.hsd.slideshare;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.EditText;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsd on 2015/06/07.
 */
public class APISlideShow {

    public static Map<String,String> getParams(Activity activity) {
        Map<String,String> params = new HashMap<>();
        String timestamp = String.valueOf(new Date().getTime() / 1000);

        // API Parameter
        params.put("api_key", activity.getString(R.string.api_key));
        params.put("ts", timestamp);

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.reset();
            md.update((activity.getString(R.string.api_secret_key) + timestamp).getBytes());
            params.put("hash", new BigInteger(1, md.digest()).toString(16));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return params;
    }

    public static void search(Activity activity, int page) {
        Map<String,String> params = getParams(activity);

        if (page > 2) {
            return;
        }

        // search options
        EditText edit = (EditText)activity.findViewById(R.id.editText);
        params.put("q", edit.getText().toString());
        params.put("lang", activity.getString(R.string.search_lang));
        params.put("page", Integer.toString(page));

        // async request
        try {
            URLParams url_params = new URLParams();
            url_params.url = activity.getString(R.string.api_url_search);
            url_params.params = params;

            AsyncSearch task = new AsyncSearch(activity);

            if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
                return;
            }

            task.execute(url_params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void get(Activity activity, String slideshow_id) {
        Map<String,String> params = getParams(activity);

        // slideshow_id
        params.put("slideshow_id", slideshow_id);

        // async request
        try {
            URLParams url_params = new URLParams();
            url_params.url = activity.getString(R.string.api_url_get);
            url_params.params = params;
            AsyncDetail task = new AsyncDetail(activity);
            task.execute(url_params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

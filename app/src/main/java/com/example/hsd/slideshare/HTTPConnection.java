package com.example.hsd.slideshare;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

/**
 * Created by hsd on 2015/06/06.
 */
public class HTTPConnection {

    public String requestGet(String request_url, Map<String,String> requestParams) {
        String response = null;

        // リクエストパラメータの設定
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : requestParams.entrySet()) {
            params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        String paramString = URLEncodedUtils.format(params, "UTF-8");
        request_url = request_url + "?" + paramString;

        Log.i("HTTPConnection", request_url);

        try {
            URL url = new URL(request_url);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();

            if (conn != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buf = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    buf.append(line);
                }
                reader.close();
                response = buf.toString();
                Log.d("HTTPConnection", response);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    public Bitmap downloadImage(String image_url){
        Bitmap bmp = null;
        Log.d("HTTPConnection", image_url);

        try {

            URL url = new URL(image_url);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            InputStream stream = conn.getInputStream();
            bmp = BitmapFactory.decodeStream(stream);
            stream.close();

        } catch(Exception e) {
            e.printStackTrace();
        }

        return bmp;
    }
}

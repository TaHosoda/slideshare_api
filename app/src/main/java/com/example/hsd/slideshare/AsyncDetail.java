package com.example.hsd.slideshare;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsd on 2015/06/06.
 */
public class AsyncDetail extends AsyncTask<URLParams,Void,String> {
    private Activity mainActivity;
    Map<String,String> resultMap = new HashMap<>();

    public AsyncDetail(Activity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    @Override
    protected String doInBackground(URLParams... url_params) {
        HTTPConnection conn = new HTTPConnection();
        return conn.requestGet(url_params[0].url, url_params[0].params);
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            resultMap = ParseXML.parseDetailResponse(response);
        } catch (Exception e) {
//            e.printStackTrace();
            Toast.makeText(mainActivity, "API Response Error.", Toast.LENGTH_LONG).show();
        }

        TextView textTitle = (TextView)mainActivity.findViewById(R.id.textTitle);
        TextView textDescription = (TextView)mainActivity.findViewById(R.id.textDescription);
        TextView textUpdated = (TextView)mainActivity.findViewById(R.id.textUpdated);
        TextView textURL = (TextView)mainActivity.findViewById(R.id.textURL);
        ImageView imageView = (ImageView)mainActivity.findViewById(R.id.imageView);

        textTitle.setText(resultMap.get("title"));
        textDescription.setText(resultMap.get("description"));
        textUpdated.setText(resultMap.get("updated"));
        textURL.setText(resultMap.get("url"));

        AsyncImage task = new AsyncImage(mainActivity);
        task.execute(resultMap.get("image_url"));

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //
//                Intent intent = new Intent(mainActivity, ViewActivity.class);
//                intent.putExtra("url", resultMap.get("embed_url"));
//                mainActivity.startActivity(intent);

                // ブラウザ呼び出し
                Uri uri = Uri.parse(resultMap.get("embed_url"));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mainActivity.startActivity(intent);
            }
        });
    }

}

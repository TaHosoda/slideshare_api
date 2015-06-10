package com.example.hsd.slideshare;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hsd on 2015/06/06.
 */
public class AsyncSearch extends AsyncTask<URLParams,Void,String> {
    private Activity mainActivity;
    private static List<Map<String,String>> resultList = new ArrayList<>();

    public AsyncSearch(Activity activity) {
        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    @Override
    protected String doInBackground(URLParams... url_params) {
        HTTPConnection conn = new HTTPConnection();
        return conn.requestGet(url_params[0].url, url_params[0].params);
    }

    @Override
    protected void onPostExecute(final String response) {
        int total = 0;
        try {
//            resultList = ParseXML.parseSearchResponse(response);
            resultList.addAll(ParseXML.parseSearchResponse(response));
            total = ParseXML.parseSearchResponseCount(response);
        } catch (Exception e) {
//            e.printStackTrace();
            Toast.makeText(mainActivity, "API Response Error.", Toast.LENGTH_LONG).show();
        }

        List<String> items = new ArrayList<>();
        for (Map<String,String> entry : resultList) {
            items.add(entry.get("title"));
        }

        ListView listView = (ListView)mainActivity.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            mainActivity, android.R.layout.simple_expandable_list_item_1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView view = (TextView)super.getView(position, convertView, parent);
                view.setTextSize(14);
                return view;
            }
        };

        // footerView
        if (items.size() < total) {
            listView.addFooterView(getFooter());
        }
        listView.invalidateViews();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("AsyncSearch", "ListView.position=" + position);
                ListView listView = (ListView)parent;

                if (resultList.size() > position) {
                    Map<String, String> entry;
                    entry = resultList.get(position);
//                Toast.makeText(mainActivity, entry.get("id"), Toast.LENGTH_LONG).show();
//                Toast.makeText(mainActivity, entry.get("title"), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(mainActivity, DetailActivity.class);
                    intent.putExtra("id", entry.get("id"));
                    mainActivity.startActivity(intent);
                } else {
                    listView.removeFooterView(getFooter());
                    int page = (resultList.size() / 12) + 1;
                    Log.d("AsyncSearch", "page=" + page);
                    APISlideShow.search(mainActivity, page);
                }
            }
        });

/*
        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                Log.d("AsyncSearch", "firstVisibleItem=" + firstVisibleItem +
                    ", visibleItemCount=" + visibleItemCount + ", totalItemCount=" + totalItemCount);

                if (totalItemCount == (firstVisibleItem + visibleItemCount)) {
                    if (totalItemCount >= 12) {
                        int page = (totalItemCount / 12) + 1;
                        Log.d("AsyncSearch", "totalItemCount=" + totalItemCount + ", page=" + page);

                        APISlideShow.search(mainActivity, page);
                    }
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

        });
*/
    }

    private View footerView = null;
    private View getFooter() {
        if (footerView == null) {
            footerView = mainActivity.getLayoutInflater().inflate(R.layout.listview_footer, null);
        }
        return footerView;
    }
}

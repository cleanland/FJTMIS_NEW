package com.example.administrator.kui;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //$.GET listData from server:
        //by session implicitly...
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                // TODO bind data to the list of this page:
                /*
                {
                    "page": 1,
                    "total": 16,
                    "rows": [
                        {
                            "ID": 16,
                            "BlogTypeID_DisplayText": "未分类",
                            "Title": "dddddddd",
                            "ClickedCount": 0,
                            "RegEmpID_DisplayText": "管理员22233",
                            "RegEmpID": 2,
                            "RegTime": "2013-11-05 18:11:36",
                            "ModTime": "2014-06-27 17:45:07"
                        }
                    ]
                }
                */

                JSONObject jsobj = null;
                try {
                    final MyActivity ctx = MyActivity.this;
                    jsobj = new JSONObject(result);
                    final JSONArray listjson = jsobj.getJSONArray("rows");
                    ListView lv = new ListView(MyActivity.this);
                    lv.setDividerHeight(2);

                    final LayoutInflater inflater = LayoutInflater.from(ctx);
                    lv.setAdapter(new ListAdapter() {
                        @Override
                        public boolean areAllItemsEnabled() {
                            return false;
                        }

                        @Override
                        public boolean isEnabled(int position) {
                            return true;
                        }

                        @Override
                        public void registerDataSetObserver(DataSetObserver observer) {

                        }

                        @Override
                        public void unregisterDataSetObserver(DataSetObserver observer) {

                        }

                        @Override
                        public int getCount() {
                            return listjson.length();
                        }

                        @Override
                        public Object getItem(int position) {
                            try {
                                return listjson.getJSONObject(position);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        public long getItemId(int position) {
                            return position;
                        }

                        @Override
                        public boolean hasStableIds() {
                            return true;
                        }

                        @Override
                        public View getView(final int position, View convertView, ViewGroup parent) {
                            convertView = inflater.inflate(R.layout.listitem_min, parent, false);
                            TextView view = (TextView) convertView.findViewById(R.id.textView);


                            try {
                                final JSONObject obj = listjson.getJSONObject(position);
                                    /*"ID": 16,
                                    "BlogTypeID_DisplayText": "未分类",
                                    "Title": "dddddddd",
                                    "ClickedCount": 0,
                                    "RegEmpID_DisplayText": "管理员22233",
                                    "RegEmpID": 2,
                                    "RegTime": "2013-11-05 18:11:36",
                                    "ModTime": "2014-06-27 17:45:07"*/

                                view.setText(obj.getString("RegEmpID_DisplayText") + "@" + obj.getString("Title"));
                                view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            JSONObject pos = listjson.getJSONObject(position);
                                            int id = pos.getInt("ID");
                                            Intent newIntent = new Intent(ctx, Act_BlogDetail.class);
                                            newIntent.putExtra("id", id);
                                            startActivity(newIntent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            convertView.setMinimumWidth(1080 + parent.getWidth());
                            return convertView;
                        }

                        @Override
                        public int getItemViewType(int position) {
                            return 0;
                        }

                        @Override
                        public int getViewTypeCount() {
                            return 1;
                        }

                        @Override
                        public boolean isEmpty() {
                            return false;
                        }
                    });

                    LinearLayout layout = (LinearLayout) MyActivity.this.findViewById(R.id.page);
                    layout.addView(lv);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    LinkedList params = new LinkedList<BasicNameValuePair>();
                    URL url = new URL(((MyApplication) getApplication()).getSiteUrl() + "/Office/GetBlogList");
                    return CwyWebJSON.postToUrl(url.toString(), params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        }.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

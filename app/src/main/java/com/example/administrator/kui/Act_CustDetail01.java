package com.example.administrator.kui;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;


public class Act_CustDetail01 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_act__cust_detail01);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);
                // TODO bind data to the list of this page:
                JSONObject jsobj = null;
                try {
                    final Act_CustDetail01 ctx = Act_CustDetail01.this;
                    jsobj = new JSONArray(result).getJSONObject(0);
                    LinearLayout layout = new LinearLayout(ctx);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    TextView title = new TextView(ctx);
                    title.setPadding(10, 10, 10, 10);
                    title.setBackgroundColor(Color.GRAY);
                    title.setText(jsobj.getString("WebSiteID_DisplayText") + "@" + jsobj.getString("CreateTime"));
                    layout.addView(title);

                    TextView c = new TextView(ctx);
                    c.setPadding(10, 10, 10, 10);
                    c.setText(jsobj.getString("Memo"));
                    layout.addView(c);

                    ctx.setContentView(layout);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    LinkedList params = new LinkedList<BasicNameValuePair>();
                    URL url = new URL(((MyApplication) getApplication()).getSiteUrl() + "/CustomerManage/GetCompany?id=" +
                            Act_CustDetail01.this.getIntent().getExtras().getInt("id"));

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
        getMenuInflater().inflate(R.menu.menu_act__cust_detail01, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

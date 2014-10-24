package com.example.administrator.kui;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class DaemonService extends Service {

    //用于记录上次获取的日志列表内容。
    //如果变化了，就振动提醒一下客户吧。
    String lastResult = "";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                // TODO Auto-generated method stub

                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected void onPostExecute(String result) {
                        // TODO Auto-generated method stub
                        super.onPostExecute(result);
                        if (!lastResult.isEmpty() && !result.equals(lastResult)) {
                            //振动提醒客户，3秒钟
                            ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(3000);
                        }
                        lastResult=result;
                    }

                    @Override
                    protected String doInBackground(Void... arg0) {
                        try {
                            LinkedList params = new LinkedList<BasicNameValuePair>();
                            return CwyWebJSON.postToUrl(((MyApplication) getApplication()).getSiteUrl() + "/Office/GetBlogList", params);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return "";
                    }
                }.execute();
            }
        }, 5000, 1 * 60 * 1000);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v("=========", "***** DaemonService *****: onStart");
        // 这里可以做Service该做的事
        //Toast.makeText(getApplicationContext(), "address" + "", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "FJTMIS SERVICE being killed..." + "", Toast.LENGTH_LONG).show();
            }
        }, 100);
        super.onDestroy();
    }
}  
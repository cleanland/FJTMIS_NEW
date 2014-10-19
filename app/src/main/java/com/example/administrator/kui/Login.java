package com.example.administrator.kui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;

import java.util.Date;
import java.util.LinkedList;


public class Login extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //从SharedPreferences获取数据+保存到全局变量:
        //从SharedPreferences获取数据+保存到全局变量:
        //从SharedPreferences获取数据+保存到全局变量:
        //从SharedPreferences获取数据+保存到全局变量:
        //从SharedPreferences获取数据+保存到全局变量:
        //从SharedPreferences获取数据+保存到全局变量:
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String s = preferences.getString("siteUrl", "");
        ((MyApplication) getApplication()).setSiteUrl(s);

        //将设置文件中的用户名和密码绑定到页面：
        EditText siteUrl = (EditText) findViewById(R.id.siteUrl);
        siteUrl.setText(((MyApplication) getApplication()).getSiteUrl());

        EditText acc = (EditText) findViewById(R.id.account);
        acc.setText(preferences.getString("account", ""));

        EditText pwd = (EditText) findViewById(R.id.pwd);
        pwd.setText(preferences.getString("pwd", ""));

        //定义事件：登录按钮。点击时：
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected void onPostExecute(String result) {
                        // TODO Auto-generated method stub
                        super.onPostExecute(result);
                        if (result.equals("Y")) {
                            startActivity(new Intent(Login.this, MyActivity.class));
                            //startActivity(new Intent(Login.this,testFra.class));
                        } else {
                            Toast.makeText(Login.this, "" + result, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    protected String doInBackground(Void... arg0) {
                        try {
                            //{ user: user, pwd: pwd,rand:new Date().toString() }
                            EditText siteUrl = (EditText) findViewById(R.id.siteUrl);
                            String siteUrlStr = siteUrl.getText().toString();

                            EditText acc = (EditText) findViewById(R.id.account);
                            String accStr = acc.getText().toString();

                            EditText pwd = (EditText) findViewById(R.id.pwd);
                            String pwdStr = pwd.getText().toString();

                            //将网址写入设置文件。下次就不必填写了。
                            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("siteUrl", siteUrlStr);
                            editor.putString("account", accStr);
                            editor.putString("pwd", pwdStr);
                            editor.commit();

                            //将域名写入全局变量：
                            ((MyApplication) getApplication()).setSiteUrl(siteUrlStr);


                            LinkedList params = new LinkedList<BasicNameValuePair>();
                            params.add(new BasicNameValuePair("user", "" + accStr));
                            params.add(new BasicNameValuePair("pwd", "" + pwdStr));
                            params.add(new BasicNameValuePair("rand", new Date().toString()));
                            return CwyWebJSON.postToUrl(((MyApplication) getApplication()).getSiteUrl() + "/home/logon", params);
                        } catch (Exception e) {
                            //Toast.makeText(Login.this,"晕，是不是网址不对啊？",Toast.LENGTH_LONG).show();
                            //上句好像加上也不起作用，不知道为啥。。。
                            e.printStackTrace();
                        }
                        return "";
                    }
                }.execute();
            }
        });

        // 如果域名不空+帐号不空+密码不空，立即自动登录:
        if (
                !s.isEmpty() &&
                        !((EditText) findViewById(R.id.account)).getText().toString().isEmpty() &&
                        !((EditText) findViewById(R.id.pwd)).getText().toString().isEmpty()
                ) {
            loginBtn.callOnClick();
        }
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

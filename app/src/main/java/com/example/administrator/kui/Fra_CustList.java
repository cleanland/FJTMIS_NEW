package com.example.administrator.kui;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.LinkedList;

/**
 * Created by Administrator on 2014/10/20.
 * 显示客户列表
 */
public class Fra_CustList extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (false) {
            TextView tvx = new TextView(getActivity());
            tvx.setText("adsfadsf");
            if (true) return tvx;
            //以上已能正确执行。。。
            //每个NEW出来的本类对象都只显示一个TEXTVIEW即可。
        }

        final View rootview = inflater.inflate(R.layout.custlist, container, false);

        new AsyncTask<Void, Void, String>() {
            @Override
            protected void onPostExecute(String result) {
                // TODO Auto-generated method stub
                super.onPostExecute(result);

                // TODO bind data to the list of this page:
                JSONObject jsobj = null;
                try {
                    final Activity ctx = getActivity();
                    jsobj = new JSONObject(result);
                    final JSONArray listjson = jsobj.getJSONArray("rows");
                    ListView lv = (ListView) rootview.findViewById(R.id.listView);
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
                                    /*
                                    "ID": 6,
                                    "Name": "kkk",
                                    "WebSiteID_DisplayText": "本机BSFJT12",
                                    "WebSiteID": 26,
                                    "SourceID": 14,
                                    "Addr": "森",
                                    "City": "",
                                    "CreateTime": "",
                                    "TestTime": "",
                                    "BeginTime": "",
                                    "EndTime": "",
                                    "Status": "试用",
                                    "Memo": "ccc",
                                    "ModTime": "2013-10-16 14:01:19",
                                    "FlagDeleted": false,
                                    "Spell": "kkk",
                                    "EmpID_DisplayText": "管理员22233",
                                    "EmpID": 2
                                    */

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(Void... arg0) {
                try {
                    LinkedList params = new LinkedList<BasicNameValuePair>();
                    params.add(new BasicNameValuePair("page", "1"));
                    params.add(new BasicNameValuePair("rp", "20"));
                    params.add(new BasicNameValuePair("sortname", "ID"));
                    params.add(new BasicNameValuePair("sortorder", "desc"));
                    params.add(new BasicNameValuePair("query", "{Date1:\"\",sCon1:\"\"}"));
                    params.add(new BasicNameValuePair("qtype", "sql"));
                    params.add(new BasicNameValuePair("iegohell", "1413782533798"));
                    URL url = new URL(((MyApplication) getActivity().getApplication()).getSiteUrl() + "/CustomerManage/GetCompanyList");
                    return CwyWebJSON.postToUrl(url.toString(), params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
        }.execute();

        return rootview;
    }
}

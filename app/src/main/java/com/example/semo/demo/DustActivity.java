package com.example.semo.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class DustActivity extends AppCompatActivity {

    TextView text_time;
    TextView text_feel;
    TextView text_dust;
    TextView text_dust_value;
    TextView text_temp;
    TextView text_temp_value;
    TextView text_humi;
    TextView text_humi_value;
    TextView text_battery;

    ImageView smile;
    ImageView frown;
    ImageView dizzy;
    ImageView angry;
    ImageView battery;

    LinearLayout linearLayout_dust;
    SwipeRefreshLayout mswipe;

    private static final String TAG_RESULTS="result";
    private static final String TAG_TEMP = "temp";
    private static final String TAG_HUMI = "humi";
    private static final String TAG_DUST ="dust";
    private static final String TAG_TIME ="time";
    private static final String TAG_BATTERY ="battery";

    String email = "mail";
    String myJSON;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust);

        mswipe = (SwipeRefreshLayout)findViewById(R.id.swipe_live_all);
        mswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetData task = new GetData();
                task.execute("http://ip/dust2.php");

                mswipe.setRefreshing(false);
            }
        });

        text_time = (TextView)findViewById(R.id.text_time);
        text_feel = (TextView)findViewById(R.id.text_feel);
        text_dust = (TextView)findViewById(R.id.text_dust);
        text_dust_value = (TextView)findViewById(R.id.text_dust_value);
        text_temp = (TextView)findViewById(R.id.text_temp);
        text_temp_value = (TextView)findViewById(R.id.text_temp_value);
        text_humi = (TextView)findViewById(R.id.text_humi);
        text_humi_value = (TextView)findViewById(R.id.text_humi_value);
        text_battery = (TextView)findViewById(R.id.text_battery);

        smile = (ImageView)findViewById(R.id.smile);
        frown = (ImageView)findViewById(R.id.frown);
        dizzy = (ImageView)findViewById(R.id.dizzy);
        angry = (ImageView)findViewById(R.id.angry);
        battery = (ImageView)findViewById(R.id.battery);

        linearLayout_dust = (LinearLayout)findViewById(R.id.linearLayout_dust);

        GetData task = new GetData();
        task.execute("http://ip/dust2.php");
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(DustActivity.this,
                    "Please Wait", null, true, true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();

            myJSON = result;
            showResult();

        }

        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];

            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();

                int responseStatusCode = httpURLConnection.getResponseCode();

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }

                bufferedReader.close();

                return sb.toString().trim();

            } catch (Exception e) {
                errorString = e.toString();
                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(myJSON);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_RESULTS);

            for(int i=0;i<jsonArray.length();i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                int temp = item.getInt(TAG_TEMP);
                int humi = item.getInt(TAG_HUMI);
                int dust = item.getInt(TAG_DUST);
                int battery = item.getInt(TAG_BATTERY);
                String time = item.getString(TAG_TIME);

                text_temp_value.setText(Integer.toString(temp)+"°C");
                text_humi_value.setText(Integer.toString(humi)+"%");
                text_dust_value.setText(Integer.toString(dust)+"㎍/㎥");
                text_time.setText(time);
                text_battery.setText(Integer.toString(battery)+"%");

                if(dust>=0 && dust<=30){
                    linearLayout_dust.setBackgroundResource(R.color.dust_blue);
                    text_feel.setText("좋음");

                    smile.setVisibility(View.VISIBLE);
                    frown.setVisibility(View.INVISIBLE);
                    angry.setVisibility(View.INVISIBLE);
                    dizzy.setVisibility(View.INVISIBLE);

                } else if(dust>=31 && dust<=80){
                    linearLayout_dust.setBackgroundResource(R.color.dust_green);
                    text_feel.setText("보통");

                    smile.setVisibility(View.INVISIBLE);
                    frown.setVisibility(View.VISIBLE);
                    angry.setVisibility(View.INVISIBLE);
                    dizzy.setVisibility(View.INVISIBLE);

                } else if(dust>=81 && dust<=150) {
                    linearLayout_dust.setBackgroundResource(R.color.dust_yellow);
                    text_feel.setText("나쁨");

                    smile.setVisibility(View.INVISIBLE);
                    frown.setVisibility(View.INVISIBLE);
                    angry.setVisibility(View.VISIBLE);
                    dizzy.setVisibility(View.INVISIBLE);

                } else if(dust>=151){
                    linearLayout_dust.setBackgroundResource(R.color.dust_red);
                    text_feel.setText("매우나쁨");

                    smile.setVisibility(View.INVISIBLE);
                    frown.setVisibility(View.INVISIBLE);
                    angry.setVisibility(View.INVISIBLE);
                    dizzy.setVisibility(View.VISIBLE);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

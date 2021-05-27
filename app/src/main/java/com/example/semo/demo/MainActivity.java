package com.example.semo.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    TextView id;
    TextView pw;
    EditText id_edit;
    EditText pw_edit;
    Button login_btn;
    Button signup_btn;
    CheckBox check_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (TextView) findViewById(R.id.id);
        pw = (TextView) findViewById(R.id.pw);
        id_edit = (EditText) findViewById(R.id.id_edit);
        pw_edit = (EditText) findViewById(R.id.pw_edit);
        login_btn = (Button) findViewById(R.id.login_btn);
        signup_btn = (Button) findViewById(R.id.signup_btn);
        check_remember = (CheckBox) findViewById(R.id.check_remember);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = id_edit.getText().toString();
                String Pw = pw_edit.getText().toString();

                insertoToDatabase(Id, Pw);
            }
        });

        SharedPreferences sf = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        Boolean check = sf.getBoolean("checked", false);
        String id = sf.getString("id", "");
        String pw = sf.getString("password", "");

        if(check == true){
            check_remember.setChecked(true);
            id_edit.setText(id);
            pw_edit.setText(pw);
            insertoToDatabase(id, pw);
        }
    }

    private void insertoToDatabase(String Id, String Pw) { //데이터 베이스에 있는 아이디, 비밀번호와 대조
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equals("success")){

                    SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                    final SharedPreferences.Editor editor = auto.edit();
                    editor.putString("id", id_edit.getText().toString());
                    editor.putString("password", pw_edit.getText().toString());
                    editor.putBoolean("checked", check_remember.isChecked());
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, DustActivity.class);
                    intent.putExtra("id", id_edit.getText().toString());
                    startActivity(intent);
                    finish();

                } else {

                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                }
            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Pw = (String) params[1];

                    String link = "http://13.125.177.193/login.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id, Pw);
    }

}

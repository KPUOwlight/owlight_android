package com.example.semo.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SignupActivity extends AppCompatActivity {

    TextView text_name;
    TextView text_email;
    TextView text_pw;
    TextView text_pwre;
    TextView match_pw;
    TextView match_pwre;
    TextView text_profile;

    EditText edit_name;
    EditText edit_email;
    EditText edit_pw;
    EditText edit_pwre;

    Button btn_signup;

    String pw_regExp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        text_name = (TextView) findViewById(R.id.text_name);
        text_email = (TextView) findViewById(R.id.text_email);
        text_pw = (TextView) findViewById(R.id.text_pw);
        text_pwre = (TextView) findViewById(R.id.text_pwre);
        match_pw = (TextView) findViewById(R.id.match_pw);
        match_pwre = (TextView) findViewById(R.id.match_pwre);
        text_profile = (TextView) findViewById(R.id.text_profile);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_email = (EditText) findViewById(R.id.edit_email);
        edit_pw = (EditText) findViewById(R.id.edit_pw);
        edit_pwre = (EditText) findViewById(R.id.edit_pwre);

        btn_signup = (Button) findViewById(R.id.btn_signup);


        edit_pw.addTextChangedListener(new TextWatcher() { //???????????? ??????
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edit_pw.getText().toString().replace(" ", "").equals("")) {
                    match_pw.setText("?????? ?????????, ?????????, ?????? ?????? 8??? ?????? 20??? ??????");
                    match_pw.setVisibility(View.VISIBLE);

                    if (!edit_pwre.getText().toString().replace(" ", "").equals("")) {
                        if (!edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                            match_pwre.setText("??????????????? ???????????? ????????????.");
                            match_pwre.setTextColor(Color.RED);
                            match_pwre.setVisibility(View.VISIBLE);
                        } else if (edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                            match_pwre.setText("??????????????? ???????????????.");
                            match_pwre.setTextColor(Color.parseColor("#00af29"));
                            match_pwre.setVisibility(View.VISIBLE);
                        } else if (edit_pwre.getText().toString().replace(" ", "").equals("")) {
                            match_pwre.setVisibility(View.INVISIBLE);
                        }
                    }

                } else {
                    if (!edit_pw.getText().toString().matches(pw_regExp)) {
                        match_pw.setText("?????? ?????????, ?????????, ?????? ?????? 8??? ?????? 20??? ??????");
                        match_pw.setVisibility(View.VISIBLE);

                        if (!edit_pwre.getText().toString().replace(" ", "").equals("")) {
                            if (!edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                                match_pwre.setText("??????????????? ???????????? ????????????.");
                                match_pwre.setTextColor(Color.RED);
                                match_pwre.setVisibility(View.VISIBLE);
                            } else if (edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                                match_pwre.setText("??????????????? ???????????????.");
                                match_pwre.setTextColor(Color.parseColor("#00af29"));
                                match_pwre.setVisibility(View.VISIBLE);
                            } else if (edit_pwre.getText().toString().replace(" ", "").equals("")) {
                                match_pwre.setVisibility(View.INVISIBLE);
                            }
                        }

                    } else if (edit_pw.getText().toString().matches(pw_regExp)) {
                        match_pw.setText("");
                        match_pw.setVisibility(View.INVISIBLE);

                        if (!edit_pwre.getText().toString().replace(" ", "").equals("")) {
                            if (!edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                                match_pwre.setText("??????????????? ???????????? ????????????.");
                                match_pwre.setTextColor(Color.RED);
                                match_pwre.setVisibility(View.VISIBLE);
                            } else if (edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                                match_pwre.setText("??????????????? ???????????????.");
                                match_pwre.setTextColor(Color.parseColor("#00af29"));
                                match_pwre.setVisibility(View.VISIBLE);
                            } else if (edit_pwre.getText().toString().replace(" ", "").equals("")) {
                                match_pwre.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                }

            }
        });


        edit_pwre.addTextChangedListener(new TextWatcher() { //???????????? ????????????
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edit_pw.getText().toString().replace(" ", "").equals("") && !edit_pwre.getText().toString().replace(" ", "").equals("")) {
                    if (!edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                        match_pwre.setText("??????????????? ???????????? ????????????.");
                        match_pwre.setTextColor(Color.RED);
                        match_pwre.setVisibility(View.VISIBLE);
                    } else if (edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                        match_pwre.setText("??????????????? ???????????????.");
                        match_pwre.setTextColor(Color.parseColor("#00af29"));
                        match_pwre.setVisibility(View.VISIBLE);
                    } else if (edit_pwre.getText().toString().replace(" ", "").equals("")) {
                        match_pwre.setVisibility(View.INVISIBLE);
                    }

                } else if (edit_pw.getText().toString().replace(" ", "").equals("") && edit_pwre.getText().toString().replace(" ", "").equals("")) {
                    match_pwre.setVisibility(View.INVISIBLE);
                } else if (!edit_pw.getText().toString().replace(" ", "").equals("") && edit_pwre.getText().toString().replace(" ", "").equals("")) {
                    match_pwre.setVisibility(View.INVISIBLE);
                }
            }
        });

        edit_pw.setOnFocusChangeListener(new View.OnFocusChangeListener() { //password ??? ???????????? ???????????? ???
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!edit_pw.getText().toString().replace(" ", "").equals("") && !edit_pwre.getText().toString().replace(" ", "").equals("")) {
                        if (!edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                            match_pwre.setText("??????????????? ???????????? ????????????.");
                            match_pwre.setTextColor(Color.RED);
                            match_pwre.setVisibility(View.VISIBLE);
                        } else if (edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                            match_pwre.setText("??????????????? ???????????????.");
                            match_pwre.setTextColor(Color.parseColor("#00af29"));
                            match_pwre.setVisibility(View.VISIBLE);
                        } else if (edit_pwre.getText().toString().replace(" ", "").equals("")) {
                            match_pwre.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });

        edit_pwre.setOnFocusChangeListener(new View.OnFocusChangeListener() { //password confirm ??? ???????????? ???????????? ???
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!edit_pw.getText().toString().replace(" ", "").equals("") && !edit_pwre.getText().toString().replace(" ", "").equals("")) {
                        if (!edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                            match_pwre.setText("??????????????? ???????????? ????????????.");
                            match_pwre.setTextColor(Color.RED);
                            match_pwre.setVisibility(View.VISIBLE);
                        } else if (edit_pwre.getText().toString().equals(edit_pw.getText().toString())) {
                            match_pwre.setText("??????????????? ???????????????.");
                            match_pwre.setTextColor(Color.parseColor("#00af29"));
                            match_pwre.setVisibility(View.VISIBLE);
                        } else if (edit_pwre.getText().toString().replace(" ", "").equals("")) {
                            match_pwre.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            }
        });

    }

    public void insert(View view) {  //btn_signup ?????? ??? ?????? ?????????

        if (edit_name.getText().toString().isEmpty() || edit_email.getText().toString().isEmpty() || edit_pw.getText().toString().isEmpty() || edit_pwre.getText().toString().isEmpty()) {

            Toast.makeText(getApplicationContext(), "??? ?????? ????????? ?????????", Toast.LENGTH_LONG).show();
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(edit_email.getText().toString()).matches()) {
                Toast.makeText(getApplicationContext(), "????????? ????????? ?????? ????????? ?????????", Toast.LENGTH_LONG).show();
            } else if (match_pw.getText().toString().equals("?????? ?????????, ?????????, ?????? ?????? 8??? ?????? 20??? ??????")) {
                Toast.makeText(getApplicationContext(), "??????????????? ????????? ?????? ???????????????", Toast.LENGTH_LONG).show();
            } else if (match_pwre.getText().toString().equals("??????????????? ???????????? ????????????.")) {
                Toast.makeText(getApplicationContext(), "??????????????? ???????????? ??????????????????", Toast.LENGTH_LONG).show();
            } else if (match_pwre.getText().toString().replace(" ", "").equals("")) {
                Toast.makeText(getApplicationContext(), "??? ?????? ????????? ?????????", Toast.LENGTH_LONG).show();
            }  else if (match_pwre.getText().toString().equals("??????????????? ???????????????.")) {
                String name = edit_name.getText().toString();
                String email = edit_email.getText().toString();
                String pw = edit_pw.getText().toString();

                insertoToDatabase(name, email, pw); //Id, Pw??? ?????? mysql??? ?????? ????????????????????? ??????
            }
        }
    }

    private void insertoToDatabase(String name, String email, String pw) { //???????????? ??????
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignupActivity.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if (s.equals("success")) {
                    Intent intent = new Intent(SignupActivity.this, DustActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "??????????????????", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    String name = (String) params[0];
                    String email = (String) params[1];
                    String pw = (String) params[2];

                    String link = "http://ip/signup.php";
                    String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") +
                            "&" + URLEncoder.encode("pw", "UTF-8") + "=" + URLEncoder.encode(pw, "UTF-8");

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
        task.execute(name, email, pw);
    }

}

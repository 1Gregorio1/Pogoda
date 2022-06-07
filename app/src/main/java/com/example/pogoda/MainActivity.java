package com.example.pogoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText pogoda;
    private Button button_main;
    private TextView result;
    private EditText con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pogoda = findViewById(R.id.pogoda);
        button_main = findViewById(R.id.button_main);
        result = findViewById(R.id.result);

        button_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pogoda.getText().toString().equals(""))
                Toast.makeText(MainActivity.this, R.string.no_text, Toast.LENGTH_SHORT).show();
                    else {
                    String city = pogoda.getText().toString();
                    String key = "e7b4cbc1bb3f56d422bf53c93ef78ae4";
                    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru";
                    new GetURL().execute(url);
                }

            }
        });

    }

    private class GetURL extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection con = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                con = (HttpURLConnection) url.openConnection();
                con.connect();

                InputStream stream = con.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null)
                    buffer.append(line).append("\n");

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    con.disconnect();

                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;


        }

        @Override
        protected  void onPostExecute(String resultStr) {
            super.onPostExecute(resultStr);

            try {
                JSONObject obj = new JSONObject(resultStr);
                result.setText("Температура:" + obj.getJSONObject("main").getDouble("temp"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
           // String url1 = "https://api.openweathermap.org/data/2.5/onecall?lat=" + result.setText(obj.getJSONObject("coord").getDouble("lat")) + "&" + result.setText(obj.getJSONObject("coord").getDouble("lon")) + "&appid=e7b4cbc1bb3f56d422bf53c93ef78ae4";
           // new GetURL().execute(url1);
        }


    }
}
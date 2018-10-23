package com.example.master.parsejson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String dataParsed ="";
    private Button btn;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new TransTask()
                .execute("https://api.myjson.com/bins/j5f6b");

        btn = (Button)findViewById(R.id.button);
        data = (TextView)findViewById(R.id.fetcheddata);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data.setText(dataParsed);
            }
        });

    }
    class TransTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(url.openStream()));
                String line = in.readLine();
                while(line!=null){
                    Log.d("HTTP", line);
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("JSON", s);
            parseJSON(s);
        }
        private void parseJSON(String s) {
            try {
                JSONArray JA = new JSONArray(s);
                for (int i=0; i<JA.length(); i++)
                {
                    JSONObject JO = JA.getJSONObject(i);
                    /*
                    String account = JO.getString("account");
                    String date = JO.getString("date");
                    int amount = JO.getInt("amount");
                    int type = JO.getInt("type");
                    dataParsed += account+"/n"+date+"/n"+amount+"/n"+type;
                    Log.d("JSON:",account+"/"+date+"/"+amount+"/"+type);*/
                    dataParsed  +=  "Name:" + JO.get("name") + "\n"+
                            "Password:" + JO.get("password") + "\n"+
                            "Contact:" + JO.get("contact") + "\n"+
                            "Country:" + JO.get("country") + "\n";
                    Log.d("JSON:",dataParsed);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

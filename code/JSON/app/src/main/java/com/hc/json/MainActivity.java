package com.hc.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try {
            JSONObject person = new JSONObject();
            person.put("name", "liulei");
            person.put("age", 28);

            JSONObject result = new JSONObject();
            result.put("grade", "awe");
            result.put("person", person);

            String resultStr = result.toString();
            Log.d(TAG, "onCreate: resultStr " + resultStr);

            String personStr = result.getString("person");
            Log.d(TAG, "onCreate: personStr " + personStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

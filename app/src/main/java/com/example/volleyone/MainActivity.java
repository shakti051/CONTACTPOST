package com.example.volleyone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText personName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        personName = findViewById(R.id.etPName);

        final String myURL = "https://apex.oracle.com/pls/apex/surb/contact_api/contact_api/";
        Button myButton = findViewById(R.id.btnContactSave);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, myURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                        VolleyLog.wtf(response.toString(), "utf-8");

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    Log.e("TAG", "jsonObject" + jsonObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Log.e("JSON Parser", "Error parsing data [" + e.getMessage() + "] " + response);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("a", apiParameter());
                        Log.e("TAG", "params" + params.put("a", apiParameter()));
                        return params;
                    }
                };

                MyApplication.getInstance().addToRequestQueue(stringRequest);

            }

        });
    }

    private String apiParameter() {
        String urlString = null;

        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject1 = new JSONObject();
            jsonObject.put("API", "insert_api_contact");
            jsonObject.put("CURD_OPERATION", "I");
            jsonObject.put("person_name", personName.getText());
            jsonObject.put("designation", "manager");
            jsonObject.put("mobile", "11122111");
            jsonObject.put("landline", "0120228964");
            jsonObject.put("email_id", "s11dds@gmail.com");
            jsonObject1.put("ITEMS", jsonObject);
            urlString = jsonObject1.toString();
            Log.e("TAG", "urlString" + urlString);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("EXCEPTION>>>", "" + String.valueOf(e));
        }
        return urlString;
    }
}

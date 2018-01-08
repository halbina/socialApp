package com.example.laptopi.myapp;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener {


    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";


    EditText nameText, emailText, passText;
    Button signupButton;
    ProgressDialog progressDialog;

    TextView linkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, FeedsActivity.class));
            return;
        }

        nameText = (EditText) findViewById(R.id.name_text);
        emailText = (EditText) findViewById(R.id.email_text);
        passText = (EditText) findViewById(R.id.pass_text);
        signupButton = (Button) findViewById(R.id.btn_signup);

        linkButton = (TextView) findViewById(R.id.linkButton);

        signupButton.setOnClickListener(this);
        linkButton.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);

    }

    private void registerUser() {
        final String username = nameText.getText().toString().trim();
        final String email = emailText.getText().toString().trim();
        final String password = passText.getText().toString().trim();

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USERNAME, username);
                params.put(KEY_EMAIL, email);
                params.put(KEY_PASSWORD, password);
                return params;


            }

        };

        // stringRequest.setRetryPolicy(new DefaultRetryPolicy(
        //       50000,
        //     DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        //   DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == signupButton)
            registerUser();
        if (v == linkButton)
            startActivity(new Intent(this, LoginActivity.class));

    }
}








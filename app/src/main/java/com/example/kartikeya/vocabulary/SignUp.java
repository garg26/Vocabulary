package com.example.kartikeya.vocabulary;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private static final String TAG = SignUp.class.getSimpleName();
    private Toolbar toolbar;
    private EditText editText3, editText4, editText5, editText6;
    private Button button;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, inputLayoutConfirmPassword;
    private ProgressDialog pDialog;
    private database db;
    private SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        editText5 = (EditText) findViewById(R.id.editText5);
        editText6 = (EditText) findViewById(R.id.editText6);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_Email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_Password);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_Confirm_Password);
        button = (Button) findViewById(R.id.button);


        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        db = new database(getApplicationContext());
        if(session.isLoggedIn()) {
            Intent i = new Intent(SignUp.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               final String name  = editText3.getText().toString().trim();
               final String email = editText4.getText().toString().trim();
               final String password = editText6.getText().toString().trim();

                if (!validateName() || !validateEmail() || !validatePassword() || !validateConfirmPassword() || !isOnline(SignUp.this)) {
                    Log.d(TAG, "Successfull");
                    return;
                }
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            registerUser(name,email,password);
                        }
                    }).start();

                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }



        });
        editText3.addTextChangedListener(new MyTextWatcher(editText3));
        editText4.addTextChangedListener(new MyTextWatcher(editText4));
        editText5.addTextChangedListener(new MyTextWatcher(editText5));
        editText6.addTextChangedListener(new MyTextWatcher(editText6));


    }
    private boolean isOnline(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        Toast.makeText(SignUp.this,"No network connection", Toast.LENGTH_LONG).show();
        return false;
    }

      public boolean validateName()
       {
        String name = editText3.getText().toString().trim();
        if(name.isEmpty())
        {
            inputLayoutName.setError("Enter the Name");
            requestFocus(editText3);
            return false;
        }
        else
            inputLayoutName.setErrorEnabled(false);
        return true;

    }

    public boolean validateEmail()
    {
        String email = editText4.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(editText4);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validatePassword()
    {
        String password = editText6.getText().toString().trim();

        if(password.isEmpty()) {
            inputLayoutPassword.setError("Password is empty");
            requestFocus(editText6);
            return false;
        }
        else
            inputLayoutName.setErrorEnabled(false);
        return true;
    }

    public boolean validateConfirmPassword(){
        String confirmPassword = editText5.getText().toString().trim();
        if(confirmPassword.isEmpty() /*|| password.equals(confirmPassword)*/)
        {
            inputLayoutConfirmPassword.setError("Password does not match");
            requestFocus(editText5);
            return false;
        }
        else
            inputLayoutConfirmPassword.setErrorEnabled(true);
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.editText3:
                    validateName();
                    break;
                case R.id.editText4:
                    validateEmail();
                    break;
                case R.id.editText6:
                    validatePassword();
                    break;
                case R.id.editText5:
                    validateConfirmPassword();
                    break;
            }
        }
    }
    public void registerUser( final String name, final String email, final  String password){
        String tag_string_req = "req_request";
        pDialog.setMessage("Registering ...");
        showDialog();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest strReq = new StringRequest(Request.Method.POST, appConfig.url_sign, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,name + " " + email + " " + password );
                Log.d(TAG, "kartikeya garg garg kartikeya");
                Log.d(TAG, "Login response" + response.toString());
                //hideDialog();
                try {
                    Log.d(TAG,"garg garg kartikeya" );
                    Log.d(TAG,name + " " + email + " " + password );
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        Log.d(TAG,name + " " + email + " " + password );
                        String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user.getString("created_at");
                        db.addUser(name, email, uid, created_at);
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                        // Launch login activity
                        Intent intent = new Intent(
                                SignUp.this,
                                Login.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    Log.e("Volley", "Error. HTTP Status Code:"+networkResponse.statusCode);
                }
                if (error instanceof TimeoutError) {
                    Log.e("Volley", "TimeoutError");
                }else if(error instanceof NoConnectionError){
                    Log.e("Volley", "NoConnectionError");
                } else if (error instanceof AuthFailureError) {

                    Log.e("Volley", "AuthFailureError");
                } else if (error instanceof ServerError) {
                    Log.e("Volley", "ServerError");
                } else if (error instanceof NetworkError) {
                    Log.e("Volley", "NetworkError");
                } else if (error instanceof ParseError) {
                    Log.e("Volley", "ParseError");
                }
                //Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
               // hideDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        requestQueue.add(strReq);
    }

    private void showDialog() {

        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
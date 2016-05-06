package com.example.kartikeya.vocabulary;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    ImageButton imageButton3;
    TextView textView4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Dialog dialog = new Dialog(Login.this);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Login");

        imageButton3 = (ImageButton)findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), SignUp.class);
                startActivity(i);
            }
        });
    }
}

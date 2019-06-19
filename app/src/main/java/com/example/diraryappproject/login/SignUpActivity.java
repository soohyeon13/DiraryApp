package com.example.diraryappproject.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.diraryappproject.HttpConnection;
import com.example.diraryappproject.R;

public class SignUpActivity extends AppCompatActivity {
    EditText signUpEmail, signUpPassword;
    Button submitBtn, cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        signUpEmail = findViewById(R.id.signUpEmail);

        signUpPassword = findViewById(R.id.signUpPassword);


        final String url_signup = "http://192.168.43.150/";


        submitBtn = findViewById(R.id.signUpSubmit);
        cancelBtn = findViewById(R.id.signUpCancel);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                final String url_body = "user/creat?username="+email+"&"+"userpassword="+password;
                HttpConnection httpConnection = new HttpConnection(SignUpActivity.this);
                httpConnection.setUrl_Home(url_signup);
                httpConnection.setBody(url_body);
                httpConnection.execute(email,password);
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}



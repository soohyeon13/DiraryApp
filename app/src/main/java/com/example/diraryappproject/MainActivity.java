package com.example.diraryappproject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.diraryappproject.Calendar.CalendarView;
import com.example.diraryappproject.data.User;
import com.example.diraryappproject.signup.SignUpActivity;
import com.example.diraryappproject.task.HttpPostTask;
import com.google.android.gms.common.util.Base64Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    EditText loginEmail, loginPassword;
    TextView noLoginEmail;
    Button loginBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        loginBtn = findViewById(R.id.loginBtn);
        loginEmail = findViewById(R.id.emailEdit);
        loginPassword = findViewById(R.id.passwordEdit);
        signUpBtn = findViewById(R.id.noEmailBtn);
        signUpBtn.setPaintFlags(signUpBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        noLoginEmail = findViewById(R.id.noEmailBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String user = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                String text = user + ":" + password;
                try {
                    String auth = encode(text);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                final String authorization = Base64Utils.encode((user + ":" + password).getBytes());
                final String url_signin = getString(R.string.base_uri)+"/user/me";
                final String url_authorization = "Basic " + authorization;
                JSONObject result = null;
                try {
                    result = new JSONObject(new HttpPostTask(url_signin, url_authorization).execute().get());
                    System.out.println(result.toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject userResult = result.getJSONObject("user");
                    User.setUser(userResult);
//                    int a = User.getUser().getInt("id");
//                    System.out.println(a);
                    Intent intent = new Intent(MainActivity.this, CalendarView.class);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(MainActivity.this, "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public static String encode(String user) throws UnsupportedEncodingException {
        byte[] data = user.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

}

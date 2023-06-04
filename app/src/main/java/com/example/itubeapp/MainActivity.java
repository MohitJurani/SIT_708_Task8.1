package com.example.itubeapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btnLogin, btnSignUp;

    private EditText etUsername, etPassword;

    private UserItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        etUsername = findViewById(R.id.et_1);
        etPassword = findViewById(R.id.et_2);

        item = new UserItem();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignupActivity.class));
            }
        });
        btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etUsername.getText().toString())) {
                Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                List<UserItem> items = DatabaseHelper.getInstance(this).getUserItems();
                boolean isValid = false;
                if (items != null && items.size() > 0){
                    for (int i = 0 ; i < items.size(); i++){
                        if (username.equals(items.get(i).getUsername()) && password.equals(items.get(i).getPassword())) {
                            isValid = true;
                        }
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please signup first",
                            Toast.LENGTH_SHORT).show();
                }

                if (isValid) {
                    Toast.makeText(MainActivity.this, "Logged in..",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, EnterUrlActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid username or password..",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.itubeapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {
    private Button btnSave;

    private EditText etName, etUsername, etPassword, etConfirmPassword;

    private UserItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnSave = findViewById(R.id.btnCreateAcc);
        etName = findViewById(R.id.et_1);
        etUsername = findViewById(R.id.et_2);
        etPassword = findViewById(R.id.et_3);
        etConfirmPassword = findViewById(R.id.et_4);


        item = new UserItem();
        btnSave.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etName.getText().toString())) {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etUsername.getText().toString())) {
                Toast.makeText(this, "Please enter usename", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etPassword.getText().toString())) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etConfirmPassword.getText().toString())) {
                Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            } else if (!etPassword.getText().toString().equalsIgnoreCase(etConfirmPassword.getText().toString())) {
                Toast.makeText(this, "Password does not matching", Toast.LENGTH_SHORT).show();
            } else {
                item.setName(etName.getText().toString());
                item.setUsername(etUsername.getText().toString());
                item.setPassword(etPassword.getText().toString());
                long id = DatabaseHelper.getInstance(this).insertUserItem(item);
                if (id > 0) {
                    Toast.makeText(SignupActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(SignupActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
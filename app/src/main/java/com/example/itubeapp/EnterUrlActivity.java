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

public class EnterUrlActivity extends AppCompatActivity {
    private Button btnPlay, btnPlaylist, btnAddToPlaylist, btnPlayMedia;

    private EditText etUrl;

    private UserItem item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_url);
        btnPlay = findViewById(R.id.btnPlay);
        btnPlaylist = findViewById(R.id.btnPlayList);
        btnAddToPlaylist = findViewById(R.id.btnAddToPlayList);
        btnPlayMedia = findViewById(R.id.btnPlayMedia);
        etUrl = findViewById(R.id.et_1);

        item = new UserItem();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUrl.getText().toString())) {
                    Toast.makeText(EnterUrlActivity.this, "Please enter youtube url to play.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(EnterUrlActivity.this, WebViewActivity.class);
                    intent.putExtra("youtubeurl", etUrl.getText().toString());
                    startActivity(intent);
                }
            }
        });
        btnPlaylist.setOnClickListener(v -> {
            Intent intent = new Intent(EnterUrlActivity.this, ViewPlaylistActivity.class);
            intent.putExtra("youtubeurl", etUrl.getText().toString());
            startActivity(intent);
        });

        btnAddToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUrl.getText().toString())) {
                    Toast.makeText(EnterUrlActivity.this, "Please enter item to add to playlist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EnterUrlActivity.this, "Item added to playlist",
                            Toast.LENGTH_SHORT).show();
                    DatabaseHelper.getInstance(EnterUrlActivity.this).insertPlaylistItem(etUrl.getText().toString());
                }
            }
        });
        btnPlayMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterUrlActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });
    }
}
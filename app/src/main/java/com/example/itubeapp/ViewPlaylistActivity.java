package com.example.itubeapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewPlaylistActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_view);
        recyclerView = findViewById(R.id.recyclerView);

        list = DatabaseHelper.getInstance(this).getPlayListItems();
        setAdapter();
    }

    private void setAdapter() {
        ViewPlayListAdapter adapter = new ViewPlayListAdapter(list, ViewPlaylistActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
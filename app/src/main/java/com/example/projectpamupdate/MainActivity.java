package com.example.projectpamupdate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button button_buatan_saya;
    private Button button_tersimpan;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button_buatan_saya = findViewById(R.id.button_buatan_saya);
        this.button_tersimpan = findViewById(R.id.button_tersimpan);
        this.fm = this.getSupportFragmentManager();

        this.button_tersimpan.setOnClickListener(this);
        this.button_buatan_saya.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        button_tersimpan.setSelected(false);
        button_buatan_saya.setSelected(false);

        if (view.getId() == R.id.button_tersimpan) {
            view.setSelected(true);
            fm.beginTransaction().replace(R.id.container_frag, new FragmentSimpan()).commit();
        } else if (view.getId() == R.id.button_buatan_saya) {
            view.setSelected(true);
            fm.beginTransaction().replace(R.id.container_frag, new FragmentSaya()).commit();
        }
    }
}
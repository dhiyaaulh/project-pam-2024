package com.example.projectpamupdate;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Burger> burgerList;
    private BurgerAdapter burgerAdapter;
    private RecyclerView rvBurger;
    private static final int REQUEST_EDIT_BURGER = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvBurger = findViewById(R.id.rvBurger);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rvBurger.setLayoutManager(gridLayoutManager);

        burgerList = new ArrayList<>();
        burgerAdapter = new BurgerAdapter(this, burgerList);
        rvBurger.setAdapter(burgerAdapter);

        String urlstr = "http://172.16.118.88/projectPAMbrgr/burger.php";
        new fetchData().execute(urlstr);

        burgerAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_EDIT_BURGER);
        });
    }

    private class fetchData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urlstr) {
            StringBuilder response = new StringBuilder();
            try {
                URL url = new URL(urlstr[0]);

                HttpURLConnection koneksi = (HttpURLConnection) url.openConnection();
                koneksi.setRequestMethod("GET");
                koneksi.setDoInput(true);
                koneksi.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(koneksi.getInputStream()));
                String line;

                while((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                koneksi.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Gagal memuat data", Toast.LENGTH_SHORT).show());
            }
            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            parseBurger(result);
        }
    }

    private void parseBurger(String jsonData) {
        Gson gson = new Gson();
        Burger[] burgerArray = gson.fromJson(jsonData, Burger[].class);

        burgerList.clear();
        for (Burger b : burgerArray) {
            this.burgerList.add(b);
        }

        // Refresh adapter di UI thread
        runOnUiThread(() -> burgerAdapter.notifyDataSetChanged());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_BURGER && resultCode == RESULT_OK && data != null) {
            int position = data.getIntExtra("position", -1);
            if (position != -1) {
                // Hapus item dari list
                burgerList.remove(position);
                burgerAdapter.notifyItemRemoved(position);
                // Tampilkan notifikasi Toast
                Toast.makeText(this, "Item dihapus", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
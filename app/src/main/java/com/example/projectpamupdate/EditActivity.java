package com.example.projectpamupdate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class EditActivity extends AppCompatActivity {
    private Button back_1;
    private Button delete;
    private TextView nama;
    private List<Burger> burgers;
    private Burger burger;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        this.back_1 = findViewById(R.id.button_back);
        this.delete = findViewById(R.id.button_hapus_burger);
        this.nama = findViewById(R.id.nama_burger);

        Burger b;

        Intent i = getIntent();
        this.nama.setText(i.getStringExtra("nama"));
        this.position = i.getIntExtra("position", -1);

        this.back_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        this.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hapus = new Intent();
                hapus.putExtra("position", position);
                setResult(RESULT_OK, hapus);
                Toast.makeText(EditActivity.this, "Item dihapus", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}
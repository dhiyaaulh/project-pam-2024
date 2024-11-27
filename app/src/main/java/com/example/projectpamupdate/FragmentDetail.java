package com.example.projectpamupdate;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentDetail extends Fragment implements View.OnClickListener {
    private TextView nama_burger, resep;
    private Button kembali, delete, edit;
    private Bundle args;
    private Burger detailed;

    public final String dbURL = "https://project-burger-ed361-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private FirebaseDatabase db;
    private DatabaseReference ref;

    public FragmentDetail() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail, container, false);

        this.nama_burger = v.findViewById(R.id.nama_burger);
        this.resep = v.findViewById(R.id.tvResep);
        this.kembali = v.findViewById(R.id.button_back);
        this.delete = v.findViewById(R.id.button_hapus_burger);
        this.edit = v.findViewById(R.id.button_edit_burger);

        this.db = FirebaseDatabase.getInstance(dbURL);
        this.ref = this.db.getReference("burger");

        args = getArguments();
        String id = args.getString("id");

        ref.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Mengubah snapshot menjadi objek Burger
                    detailed = dataSnapshot.getValue(Burger.class);
                } else {
                    Log.d("FirebaseGet", "Data tidak ditemukan untuk ID: " + id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        this.nama_burger.setText(detailed.getNama());
        this.resep.setText(detailed.getDeskripsi());

        this.kembali.setOnClickListener(this);
        this.edit.setOnClickListener(this);
        this.delete.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        if (view.getId() == R.id.button_hapus_burger) {
            new Thread(() -> {
                // Ambil argumen burger dari bundle
                Bundle args = getArguments();
                if (args != null) {
                    String id = args.getString("id");

                    // Cari burger berdasarkan nama
//                    Burger burger = db.burgerDao().getBurgerById(id);

                    if (detailed != null) {
                        // Hapus burger jika ditemukan
                        ref.child(id).removeValue();

                        // Update UI di thread utama
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "Burger deleted successfully!", Toast.LENGTH_SHORT).show();
                            // Kembali ke fragment sebelumnya
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // Ganti nama variabel
                            fragmentManager.popBackStack();
                        });
                    } else {
                        requireActivity().runOnUiThread(() -> {
                            Toast.makeText(requireContext(), "Burger not found!", Toast.LENGTH_SHORT).show();
                        });
                    }
                }
            }).start();
        }
        if (view.getId() == R.id.button_edit_burger) {
            Bundle bundle = new Bundle();
            FragmentSaya edit = new FragmentSaya();
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            new Thread(() -> {
                String[] pilihan = {
                        detailed.getNama(), detailed.getRoti(), detailed.getDaging(),
                        detailed.getPelengkap(), detailed.getSaus()
                };
                bundle.putStringArray("pilihan", pilihan);
                bundle.putInt("id", args.getInt("id"));
                edit.setArguments(bundle);
                fm.beginTransaction().replace(R.id.container_frag, edit).addToBackStack(null).commit();
            }).start();
        }
    }
}
package com.example.projectpamupdate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class BurgerAdapter extends RecyclerView.Adapter {
    private final Context ctx;
    private final List<Burger> burgers;
    private final OnItemClickListener listener;
    private DatabaseReference dbRef;

    public BurgerAdapter(Context ctx, List<Burger> burgers, OnItemClickListener listener) {
        this.ctx = ctx;
        this.burgers = burgers;
        this.listener = listener;
    }

    public void setDbRef(DatabaseReference dbRef) {
        this.dbRef = dbRef;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    private class VH extends RecyclerView.ViewHolder {
        private final TextView nama_burger;
        private final ImageButton gambar_burger;
        private Burger burger;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.nama_burger = itemView.findViewById(R.id.nama_burger);
            this.gambar_burger = itemView.findViewById(R.id.button_burger);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.ctx).inflate(R.layout.item_burger, parent, false);
        VH vh =new VH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Burger b = this.burgers.get(position);
        VH vh = (VH) holder;
        vh.nama_burger.setText(b.nama);
        vh.gambar_burger.setImageResource(R.drawable.logo_burger_1);
        vh.gambar_burger.setOnClickListener(view -> {
            listener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return this.burgers.size();
    }
}

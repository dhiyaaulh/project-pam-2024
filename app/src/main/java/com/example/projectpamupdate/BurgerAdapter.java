package com.example.projectpamupdate;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.getString;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BurgerAdapter extends RecyclerView.Adapter {
    private final Context ctx;
    private final List<Burger> burgers;
    private static final int REQUEST_EDIT_BURGER = 1;
    private BurgerAdapter.OnItemClickListener onItemClickListener;


    public BurgerAdapter(Context ctx, List<Burger> burgers) {
        this.ctx = ctx;
        this.burgers = burgers;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(BurgerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private class VH extends RecyclerView.ViewHolder {
        private final TextView nama_burger;
        private final ImageButton gambar_burger;

        public VH(@NonNull View itemView) {
            super(itemView);
            this.nama_burger = itemView.findViewById(R.id.nama_burger);
            this.gambar_burger = itemView.findViewById(R.id.button_burger);
            gambar_burger.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(position);
                    }
                }
            });
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
    }

    @Override
    public int getItemCount() {
        return this.burgers.size();
    }
}

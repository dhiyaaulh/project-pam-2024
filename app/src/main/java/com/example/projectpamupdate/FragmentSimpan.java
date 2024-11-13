package com.example.projectpamupdate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FragmentSimpan extends Fragment {
    private static final int REQUEST_EDIT_BURGER = 1;
    private List<Burger> burgerList;
    private BurgerAdapter burgerAdapter;
    private RecyclerView rvBurger;

    public FragmentSimpan(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_simpan, container, false);
        rvBurger = v.findViewById(R.id.rvBurger);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        rvBurger.setLayoutManager(gridLayoutManager);

        burgerList = new ArrayList<>();
        Burger burger1 = new Burger("Veggie Burger", "Roti", "Daging", "Sayur", "Saus Tomat");
        Burger burger2 = new Burger("Cheeseburger", "Roti", "Daging", "Sayur", "Saus Tomat");
        Burger burger3 = new Burger("Big Mac", "Roti", "Daging Double", "Sayur", "Saus Tomat");
        burgerList.add(burger1);
        burgerList.add(burger2);
        burgerList.add(burger3);

        burgerAdapter = new BurgerAdapter(this.getActivity(), burgerList);
        rvBurger.setAdapter(burgerAdapter);

        burgerAdapter.setOnItemClickListener(position -> {
            Intent intent = new Intent(this.getActivity(), EditActivity.class);
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_EDIT_BURGER);
        });

        return v;
    }
}

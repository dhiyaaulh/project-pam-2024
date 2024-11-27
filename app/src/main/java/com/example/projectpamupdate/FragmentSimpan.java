package com.example.projectpamupdate;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentSimpan extends Fragment implements BurgerAdapter.OnItemClickListener {
    public final String dbURL = "https://project-burger-ed361-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private FirebaseDatabase db;
    private DatabaseReference ref;

    private List<Burger> burgerList;
    private BurgerAdapter burgerAdapter;
    private RecyclerView rvBurger;

    private Handler handler;


    public FragmentSimpan(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_simpan, container, false);
        rvBurger = v.findViewById(R.id.rvBurger);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(), 2);
        rvBurger.setLayoutManager(gridLayoutManager);

        burgerList = new ArrayList<>();
        burgerAdapter = new BurgerAdapter(this.getActivity(), burgerList,this);
        rvBurger.setAdapter(burgerAdapter);


        this.handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                burgerAdapter.notifyDataSetChanged();
            }
        };

        this.db = FirebaseDatabase.getInstance(dbURL);
        this.ref = this.db.getReference("burger");
        burgerAdapter.setDbRef(this.ref);

        Thread t = new Thread(() -> {
            this.ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    burgerList.clear();
                    for (DataSnapshot b : snapshot.getChildren()) {
                        Burger burger = b.getValue(Burger.class);
                        burgerList.add(burger);
                    }
                    burgerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        t.start();

        return v;
    }

    @Override
    public void onItemClick(int position) {
        Burger selected = this.burgerList.get(position);
        Bundle bundle = new Bundle();
        FragmentDetail detail = new FragmentDetail();
        FragmentManager fm = this.requireActivity().getSupportFragmentManager();

        Thread t = new Thread(() -> {
            bundle.putString("id", selected.getId());
            detail.setArguments(bundle);
            fm.beginTransaction().replace(R.id.container_frag, detail).addToBackStack(null).commit();
        });
        t.start();
    }
}

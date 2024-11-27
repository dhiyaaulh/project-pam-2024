package com.example.projectpamupdate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentSaya extends Fragment implements View.OnClickListener {
    private Spinner spinnerRoti, spinnerDaging, spinnerSaus, spinnerPelengkap;
    public final String dbURL = "https://project-burger-ed361-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private Button buttonSave;
    private EditText etNama;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private int id = -1;


    public FragmentSaya(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_saya, container, false);

        spinnerRoti = v.findViewById(R.id.spinner_roti);
        spinnerDaging = v.findViewById(R.id.spinner_daging);
        spinnerSaus = v.findViewById(R.id.spinner_saus);
        spinnerPelengkap = v.findViewById(R.id.spinner_pelengkap);
        buttonSave = v.findViewById(R.id.button_save);
        etNama = v.findViewById(R.id.etNama);

        this.db = FirebaseDatabase.getInstance(dbURL);
        this.ref = this.db.getReference("burger");

        this.buttonSave.setOnClickListener(this);

//        if(getArguments() != null){
//            String[] pilihan = getArguments().getStringArray("pilihan");
//            id = getArguments().getInt("id", -1);
//            etNama.setText(pilihan[0]);
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, pilihan);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            spinnerRoti.setAdapter(adapter);
//            spinnerDaging.setAdapter(adapter);
//            spinnerSaus.setAdapter(adapter);
//            spinnerPelengkap.setAdapter(adapter);
//        }
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button_save){
            String id = this.ref.push().getKey();
            String nama = etNama.getText().toString();
            String selectedRoti = spinnerRoti.getSelectedItem().toString();
            String selectedDaging = spinnerDaging.getSelectedItem().toString();
            String selectedSaus = spinnerSaus.getSelectedItem().toString();
            String selectedPelengkap = spinnerPelengkap.getSelectedItem().toString();

            Burger newBurger = new Burger(nama, selectedRoti, selectedDaging, selectedPelengkap, selectedSaus);

            new Thread(() -> {
                newBurger.setId(id);
                ref.child(id).setValue(newBurger);
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Burger added successfully!", Toast.LENGTH_SHORT).show()
                );
            }).start();
        }
    }
}

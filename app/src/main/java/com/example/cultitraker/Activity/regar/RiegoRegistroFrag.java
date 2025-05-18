package com.example.cultitraker.Activity.regar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cultitraker.Models.Enums.TipoInsumo;
import com.example.cultitraker.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RiegoRegistroFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RiegoRegistroFrag extends Fragment {

    private Spinner spinnerTipoInsumo;
    private EditText fechaInsumo;
    private MaterialButton btnFechaInsumo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RiegoRegistroFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_riego_registro.
     */
    // TODO: Rename and change types and number of parameters
    public static RiegoRegistroFrag newInstance(String param1, String param2) {
        RiegoRegistroFrag fragment = new RiegoRegistroFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_riego_registro, container, false);

        fechaInsumo = view.findViewById(R.id.txt_FechaInsumo);
        btnFechaInsumo = view.findViewById(R.id.btn_FechaInsumo);

        TipoInsumo[] tipoInsumo = TipoInsumo.values();
        ArrayAdapter<TipoInsumo> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, tipoInsumo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btnFechaInsumo.setOnClickListener(v -> {
            MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Selecciona una fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            materialDatePicker.addOnPositiveButtonClickListener((MaterialPickerOnPositiveButtonClickListener<Long>) selection -> {
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                fechaInsumo.setText(MessageFormat.format("{0}", date));
            });

            materialDatePicker.show(getParentFragmentManager(), "tag");
        });

        return view;
    }
}
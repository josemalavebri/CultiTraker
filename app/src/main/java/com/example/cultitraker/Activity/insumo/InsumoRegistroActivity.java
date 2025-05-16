package com.example.cultitraker.Activity.insumo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cultitraker.Models.Enums.TipoInsumo;
import com.example.cultitraker.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class InsumoRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_insumo_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TipoInsumo[] tipoInsumo = TipoInsumo.values();
        Spinner spinner = findViewById(R.id.txt_TipoInsumo);
        ArrayAdapter<TipoInsumo> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, tipoInsumo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        MaterialButton button = findViewById(R.id.btn_FechaInsumo);
        EditText fecha_txt=findViewById(R.id.txt_FechaInsumo);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               MaterialDatePicker<Long>material=MaterialDatePicker.Builder.datePicker()
                       .setTitleText("Selecciona una fecha")
                       .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                       .build();
               material.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                   @Override
                   public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                        fecha_txt.setText(MessageFormat.format("{0}",date));
                   }
               });
               material.show(getSupportFragmentManager(),"tag");
           }
       });
    }


}
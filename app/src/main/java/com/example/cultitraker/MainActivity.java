package com.example.cultitraker;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cultitraker.Activity.Cultivo.CultivoFrag;
import com.example.cultitraker.Activity.insumo.InsumoFrag;
import com.example.cultitraker.Activity.parcela.ParcelaTierraFrag;
import com.example.cultitraker.Activity.regar.RiegoFrag;
import com.example.cultitraker.Activity.tareas.TareasFragment;
import com.example.cultitraker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        cambiarFragment(new InsumoFrag());


        activityMainBinding.bnvPrincipal.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.parcelaMenu) {
                cambiarFragment(new ParcelaTierraFrag());
            } else if (id == R.id.cultivoMenu) {
                cambiarFragment(new CultivoFrag());
            } else if (id == R.id.tareaMenu) {
                cambiarFragment(new TareasFragment());
            }
              else if (id == R.id.riegosMenu) {
                cambiarFragment(new RiegoFrag());
            }
            else if (id == R.id.insumosMenu) {
                cambiarFragment(new InsumoFrag());
            }
            return true;
        });


    }

    private void cambiarFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frl_principal,fragment);
        fragmentTransaction.commit();

    }


}
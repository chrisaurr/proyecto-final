package com.example.proyectoprogramacioniii;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConDepartamento;
import com.example.proyectoprogramacioniii.adapters.ViewPagerAdapter;
import com.example.proyectoprogramacioniii.databinding.ActivityMainBinding;
import com.example.proyectoprogramacioniii.viewmodels.DepartamentoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MainActivityViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MunicipioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.PrivilegioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolAccesoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolViewModel;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    @Inject
    public MainActivityViewModel mMainActivityViewModel;
    @Inject
    public DepartamentoViewModel mDepartamentoViewModel;

    public static MainActivity myContext;

    public MainActivity(){
        myContext = this;
    }

    public static MainActivity getInstance(){
        return myContext;
    }

    private ActivityMainBinding binding;
    private ViewPagerAdapter adapter;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //btn = findViewById(R.id.btn);

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Login"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Registro"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        adapter = new ViewPagerAdapter(fragmentManager, getLifecycle());
        binding.viewPager.setAdapter(adapter);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

    }

}
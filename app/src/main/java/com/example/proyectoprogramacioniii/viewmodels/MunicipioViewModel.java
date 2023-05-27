package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.DepartamentoConMunicipio;
import com.example.proyectoprogramacioniii.repositories.MunicipioRepo;

import java.util.List;

import javax.inject.Inject;

public class MunicipioViewModel extends ViewModel {

    private MunicipioRepo municipioRepo;
    private LiveData<List<Municipio>> obtenerMunicipio;

    @Inject
    public MunicipioViewModel(MunicipioRepo municipioRepo) {
        this.municipioRepo = municipioRepo;
        this.obtenerMunicipio = municipioRepo.getAllMunicipios();
    }

    public void agregarMunicipio(int id, String nombre, int id_departamento){
        municipioRepo.insertarMunicipio(id, nombre, id_departamento);
    }

    public LiveData<List<DepartamentoConMunicipio>> obtenerMunicipiosDepartamento(int id_Departamento){
        return municipioRepo.obtenerMunicipiosdeDepartamento(id_Departamento);
    }

    public void deleteMuni(int id){
        municipioRepo.eliminarMunicipio(id);
    }

    public LiveData<List<Municipio>> obtenerMunicipios(int id_Departamento){
        return municipioRepo.obtenerMunicipios(id_Departamento);
    }


    public LiveData<List<Municipio>> obtenerMunicipios(){
        return obtenerMunicipio;
    }

}

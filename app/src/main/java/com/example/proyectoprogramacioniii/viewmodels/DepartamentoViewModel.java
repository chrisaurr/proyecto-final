package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConDepartamento;
import com.example.proyectoprogramacioniii.repositories.DepartamentoRepo;

import java.util.List;

import javax.inject.Inject;

public class DepartamentoViewModel extends ViewModel {

    private DepartamentoRepo departamentoRepo;
    private LiveData<List<Departamento>> obtenerDepartamento;

    @Inject
    public DepartamentoViewModel(DepartamentoRepo departamentoRepo) {
        this.departamentoRepo = departamentoRepo;
        this.obtenerDepartamento = departamentoRepo.getAllDepartamentos();
    }

    public void agregarDepartamento(int id, String nombre, int id_pais){
        departamentoRepo.insertarDepartamento(id, nombre, id_pais);
    }

    public LiveData<List<PaisConDepartamento>> obtenerDepartamentosPais(int id_pais){
        return departamentoRepo.obtenerDepartamentosdePais(id_pais);
    }

    public void deleteDepartment(int id){
        departamentoRepo.eliminarDepartamento(id);
    }

    public LiveData<List<Departamento>> mostrarDeps(int id){
       return departamentoRepo.obtenerDepartamentos(id);
    }


    public LiveData<List<Departamento>> obtenerDepartamentos(){
        return obtenerDepartamento;
    }

}

package com.example.proyectoprogramacioniii.di;

import android.app.Application;

import androidx.room.Room;

import com.example.proyectoprogramacioniii.RoomDatabase.AppDb;
import com.example.proyectoprogramacioniii.repositories.CaracteristicasRepo;
import com.example.proyectoprogramacioniii.repositories.DepartamentoRepo;
import com.example.proyectoprogramacioniii.repositories.MunicipioRepo;
import com.example.proyectoprogramacioniii.repositories.PaisRepo;
import com.example.proyectoprogramacioniii.repositories.PrivilegioRepo;
import com.example.proyectoprogramacioniii.repositories.RolAccesoRepo;
import com.example.proyectoprogramacioniii.repositories.RolRepo;
import com.example.proyectoprogramacioniii.repositories.TiendaRepo;
import com.example.proyectoprogramacioniii.repositories.UsuarioRepo;
import com.example.proyectoprogramacioniii.repositories.impl.CaracteristicasRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.DepartamentoRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.MunicipioRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.PaisRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.PrivilegioRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.RolAccesoRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.RolRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.TiendaRepoImpl;
import com.example.proyectoprogramacioniii.repositories.impl.UsuarioRepoImpl;
import com.example.proyectoprogramacioniii.viewmodels.CaracteristicasViewModel;
import com.example.proyectoprogramacioniii.viewmodels.DepartamentoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MainActivityViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MunicipioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.PrivilegioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolAccesoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolViewModel;
import com.example.proyectoprogramacioniii.viewmodels.TiendaViewModel;
import com.example.proyectoprogramacioniii.viewmodels.UsuarioViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public abstract class AppModule {

    @Provides
    @Singleton
    public static AppDb provideDataBase(Application application){
        return Room.databaseBuilder(application, AppDb.class, "AppDb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    public static PaisRepo providePaisRepo(AppDb database){
        return new PaisRepoImpl(database.paisDao());
    }

    @Provides
    @Singleton
    public static DepartamentoRepo provideDepartamentoRepo(AppDb database){
        return new DepartamentoRepoImpl(database.departamentoDao());
    }

    @Provides
    @Singleton
    public static MunicipioRepo provideMunicipioRepo(AppDb database){
        return new MunicipioRepoImpl(database.municipioDao());
    }

    @Provides
    @Singleton
    public static RolRepo provideRolRepo(AppDb database){
        return new RolRepoImpl(database.rolDao());
    }

    @Provides
    @Singleton
    public static PrivilegioRepo providePrivilegioRepo(AppDb database){
        return new PrivilegioRepoImpl(database.privilegioDao());
    }

    @Provides
    @Singleton
    public static RolAccesoRepo provideRolAccesoRepo(AppDb database){
        return new RolAccesoRepoImpl(database.rolAccesoDao());
    }

    @Provides
    @Singleton
    public static UsuarioRepo provideUsuarioRepo(AppDb database){
        return new UsuarioRepoImpl(database.usuarioDao());
    }

    @Provides
    @Singleton
    public static TiendaRepo provideTiendaRepo(AppDb database){
        return new TiendaRepoImpl(database.tiendaDao());
    }

    @Provides
    @Singleton
    public static CaracteristicasRepo provideCaracteristicasRepo(AppDb database){
        return new CaracteristicasRepoImpl(database.caracteristicasDao());
    }

    @Provides
    @Singleton
    public static MainActivityViewModel provideMainActivityViewModel(PaisRepo paisRepo){
        return new MainActivityViewModel(paisRepo);
    }

    @Provides
    @Singleton
    public static DepartamentoViewModel provideDepartamentoViewModel(DepartamentoRepo departamentoRepo){
        return new DepartamentoViewModel(departamentoRepo);
    }

    @Provides
    @Singleton
    public static MunicipioViewModel provideMunicipioViewModel(MunicipioRepo municipioRepo){
        return new MunicipioViewModel(municipioRepo);
    }

    @Provides
    @Singleton
    public static RolViewModel provideRolViewModel(RolRepo rolRepo){
        return new RolViewModel(rolRepo);
    }

    @Provides
    @Singleton
    public static PrivilegioViewModel providePrivilegioViewModel(PrivilegioRepo privilegioRepo){
        return new PrivilegioViewModel(privilegioRepo);
    }

    @Provides
    @Singleton
    public static RolAccesoViewModel provideRolAccesoViewModel(RolAccesoRepo rolAccesoRepo){
        return new RolAccesoViewModel(rolAccesoRepo);
    }

    @Provides
    @Singleton
    public static UsuarioViewModel provideUsuarioViewModel(UsuarioRepo usuarioRepo){
        return new UsuarioViewModel(usuarioRepo);
    }

    @Provides
    @Singleton
    public static TiendaViewModel provideTiendaViewModel(TiendaRepo tiendaRepo){
        return new TiendaViewModel(tiendaRepo);
    }

    @Provides
    @Singleton
    public static CaracteristicasViewModel provideCaracteristicasViewModel(CaracteristicasRepo caracteristicasRepo){
        return new CaracteristicasViewModel(caracteristicasRepo);
    }

}

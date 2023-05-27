package com.example.proyectoprogramacioniii.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoprogramacioniii.R;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;
import com.example.proyectoprogramacioniii.utils.PojoProductos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavProductRvAdapter extends RecyclerView.Adapter<FavProductRvAdapter.MyHolder> {

    ArrayList<Caracteristica> data;
    private FavProductRvAdapter.ItemClickListener mItemListener;
    int id;

    public FavProductRvAdapter(ArrayList<Caracteristica> data, ItemClickListener mItemListener) {
        this.data = data;
        this.mItemListener = mItemListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorite_product, parent, false);
        return new FavProductRvAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Picasso.get()
                .load(data.get(position).imageUrl)
                .into(holder.imgProduct);

        holder.title.setText(data.get(position).marca);
        holder.description.setText(data.get(position).descripcion);


        String[] price = data.get(position).precio.split(" ");
        if(price.length == 2){
            holder.price.setText(price[1]);
        }else{
            holder.price.setText(data.get(position).precio);
        }

        holder.visitWeb.setOnClickListener(view ->{
            mItemListener.onItemClick(data.get(position).requestUrl, view, position);
        });

        holder.eliminar.setOnClickListener(view ->{
            mItemListener.onItemClick(data.get(position).requestUrl, view, position);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public interface ItemClickListener{
        void onItemClick(String link, View view, int pos);
    }


    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        TextView title, description, price;
        Button visitWeb, eliminar;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgFavorite);
            title = itemView.findViewById(R.id.marcaFavorite);
            description = itemView.findViewById(R.id.descripcionFavorite);
            price = itemView.findViewById(R.id.precioFavorite);
            visitWeb = itemView.findViewById(R.id.visitarBtnFavorite);
            eliminar = itemView.findViewById(R.id.eliminar);

        }
    }

}

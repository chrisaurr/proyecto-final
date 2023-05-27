package com.example.proyectoprogramacioniii.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoprogramacioniii.R;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;
import com.example.proyectoprogramacioniii.utils.PojoProductos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsRvAdapter extends RecyclerView.Adapter<ProductsRvAdapter.MyHolder>{

    ArrayList<PojoProductos> data;
    private ProductsRvAdapter.ItemClickListener mItemListener;
    int id;


    public ProductsRvAdapter(ArrayList<PojoProductos> data, ProductsRvAdapter.ItemClickListener itemClickListener){
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    public ProductsRvAdapter(ArrayList<PojoProductos> data, ProductsRvAdapter.ItemClickListener itemClickListener, int id){
        this.data = data;
        this.mItemListener = itemClickListener;
        this.id = id;
    }

    public ProductsRvAdapter(ArrayList<PojoProductos> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_product_item, parent, false);
        return new ProductsRvAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

       // if(position != 0){
            Picasso.get()
                    .load(data.get(position).url)
                    .into(holder.imgProduct);

        holder.title.setText(data.get(position).marca);
        holder.description.setText(data.get(position).descripcion);


        String[] price = data.get(position).precio.split(" ");
        if(price.length == 2){
            holder.price.setText(price[1]);
            //holder.checkStar.setChecked(true);
        }else{
            holder.price.setText(data.get(position).precio);
        }


        holder.checkStar.setOnClickListener(view ->{
            mItemListener.onItemClick(data.get(position).link, view, data.get(position).numTienda, data.get(position).url);
        });

        holder.visitWeb.setOnClickListener(view ->{
            mItemListener.onItemClick(data.get(position).link, view, data.get(position).numTienda, data.get(position).url);
        });

        int valor = position;

        serviciosFavorito servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosFavorito.class);
        Call<List<FavoritosClass>> call = servicios.checkProduct(id);

        call.enqueue(new Callback<List<FavoritosClass>>() {
            @Override
            public void onResponse(Call<List<FavoritosClass>> call, Response<List<FavoritosClass>> response) {
                List<FavoritosClass> lista = response.body();

                for(int i = 0; i < lista.size(); i++){
                    if(lista.get(i).requestUrl.equals(data.get(valor).link)){
                        holder.checkStar.setChecked(true);
                        System.out.println(lista.get(i).requestUrl + " = " + data.get(valor).link);
                        break;
                    }else {
                        holder.checkStar.setChecked(false);
                        System.out.println("No son iguales");
                        System.out.println(lista.get(i).requestUrl + " != " + data.get(valor).link);
                    }

                }

            }

            @Override
            public void onFailure(Call<List<FavoritosClass>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    @Override
    public int getItemCount() {
       return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(String link, View view, int tienda, String imageUrl);
    }


    class MyHolder extends RecyclerView.ViewHolder{
        ImageView imgProduct;
        CheckBox checkStar;
        TextView title, description, price;
        Button visitWeb;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img);
            checkStar = itemView.findViewById(R.id.star);
            title = itemView.findViewById(R.id.marca);
            description = itemView.findViewById(R.id.descripcion);
            price = itemView.findViewById(R.id.precio);
            visitWeb = itemView.findViewById(R.id.visitarBtn);

        }
    }

}

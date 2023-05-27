package com.example.proyectoprogramacioniii.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoprogramacioniii.ActivityCategorias;
import com.example.proyectoprogramacioniii.R;

import java.util.ArrayList;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyHolder>{

    ArrayList<String> data;
    private ItemClickListener mItemListener;


    public MyRvAdapter(ArrayList<String> data, ItemClickListener itemClickListener){
        this.data = data;
        this.mItemListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.tvTitle.setText(data.get(position));
        holder.itemView.setOnClickListener(view ->{
            mItemListener.onItemClick(data.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener{
        void onItemClick(String category, int i);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}

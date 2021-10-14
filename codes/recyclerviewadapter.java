package com.example.phoneauth;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {

    int[]arr;
    Context context;
    public RecyclerViewAdapter(int[] arr) {
        this.arr = arr;
    }

    @NonNull
    @Override
    public MyViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder  holder, final int position) {
        holder.imageview.setImageResource(arr[position]);
        holder.textView.setText("");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,MainActivity.class);
                context.startActivity(intent);
            }
        });





    }



    @Override
    public int getItemCount() {
        return arr.length;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageview,imageView1;
        TextView textView;
        LinearLayout linearLayout;
        Context context;
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           imageview = itemView.findViewById(R.id.imageView);
           textView = itemView.findViewById(R.id.textView);
           linearLayout=itemView.findViewById(R.id.linearlayout);


       }
    }
}

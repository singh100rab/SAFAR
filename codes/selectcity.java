package com.example.phoneauth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SelectCity extends AppCompatActivity {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    int []arr={R.drawable.andamanandnicobar,R.drawable.andhrapradesh,R.drawable.arunachalpradesh,R.drawable.assam,R.drawable.bihar,R.drawable.chandigarh,R.drawable.chattisgarh,R.drawable.dadraandanagarhaveli};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        layoutManager=new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter=new RecyclerViewAdapter(arr);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);







        }



    ;



    }




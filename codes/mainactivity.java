package com.example.phoneauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    TextView pname,pphone,pemail;
    Button button;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pname=findViewById(R.id.profileFullName);
        pphone=findViewById(R.id.profilePhone);
        pemail=findViewById(R.id.profileEmail);
        fauth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();




        DocumentReference docRef=fstore.collection("users").document(fauth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    String fullName=documentSnapshot.getString("editTextTextPersonName")+" "+documentSnapshot.getString("editTextTextPersonName2");
                    pname.setText(fullName);
                    pemail.setText(documentSnapshot.getString("editTextTextEmailAddress"));
                    pphone.setText(fauth.getCurrentUser().getPhoneNumber());
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Register.class));
            finish();
        }
        if(item.getItemId()==R.id.db){
            Intent intent=new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.db1){
            Intent intent=new Intent(MainActivity.this,SelectCity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

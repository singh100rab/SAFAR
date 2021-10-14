package com.example.phoneauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class Details extends AppCompatActivity {
    EditText firstname,lastname,email;
    Button button;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore fStore;
    String Userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        firstname=findViewById(R.id.editTextTextPersonName);
        lastname=findViewById(R.id.editTextTextPersonName2);
        email=findViewById(R.id.editTextTextEmailAddress);
        button=findViewById(R.id.button);
        firebaseAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        Userid=firebaseAuth.getCurrentUser().getUid();
        final DocumentReference docRef=fStore.collection("users").document(Userid);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!firstname.getText().toString().isEmpty() &&!lastname.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()){
                 String first=firstname.getText().toString();
                 String last=lastname.getText().toString();
                 String userEmail=email.getText().toString();
                    Map<String,Object> user=new HashMap<>();
                    user.put("editTextTextPersonName",first);
                    user.put("editTextTextPersonName2",last);
                    user.put("editTextTextEmailAddress",userEmail);

                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(Details.this,"data is not inserted",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    });


                }
                else{
                    Toast.makeText(Details.this,"all details are required",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }
}

package com.example.phoneauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Register extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fauth;
    EditText phonenumber,codeenter;
    FirebaseFirestore fStore;
    Button nextbtn;
    ProgressBar progressBar;
    TextView state;
    String VerificationId;
    CountryCodePicker codePicker;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fauth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        phonenumber=findViewById(R.id.phone);
        codeenter=findViewById(R.id.codeEnter);
        progressBar=findViewById(R.id.progressBar);
        nextbtn=findViewById(R.id.nextBtn);
        state=findViewById(R.id.state);
        codePicker=findViewById(R.id.ccp);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!verificationInProgress){
                   if(!phonenumber.getText().toString().isEmpty() && phonenumber.getText().toString().length()==10){
                       String phoneNum="+"+codePicker.getSelectedCountryCode()+phonenumber.getText().toString();
                       Log.d(TAG,"onClick: Phone NO -> "+phoneNum);
                       progressBar.setVisibility(View.VISIBLE);
                       state.setText("sending otp");
                       state.setVisibility(View.VISIBLE);
                       requestOTP(phoneNum);


                   }
                   else {
                       phonenumber.setError("Phone Number Is not Valid");
                   }
               }
               else{
                   String userOTP=codeenter.getText().toString();
                   if(!userOTP.isEmpty() && userOTP.length()==6){
                       PhoneAuthCredential credential=PhoneAuthProvider.getCredential(VerificationId,userOTP);
                       verifyAuth(credential);

                   }
                   else{
                       codeenter.setError("Valid Otp is required");
                   }

               }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(fauth.getCurrentUser()!=null){
            progressBar.setVisibility(View.VISIBLE);
            state.setText("CHECKING...");
            state.setVisibility(View.VISIBLE);
            checkUserProfile();
        }
    }



    private void verifyAuth(PhoneAuthCredential credential) {
        fauth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    checkUserProfile();

                }else{
                    Toast.makeText(Register.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void checkUserProfile() {
        DocumentReference docRef=fStore.collection("users").document(fauth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }
                else{
                    startActivity(new Intent(getApplicationContext(),Details.class));
                    finish();
                }
            }
        });
    }

    private void requestOTP(String phoneNum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60l, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                codeenter.setVisibility(View.VISIBLE);
                VerificationId=s;
                token=forceResendingToken;
                nextbtn.setText("Verify");
                nextbtn.setEnabled(true);
                verificationInProgress=true;
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Register.this,"Re request the otp",Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Register.this,"cannot create account"+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}

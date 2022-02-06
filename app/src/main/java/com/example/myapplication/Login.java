package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

 public class Login extends AppCompatActivity {

    public Button btnlogin;
    public TextView txsignup;
    public EditText email;
    public EditText password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnlogin = (Button) findViewById(R.id.login);
        txsignup = (TextView) findViewById(R.id.sgin_up_txt);

        email = findViewById(R.id.email_address);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();



        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString()))
                {
                    email.setError("Email  is required");
                }else if (TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Password is required");
                }else {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(Login.this,"logged in sucessfully",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this,MainActivity.class));


                            }else {
                                Toast.makeText(Login.this,task.getException().getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }
                        }


                    });
                }



            }
        });






        txsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

    }

}
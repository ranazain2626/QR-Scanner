package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.VERSION_CODES.S;

public class Signup extends AppCompatActivity {

    public Button btn_sginup;
    public TextView tx_login;
    public EditText username;
    public EditText email;
    public EditText password;
    public EditText confirmpassword;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USER= "user";
    private static final String TAG="SignUpActivity";
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btn_sginup = (Button) findViewById(R.id.sgin_up);
        tx_login = (TextView) findViewById(R.id.login_txt);

        username=findViewById(R.id.user_name);
        email=findViewById(R.id.email_address);

        password=findViewById(R.id.password_txt);
        confirmpassword=findViewById(R.id.confirm_password);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USER);


        btn_sginup.setOnClickListener(new View.OnClickListener() {
            @Override




            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString()))
                {
                    username.setError("UserName is required");
                }else if(TextUtils.isEmpty(email.getText().toString()))
                {

                    email.setError("Username is required");
                }else if (TextUtils.isEmpty(password.getText().toString()))
                {
                    password.setError("Password is required");
                }else if (TextUtils.isEmpty(confirmpassword.getText().toString()))
                {
                    confirmpassword.setError("Confirm Password is required");
                }else if(!(confirmpassword.getText().toString().equals(password.getText().toString()))) {
                    confirmpassword.setError(" Password is not correct");
                } else if(confirmpassword.getText().toString().length()<6){
                    confirmpassword.setError("Password should be more than 6 characters");
                }
                String username_st =username.getText().toString();
                String email_st=email.getText().toString();
                String password_st=password.getText().toString();
                String confirmPassword_st=confirmpassword.getText().toString();
                user = new User(email_st,password_st,username_st);
                registerUser(email_st,password_st,username_st);

            }

        });

        tx_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });


    }

    public void registerUser(String email,String password,String username){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });

    }

    public  void  updateUI(FirebaseUser currentUser){
//        String keyId=mDatabase.push().getKey();
        mDatabase.child(mAuth.getCurrentUser().getUid()).setValue(user);
        Intent loginIntent=new Intent(Signup.this,Login.class);
        startActivity(loginIntent);

    }
}
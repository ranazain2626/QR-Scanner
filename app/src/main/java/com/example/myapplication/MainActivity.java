package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.HashMap;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {
     public static EditText ed;
    public static TextView edt;
    Button createBtn,scanBtn;
    ImageView qrImage;
    QRGEncoder qrgEncoder;
    DatabaseReference dbref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed=findViewById(R.id.editTextNumber);
        edt=findViewById(R.id.rs);
        createBtn=findViewById(R.id.button);
        scanBtn=findViewById(R.id.button2);
        qrImage=findViewById(R.id.imageView6);
        dbref = FirebaseDatabase.getInstance().getReference().child("generate bill");

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data =ed.getText().toString();

                dbref.push().setValue(data);
                Toast.makeText(MainActivity.this, "Data Update.", Toast.LENGTH_SHORT).show();

                MultiFormatWriter writer =new MultiFormatWriter();


                if (data.isEmpty()){
                    ed.setError("Value Required");
                }else {

                    qrgEncoder = new QRGEncoder(data,null,QRGContents.Type.TEXT,500);
                    try {
                        BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE,350,350);

                        BarcodeEncoder encoder =new BarcodeEncoder();
                        Bitmap bitmap= encoder.createBitmap(matrix);
                        // the bitmap is set inside our image
                        // view using .setimagebitmap method.
                        qrImage.setImageBitmap(bitmap);
                        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(ed.getApplicationWindowToken(),0);
                    }catch (WriterException e) {
                        e.printStackTrace();

                    }


                }


            }



        });
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Scan.class));
            }
        });


    }

    public void handleResult(Result rawResult) {
        MainActivity.ed.setText(rawResult.getText());
        String data = rawResult.getText().toString();
        dbref.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Data Update.", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });


    }

}


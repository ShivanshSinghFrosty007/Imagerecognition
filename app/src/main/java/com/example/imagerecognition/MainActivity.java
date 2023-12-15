package com.example.imagerecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ImageLabelBtn;
    Button TextRecog;
    Button barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageLabelBtn = findViewById(R.id.Image_Label_btn);
        TextRecog = findViewById(R.id.text_recog_btn);
        barcodeReader = findViewById(R.id.barcode_btn);

        ImageLabelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ImageLabelActivity.class);
                startActivity(intent);
            }
        });

        TextRecog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TextRecognition.class);
                startActivity(intent);
            }
        });

        barcodeReader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FaceDetection.class);
                startActivity(intent);
            }
        });
    }
}
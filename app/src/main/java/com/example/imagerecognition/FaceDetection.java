package com.example.imagerecognition;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;

import java.util.List;

public class FaceDetection extends AppCompatActivity {

    public static final int CAMERA_REQUEST = 1000;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_detection);

        imageView = findViewById(R.id.image);
    }

    public void startCameraActivity(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            FirebaseFaceRecognization(bitmap);
        }
    }

    public void FirebaseFaceRecognization(Bitmap bitmap) {
        FirebaseVisionFaceDetectorOptions highAccuracyOpts =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .setContourMode(FirebaseVisionFaceDetectorOptions.ALL_CONTOURS)
                        .build();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(highAccuracyOpts);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {

                                        // smile progress
                                        LinearProgressIndicator smileProgressBar = findViewById(R.id.smile_prog);
                                        findViewById(R.id.smile_txt).setVisibility(View.VISIBLE);
                                        smileProgressBar.setProgress((int) (faces.get(0).getSmilingProbability() * 100));
                                        smileProgressBar.setVisibility(View.VISIBLE);

                                        //left eye
                                        LinearProgressIndicator leftEyeProgressBar = findViewById(R.id.left_eye_prog);
                                        findViewById(R.id.left_eye_text).setVisibility(View.VISIBLE);
                                        leftEyeProgressBar.setProgress((int) (faces.get(0).getLeftEyeOpenProbability() * 100));
                                        leftEyeProgressBar.setVisibility(View.VISIBLE);

                                        //right eye
                                        LinearProgressIndicator rightEyeProgressBar = findViewById(R.id.right_eye_prog);
                                        findViewById(R.id.right_eye_text).setVisibility(View.VISIBLE);
                                        rightEyeProgressBar.setProgress((int) (faces.get(0).getRightEyeOpenProbability() * 100));
                                        rightEyeProgressBar.setVisibility(View.VISIBLE);

                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
    }

}
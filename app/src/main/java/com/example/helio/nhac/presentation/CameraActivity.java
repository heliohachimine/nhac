package com.example.helio.nhac.presentation;

import android.Manifest;
import android.app.Application;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.example.helio.nhac.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Objects;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;

public class CameraActivity extends AppCompatActivity {

    private Fotoapparat fotoapparat;
    private CameraView cameraView;
    private LottieAnimationView sparkles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_camera);
        cameraView = findViewById(R.id.cameraView);
        sparkles = findViewById(R.id.anim_sparkles);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.yellow_300));
        verifyPermission();
        findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //takePicture();
                sparkles.playAnimation();
                Toast.makeText(getBaseContext(), "Foto salva", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void verifyPermission() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                fotoapparat = new Fotoapparat(getBaseContext(), cameraView);
                fotoapparat.start();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(getBaseContext(), "Sem essa permissão não é possível continuar.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
        }).check();
    }

    private void takePicture() {
        PhotoResult photo = fotoapparat.takePicture();
        photo.toBitmap().whenDone(new WhenDoneListener<BitmapPhoto>() {
            @Override
            public void whenDone(BitmapPhoto bitmap) {
                CameraActivity.this.doImageRecognition(bitmap.bitmap);
            }
        });

    }

    private void doImageRecognition(Bitmap bitmapPhoto) {
        FirebaseApp.initializeApp(this);
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmapPhoto);
        FirebaseVisionImageLabeler detector = FirebaseVision.getInstance().getOnDeviceImageLabeler();
        detector.processImage(firebaseVisionImage)
                .addOnSuccessListener(
                        new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                            @Override

                            public void onSuccess(List<FirebaseVisionImageLabel> it) {
                                List<FirebaseVisionImageLabel> labels = it;
                                Log.d("LIST", labels.toString());
                            }
                        }

        ).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}

package com.example.helio.nhac.presentation.cameraActivity;

import android.Manifest;
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
import com.example.helio.nhac.data.dao.FruitDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import io.fotoapparat.Fotoapparat;
import io.fotoapparat.result.BitmapPhoto;
import io.fotoapparat.result.PhotoResult;
import io.fotoapparat.result.WhenDoneListener;
import io.fotoapparat.view.CameraView;

public class CameraActivity extends AppCompatActivity {

    private Fotoapparat fotoapparat;
    private CameraView cameraView;
    private LottieAnimationView sparkles;
    private FirebaseAutoMLRemoteModel remoteModel;
    private Boolean modelIsDownloaded = false;
    private FruitDao dao;
    private RecognizeToast toast;
    public String textToast;
    public int imageToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = new RecognizeToast(this);
        toast.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        FirebaseApp.initializeApp(this);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .build();
        remoteModel = new FirebaseAutoMLRemoteModel.Builder("vegetais_20191013174649").build();

        FirebaseModelManager.getInstance().download(remoteModel, conditions)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        modelIsDownloaded = true;
                    }
                });
        dao = new FruitDao(this);
        setContentView(R.layout.activity_camera);
        cameraView = findViewById(R.id.cameraView);
        sparkles = findViewById(R.id.anim_sparkles);
        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.yellow_300));
        verifyPermission();
        findViewById(R.id.camera_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
                sparkles.setVisibility(View.VISIBLE);
                sparkles.playAnimation();
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
                try {
                    doImageRecognition(bitmap.bitmap);
                } catch (FirebaseMLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void doImageRecognition(Bitmap bitmapPhoto) throws FirebaseMLException {
        if (modelIsDownloaded) {
            FirebaseApp.initializeApp(this);
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmapPhoto);
            FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions =
                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(remoteModel).build();
            FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(labelerOptions).processImage(firebaseVisionImage)
                    .addOnSuccessListener(
                            new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                                @Override
                                public void onSuccess(List<FirebaseVisionImageLabel> it) {
                                    cancelSparkles();
                                    if (it.size() == 0) {
                                        setErrorCustomToast(getString(R.string.error_recognize));
                                        toast.show();
                                    } else {
                                        for (FirebaseVisionImageLabel label : it) {
                                            Log.d("LIST", label.getText());
                                            if (label.getConfidence() > 0.8 && dao.activate(label.getText())) {
                                                //TODO fazer um enum para os produtos
                                                switch (label.getText()) {
                                                    case "broccoli":
                                                        setCustomToast(R.drawable.brocolis, getString(R.string.broccoli));
                                                        toast.show();
                                                        break;
                                                    case "carrot":
                                                        setCustomToast(R.drawable.cenoura, getString(R.string.carrot));
                                                        toast.show();
                                                        break;
                                                    case "tomato":
                                                        setCustomToast(R.drawable.tomate, getString(R.string.tomato));
                                                        toast.show();
                                                        break;
                                                    default:
                                                        textToast = "Sem detalhes";
                                                        imageToast = R.drawable.abacate;
                                                        toast.show();
                                                        break;
                                                }


                                            }
                                        }

                                    }

                                }
                            }

                    ).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("LIST", e.getMessage());
                    cancelSparkles();
                    setErrorCustomToast(getString(R.string.error_mlkit));
                    toast.show();
                }
            });
        } else {
            cancelSparkles();
            setErrorCustomToast(getString(R.string.error_internet));
            toast.show();
        }
    }

    private void cancelSparkles() {
        sparkles.cancelAnimation();
        sparkles.setVisibility(View.GONE);
    }

    private void setCustomToast(int image, String name) {
        this.imageToast = image;
        this.textToast = name;
    }

    private void setErrorCustomToast(String error) {
        setCustomToast(R.drawable.abacaxi, error);
    }

}

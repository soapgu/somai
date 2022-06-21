package com.example.elcapi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private PreviewView mPreviewView;
    private ProcessCameraProvider mCameraProvider;
    private ListenableFuture<ProcessCameraProvider> mCameraFuture;
    private CameraSelector mCameraSelector;
    private Preview mPreview;
    private Button photoButton;
    ImageCapture imageCapture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPreviewView = findViewById(R.id.preview_video_recode);
        photoButton = findViewById(R.id.button_photo);
        photoButton.setOnClickListener( v->{
            String dirPath = this.getExternalFilesDir("").getAbsolutePath();
            File dirFile = new File(dirPath);
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            ImageCapture.OutputFileOptions outputFileOptions =
                    new ImageCapture.OutputFileOptions.Builder(file).build();
            imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    Uri uri = outputFileResults.getSavedUri();
                    Logger.i( "Save photo:%s",uri );
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Logger.e( exception, "save photo error" );
                }
            });

        } );
        //AlienJJ156.AlienJJ_GreedLED();
        //AlienJJ156.AlienJJ_YellowLED();
        checkPermissions();
    }

    private void checkPermissions() {
        ActivityResultLauncher<String[]> mRequestPermission = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    if (result.values().stream().allMatch(p -> p)) {
                        init();
                    } else {
                        Toast toast = Toast.makeText(this,"有权限被拒绝，无法使用本功能。请在设置中打开所需权限。" , Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        ) {
            init();
        } else {
            mRequestPermission.launch(new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO,
            });
        }
    }

    private void init() {
        //initVideoView();
        //initEvent();
        initCamera();
        startPreview();
    }

    @SuppressLint("RestrictedApi")
    private void initCamera() {
        mCameraFuture = ProcessCameraProvider.getInstance(this);
        mCameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA;
        mPreview = new Preview.Builder()
                .build();
        mPreview.setSurfaceProvider(mPreviewView.getSurfaceProvider());

        /*
        mVideoCapture = new VideoCapture.Builder()
                .build();
         */
        imageCapture =
                new ImageCapture.Builder()
                        //.setTargetRotation( this.getDisplay().getRotation() )
                        .build();

        try {
            mCameraProvider = mCameraFuture.get();
        } catch (ExecutionException | InterruptedException e) {
            Logger.e("初始化摄像头失败，%s", e.toString());
        }
    }

    private void startPreview() {
        mCameraFuture.addListener(() -> {
            try {
                mCameraProvider.unbindAll();
                //mCameraProvider.bindToLifecycle(this.getLifecycle(), mCameraSelector, mPreview, mVideoCapture);
                mCameraProvider.bindToLifecycle((LifecycleOwner) this,mCameraSelector,mPreview,imageCapture );
            } catch (Exception e) {
                Logger.e("预览摄像头失败，%s", e.toString());
            }
        }, ContextCompat.getMainExecutor(this) );
    }
}
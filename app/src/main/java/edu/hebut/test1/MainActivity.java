// c:/Users/尹先生/Desktop/Test2/Test2/app/src/main/java/edu/hebut/test1/MainActivity.java
package edu.hebut.test1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.hebut.test1.network.ApiClient;

public class MainActivity extends AppCompatActivity {

    private Button btnCaptureImage;
    private Button btnAnalyzeVideo;
    private Button btnViewReports;
    private TextView tvStatus;

    private File currentPhotoFile;
    
    // 1. 注册相机启动器
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    if (currentPhotoFile != null && currentPhotoFile.exists()) {
                        tvStatus.setText("照片已拍摄，正在上传识别...");
                        // 2. 调用后端接口
                        ApiClient.uploadImage(currentPhotoFile, new ApiClient.ApiCallback() {
                            @Override
                            public void onSuccess(String response) {
                                runOnUiThread(() -> {
                                    tvStatus.setText("识别结果: " + response);
                                    Toast.makeText(MainActivity.this, "识别成功", Toast.LENGTH_SHORT).show();
                                });
                            }

                            @Override
                            public void onError(String error) {
                                runOnUiThread(() -> {
                                    tvStatus.setText("识别失败: " + error);
                                    Toast.makeText(MainActivity.this, "识别失败", Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    } else {
                        tvStatus.setText("拍照取消或失败");
                    }
                } else {
                    tvStatus.setText("拍照取消");
                }
            }
    );

    // 3. 注册权限请求启动器
    private final ActivityResultLauncher<String[]> permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            permissions -> {
                Boolean cameraGranted = permissions.getOrDefault(Manifest.permission.CAMERA, false);
                // Android 13以下需要存储权限，Android 13+ 需要 READ_MEDIA_IMAGES (如果在 Manifest 中声明了)
                // 这里简化处理，主要检查相机
                if (cameraGranted) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "需要相机权限才能拍照", Toast.LENGTH_SHORT).show();
                    tvStatus.setText("权限被拒绝");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCaptureImage = findViewById(R.id.btn_capture_image);
        btnAnalyzeVideo = findViewById(R.id.btn_analyze_video);
        btnViewReports = findViewById(R.id.btn_view_reports);
        tvStatus = findViewById(R.id.tv_status);

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndTakePhoto();
            }
        });

        btnAnalyzeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                analyzeVideo();
            }
        });

        btnViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewReports();
            }
        });
        
        // 测试连接可以保留或移除
        // testBackendConnection(); 
    }

    private void checkPermissionAndTakePhoto() {
        // 检查相机权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            permissionLauncher.launch(new String[]{Manifest.permission.CAMERA});
        } else {
            // 已有权限，直接拍照
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 确保有相机应用可以处理这个 Intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // 创建文件来保存照片
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "创建文件失败: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (photoFile != null) {
                // 获取 FileProvider 的 URI
                Uri photoURI = FileProvider.getUriForFile(this,
                        getApplicationContext().getPackageName() + ".fileprovider",
                        photoFile);
                
                // 将 URI 传递给相机应用
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                
                // 启动相机
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // 创建文件名: JPEG_年月日_时分秒_
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        
        // 获取外部私有目录 (不需要额外存储权限即可写入)
        File storageDir = getExternalFilesDir(null);
        
        // 创建临时文件
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // 保存当前文件路径，以便在 onActivityResult 中使用
        currentPhotoFile = image;
        return image;
    }

    private void analyzeVideo() {
        tvStatus.setText("视频分析功能开发中...");
    }

    private void viewReports() {
        tvStatus.setText("查看报告功能开发中...");
    }
}
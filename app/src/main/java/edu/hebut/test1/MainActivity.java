package edu.hebut.test1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.hebut.test1.network.ApiClient;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button btnCaptureImage;
    private Button btnAnalyzeVideo;
    private Button btnViewReports;
    private TextView tvStatus;

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
                captureImage();
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

        // 测试与后端的连接
        testBackendConnection();
    }

    private void captureImage() {
        tvStatus.setText("正在拍照...");
        // 实现拍照功能
    }

    private void analyzeVideo() {
        tvStatus.setText("正在分析视频...");
        // 实现视频分析功能
    }

    private void viewReports() {
        tvStatus.setText("查看心情报告...");
        // 实现查看报告功能
    }

    private void testBackendConnection() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Hello from Android");
            String jsonData = jsonObject.toString();

            ApiClient.sendPostRequest("/test", jsonData, new ApiClient.ApiCallback() {
                @Override
                public void onSuccess(String response) {
                    tvStatus.setText("后端连接正常: " + response);
                }

                @Override
                public void onError(String error) {
                    tvStatus.setText("后端连接失败: " + error);
                }
            });
        } catch (Exception e) {
            tvStatus.setText("连接测试失败: " + e.getMessage());
        }
    }
}
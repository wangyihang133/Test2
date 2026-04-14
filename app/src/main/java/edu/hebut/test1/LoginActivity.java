package edu.hebut.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.hebut.test1.db.UserDao;
import edu.hebut.test1.network.ApiClient;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnRegister, btnTestConnection;
    private TextView tvMessage;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        btnTestConnection = findViewById(R.id.btn_test_connection);
        tvMessage = findViewById(R.id.tv_message);

        userDao = new UserDao(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = userDao.login(username, password);
                if (success) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 跳转到主界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean success = userDao.register(username, password);
                if (success) {
                    Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "注册失败，用户名已存在", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConnection();
            }
        });
    }

    private void testConnection() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "Hello from Android");
            String jsonData = jsonObject.toString();

            ApiClient.sendPostRequest("/test", jsonData, new ApiClient.ApiCallback() {
                @Override
                public void onSuccess(String response) {
                    tvMessage.setText("通信测试成功: " + response);
                }

                @Override
                public void onError(String error) {
                    tvMessage.setText("通信测试失败: " + error);
                }
            });
        } catch (Exception e) {
            tvMessage.setText("测试失败: " + e.getMessage());
        }
    }
}
package edu.hebut.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.hebut.test1.db.UserDao;
import edu.hebut.test1.network.ApiClient;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin, btnTestConnection;
    private TextView tvMessage, tvRegister, tvForgotPassword;
    private ImageView ivEye;
    private UserDao userDao;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnTestConnection = findViewById(R.id.btn_test_connection);
        tvMessage = findViewById(R.id.tv_message);
        tvRegister = findViewById(R.id.tv_register);
        tvForgotPassword = findViewById(R.id.tv_forgot_password);
        ivEye = findViewById(R.id.iv_eye);

        userDao = new UserDao(this);

        // 密码可见性切换
        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility();
            }
        });

        // 登录按钮点击事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 注册链接点击事件
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到注册界面
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // 忘记密码点击事件
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "忘记密码功能开发中", Toast.LENGTH_SHORT).show();
            }
        });

        // 测试通信按钮点击事件
        btnTestConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testConnection();
            }
        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // 隐藏密码
            etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ivEye.setImageResource(R.drawable.ic_eye);
        } else {
            // 显示密码
            etPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            ivEye.setImageResource(R.drawable.ic_eye_off);
        }
        isPasswordVisible = !isPasswordVisible;
        // 光标定位到输入框末尾
        etPassword.setSelection(etPassword.getText().length());
    }

    private void login() {
        String email = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // 验证输入
        if (email.isEmpty()) {
            Toast.makeText(this, "请输入邮箱地址", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "请输入有效的邮箱地址", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 登录验证
        boolean success = userDao.login(email, password);
        if (success) {
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            // 跳转到主界面
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "邮箱或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                    tvMessage.setVisibility(View.VISIBLE);
                }

                @Override
                public void onError(String error) {
                    tvMessage.setText("通信测试失败: " + error);
                    tvMessage.setVisibility(View.VISIBLE);
                }
            });
        } catch (Exception e) {
            tvMessage.setText("测试失败: " + e.getMessage());
            tvMessage.setVisibility(View.VISIBLE);
        }
    }
}
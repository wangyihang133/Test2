package edu.hebut.test1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ImageView ivCat, ivDog;
    private TextView tvWelcome, tvPetName;
    private ImageView ivPetIcon;
    private boolean isCat = true; // 默认显示猫

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            // 初始化视图
            ivCat = view.findViewById(R.id.iv_cat);
            ivDog = view.findViewById(R.id.iv_dog);
            tvWelcome = view.findViewById(R.id.tv_welcome);
            tvPetName = view.findViewById(R.id.tv_pet_name);
            ivPetIcon = view.findViewById(R.id.iv_pet_icon);

            // 设置默认状态
            updatePetView();

            // 猫/狗切换按钮点击事件
            ivCat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isCat) {
                        isCat = true;
                        updatePetView();
                    }
                }
            });

            ivDog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCat) {
                        isCat = false;
                        updatePetView();
                    }
                }
            });

            // 心情识别按钮点击事件
            view.findViewById(R.id.btn_mood_recognition).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "开始心情识别", Toast.LENGTH_SHORT).show();
                    // 跳转到拍照Activity
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    startActivity(intent);
                }
            });

            // 宠物日报按钮点击事件
            view.findViewById(R.id.btn_pet_report).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转到日报页面
                    ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.nav_report);
                }
            });

            return view;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updatePetView() {
        if (isCat) {
            // 切换到猫模式
            ivCat.setAlpha(1.0f);
            ivDog.setAlpha(0.5f);
            ivPetIcon.setImageResource(R.drawable.ic_cat);
            tvPetName.setText("小猫咪");
        } else {
            // 切换到狗模式
            ivCat.setAlpha(0.5f);
            ivDog.setAlpha(1.0f);
            ivPetIcon.setImageResource(R.drawable.ic_dog);
            tvPetName.setText("小狗狗");
        }
    }
}
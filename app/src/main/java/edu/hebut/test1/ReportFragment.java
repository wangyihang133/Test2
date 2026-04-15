package edu.hebut.test1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class ReportFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        // 查看详情按钮点击事件
        view.findViewById(R.id.btn_view_details).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "查看详细报告", Toast.LENGTH_SHORT).show();
            }
        });

        // 模拟数据显示
        TextView tvDate = view.findViewById(R.id.tv_date);
        tvDate.setText("今天·周三");

        return view;
    }
}
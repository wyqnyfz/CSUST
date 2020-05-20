package com.coolweather.csust;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MyClassTime extends AppCompatActivity {

    List<ItemClass> mList;
    private RecyclerView mRecyclerView;
    private ClassAdapter mClassAdapter;
    private String TAG = "MenuNum2";
    private TextView mTv_name;
    private TextView mTv_class;
    private TextView mTv_week;
    private TextView mTv_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class_time);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();
        initData();
    }
    private void init() {
        mTv_name = findViewById(R.id.tv_m2_name);
        mTv_class = findViewById(R.id.tv_m2_class);
        mTv_week = findViewById(R.id.tv_m2_week);
        mTv_exit = findViewById(R.id.tv_m2_exit);
        mRecyclerView = findViewById(R.id.rv_m2);

        LinearLayout ll = findViewById(R.id.ll_all);
        ll.getBackground().setAlpha(100);


        mList = new ArrayList<>();
        ArrayList<String> list = MyData.myClass;
        for(int i=0;i< list.size();i++){
            if(i%8!=0){
                String s = list.get(i);
                if("0".equals(s)){
                    mList.add(new ItemClass("",""));
                }else {
                    String[] strings = s.split(",");
                    mList.add(new ItemClass(strings[0],strings[1]));
                }
            }
        }
        mTv_name.setText(MyData.myMassage.get("学生姓名"));
        mTv_class.setText(MyData.myMassage.get("班级名称"));
        mTv_week.setText(MyData.zhou);
        mTv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("b", 0);
                editor.apply();
                startActivity(new Intent(MyClassTime.this,MainActivity.class));
                finish();
            }
        });



    }

    private void initData() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(7, StaggeredGridLayoutManager.VERTICAL));
        mClassAdapter = new ClassAdapter(mList);
        mRecyclerView.setAdapter(mClassAdapter);
    }
}

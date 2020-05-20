package com.coolweather.csust;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    private EditText mName;
    private EditText mPassword;
    private Button mLogin;
    private TextView mRegister;
    private TextView mForget;
    private boolean mLogin_b = true;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){

                case 2:
                    int t2 = (int) msg.obj;
                    if(t2==1){
                        //data是数据表的名字
                        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name",MyData.userAccount);
                        editor.putString("password",MyData.userPassword);
                        editor.putInt("b", 1);
                        editor.apply();
                        startActivity(new Intent(MainActivity.this,MyClassTime.class));
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this, "密码或账号不正确", Toast.LENGTH_SHORT).show();
                        mLogin_b = true;
                        mLogin.setText("登录");
                    }
                    break;
                    case 3:
                        int t1 = (int) msg.obj;
                        if(t1==1){
                            Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            mLogin_b = true;
                            mLogin.setText("登录");
                        }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();
        //data是数据表的名字
        SharedPreferences sharedPreferences= getSharedPreferences("data", Context.MODE_PRIVATE);
        int b = sharedPreferences.getInt("b", 0);		//如果没有返回0
        if(b==1){
            String s1 = sharedPreferences.getString("name", "");
            String s2 = sharedPreferences.getString("password", "");
            mName.setText(s1);
            mPassword.setText(s2);
            MyData.userAccount = s1;
            MyData.userPassword = s2;
            loging();
        }
    }

    private void init() {
        mName = findViewById(R.id.et_login_name);
        mPassword = findViewById(R.id.et_login_password);
        mLogin = findViewById(R.id.bt_login_login);
        mRegister = findViewById(R.id.tv_login_register);
        mForget = findViewById(R.id.tv_login_forget);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = mName.getText().toString();
                String s2 = mPassword.getText().toString();
                if("".equals(s1) || "".equals(s2)){
                    Toast.makeText(MainActivity.this, "账户或密码不能为空!", Toast.LENGTH_SHORT).show();
                }else{
                    MyData.userAccount = s1;
                    MyData.userPassword = s2;
                    loging();
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "注册？ 你想啥呢？", Toast.LENGTH_SHORT).show();
            }
        });
        mForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "傻了吧！我也不知道 hhh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登录
     */
    private void loging() {

        if(mLogin_b){
            mLogin_b=false;
            mLogin.setText("正在登录..");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyUtils.get_1(mHandler);
                }
            }).start();
        }


    }

}

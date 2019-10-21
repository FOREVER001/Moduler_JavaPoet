package com.tianzhuan.net_modular_javapoet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tianzhuan.annotation.ARouter;

/**
 * 个人页面
 */

@ARouter(path = "/app/PersonalActivity")
public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
    }

    //跳转MainActivity
    public void jump(View view) {
        Class targetClass = MainActivity$$ARouter.findTargetClass("/app/MainActivity");
        Intent intent =new Intent(this,targetClass);
        startActivity(intent);
    }
}

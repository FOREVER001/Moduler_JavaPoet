package com.tianzhuan.net_modular_javapoet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tianzhuan.annotation.ARouter;

/**
 * 订单页面
 */

@ARouter(path = "/app/OrderActivity")
public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    //跳转personal
    public void jump(View view) {
        Class targetClass = PersonalActivity$$ARouter.findTargetClass("/app/PersonalActivity");
        Intent intent =new Intent(this,targetClass);
        startActivity(intent);
    }
}

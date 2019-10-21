package com.tianzhuan.net_modular_javapoet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.tianzhuan.annotation.ARouter;

@ARouter(path = "/app/MainActivity")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //跳转order
    public void jump(View view) {
        Class targetClass = OrderActivity$$ARouter.findTargetClass("/app/OrderActivity");
        Intent intent =new Intent(this,targetClass);
        startActivity(intent);
    }
}

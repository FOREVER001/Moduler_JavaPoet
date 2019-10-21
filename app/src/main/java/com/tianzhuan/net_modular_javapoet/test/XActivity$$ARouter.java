package com.tianzhuan.net_modular_javapoet.test;


import com.tianzhuan.net_modular_javapoet.MainActivity;

public class XActivity$$ARouter {
    public static Class<?> findTargetClass(String path){

        return path.equalsIgnoreCase("/app/MainActivity")?MainActivity.class :null;
    }
}

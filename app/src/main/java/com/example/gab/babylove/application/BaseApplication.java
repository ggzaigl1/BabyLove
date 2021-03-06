package com.example.gab.babylove.application;

import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.ggz.baselibrary.R;
import com.ggz.baselibrary.application.BaseActivityLifecycleCallbacks;
import com.ggz.baselibrary.retrofit.ioc.ConfigUtils;
import com.ggz.baselibrary.utils.L;
import com.ggz.baselibrary.utils.NightModeConfig;


/**
 * 基础 application
 *
 * @author fangs
 * @date 2017/5/5
 */
public class BaseApplication extends MultiDexApplication {

    //也可以直接使用代码块直接设置
    //static {
    //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_ NIGHT_YES);
    //}

    @Override
    public void onCreate() {
        super.onCreate();
        L.e("ActivityCallbacks", "Application--Create() 启动-----");

        //初始化应用框架基础配置工具类
        new ConfigUtils.ConfigBiuder()
                .setBgColor(R.color.colorPrimary)
//                .setTitleColor(R.color.red)
//                .setTitleCenter(true)
//                .setCer(CER)
                .setBASE_URL("https://www.wanandroid.com/")
                .create(this);

//        设置activity 生命周期回调
        registerActivityLifecycleCallbacks(new BaseActivityLifecycleCallbacks());

        //根据app上次退出的状态来判断是否需要设置夜间模式,提前在SharedPreference中存了一个是否是夜间模式的boolean值
        boolean isNightMode = NightModeConfig.getInstance().getNightMode(getApplicationContext());
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}

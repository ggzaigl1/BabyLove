package com.example.gab.babylove.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.example.gab.babylove.R;
import com.example.gab.babylove.utils.CleanMessageUtil;
import com.fy.baselibrary.base.BaseActivity;
import com.fy.baselibrary.utils.T;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Gab on 2017/12/20 0020.
 * 工具栏
 */

public class ToolsActivity extends BaseActivity {

    @BindView(R.id.tv_cache_size)
    TextView tv_cache_size;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if (what == 0) {
                //在主线程中需要执行的操作，一般是UI操作
                tv_cache_size.setText("0K");
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_tools;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText("设置");
        tvMenu.setVisibility(View.GONE);

        try {
            tv_cache_size.setText(CleanMessageUtil.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.night_switch, R.id.Ll_cache, R.id.Ll_praise})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.night_switch:
                Switch sw = (Switch) view;
                boolean isChecked = sw.isChecked();
                if (isChecked) {
                    T.showShort("开启夜间模式");
                } else {
                    T.showShort("关闭夜间模式");
                }
                break;
            case R.id.Ll_praise:
                startMarket();
                break;
            case R.id.Ll_cache:
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                        .setTitle("确认清除")
                        .setMessage("是否清除缓存")
                        .setCancelable(false)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CleanMessageUtil.clearAllCache(getApplicationContext());
                                        mHandler.sendEmptyMessage(0);
                                    }
                                }).start();

                            }
                        }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                builder.show();
                break;
        }
    }

    public void startMarket() {
        Uri uri = Uri.parse(String.format("market://details?id=%s", getPackageName()));
        if (isIntentSafe(mContext, uri)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            T.showShort("无法打开应用市场");
        }
    }

    public static boolean isIntentSafe(Activity activity, Uri uri) {
        Intent mapCall = new Intent(Intent.ACTION_VIEW, uri);
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(mapCall, 0);
        return activities.size() > 0;
    }

}

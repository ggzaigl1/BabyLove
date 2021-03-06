package com.example.gab.babylove.ui.view.activity;

import android.app.Activity;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.gab.babylove.R;
import com.example.gab.babylove.base.BaseActivity;
import com.example.gab.babylove.entity.ViewBean;
import com.example.gab.babylove.ui.view.fragment.SystemFlyFragment;
import com.ggz.baselibrary.application.IBaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 初夏小溪
 * @date 2018/4/20 0020
 * 知识体系 详情页
 */

public class SystemActivity extends BaseActivity implements IBaseActivity {

    private ArrayList<SystemFlyFragment> mFragments = new ArrayList<>();
    private List<ViewBean.ChildrenBean> mChildren;
    ViewBean bean;

    @BindView(R.id.sliding_tab)
    TabLayout mTabLayout;
    @BindView(R.id.view_page)
    ViewPager mViewPager;
    @BindView(R.id.fab_top)
    FloatingActionButton mFabTop;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.fragment_list_floab;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        //全局变灰
//        Paint mPaint = new Paint();
//        ColorMatrix cm = new ColorMatrix();
//        cm.setSaturation(0);
//        mPaint.setColorFilter(new ColorMatrixColorFilter(cm));
//        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, mPaint);

        mFabTop.setVisibility(View.GONE);
        bean = (ViewBean) getIntent().getSerializableExtra("bean");
        mChildren = bean.getChildren();
        for (ViewBean.ChildrenBean child : mChildren) {
            SystemFlyFragment systemFlyFragment = SystemFlyFragment.getInstance(child.getId(), "");
            mFragments.add(systemFlyFragment);
        }
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mChildren.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}

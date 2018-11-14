package com.example.gab.babylove.ui.main.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;

import com.example.gab.babylove.R;
import com.example.gab.babylove.api.ApiService;
import com.example.gab.babylove.base.BaseActivity;
import com.example.gab.babylove.entity.GankBean;
import com.example.gab.babylove.entity.OrListBean;
import com.example.gab.babylove.ui.main.adapter.PicturesAdapter;
import com.ggz.baselibrary.application.IBaseActivity;
import com.ggz.baselibrary.retrofit.NetCallBack;
import com.ggz.baselibrary.retrofit.RequestUtils;
import com.ggz.baselibrary.utils.JumpUtils;
import com.ggz.baselibrary.utils.T;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Gab
 * @date 2017/12/19 0019
 * 美图欣赏
 */

public class BelleActivity extends BaseActivity implements IBaseActivity {

    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.fab_top)
    FloatingActionButton mFabTop;
    PicturesAdapter mAdapter;
    private int mCurPage = 1;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private final static int FIND_FIRST_VISIBLE_ITEM_POSITION = 100;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_belle;
    }

    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        mRefreshLayout.autoRefresh();
        initRv();
        initRefresh();
        getCourseDetails(mCurPage);
    }

    /**
     * 获取数据
     *
     * @param mCurPage
     */
    @SuppressLint("CheckResult")
    private void getCourseDetails(int mCurPage) {
        RequestUtils.create(ApiService.class)
                .getCourseDetails(50, mCurPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetCallBack<GankBean>() {
                    @Override
                    protected void onSuccess(GankBean gankBean) {
                        if (null != gankBean && null != gankBean.getResults()) {
                            if (mRefreshLayout.isRefreshing()) {
                                mAdapter.setNewData(gankBean.getResults());
                                mRefreshLayout.finishRefresh();
                            } else if (mRefreshLayout.isLoading()) {
                                mAdapter.getData().addAll(gankBean.getResults());
                                mRefreshLayout.finishLoadMore();
                                mAdapter.notifyDataSetChanged();
                                T.showShort("又加载了" + gankBean.getResults().size() + "位妹子");
                            } else {
                                mAdapter.setNewData(gankBean.getResults());
                                T.showShort("加载了" + gankBean.getResults().size() + "妹子");
                            }
                        }
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });

    }

    /**
     * 设置刷新
     */
    private void initRefresh() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mCurPage += 1;
                getCourseDetails(mCurPage);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mCurPage = 1;
                getCourseDetails(1);
            }
        });
    }


    private void initRv() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PicturesAdapter(R.layout.item_gank_list_context, new ArrayList<>());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager.findFirstVisibleItemPosition() != 0) {
                    /* 悬浮按钮显示动画 */
                    if (mFabTop.getVisibility() == View.GONE) {
                        mFabTop.setVisibility(View.VISIBLE);
                        ViewCompat.animate(mFabTop)
                                .scaleX(1.0F)
                                .scaleY(1.0F)
                                .alpha(1.0F)
                                .setInterpolator(INTERPOLATOR).withLayer()
                                .setListener(null).start();
                    }
                } else {
                    fabOutAnim();
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<GankBean.ResultsBean> data = mAdapter.getData();
            OrListBean orListBean = new OrListBean(data);
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            bundle.putSerializable("orListBean", orListBean);
            JumpUtils.jumpFade(this, PictureDetailActivity.class, bundle);
        });

        mFabTop.setOnClickListener(v -> {
            LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            //如果超过100项直接跳到开头，不然要滚好久
            if (manager.findFirstVisibleItemPosition() < FIND_FIRST_VISIBLE_ITEM_POSITION) {
                mRecyclerView.smoothScrollToPosition(0);
            } else {
                mRecyclerView.scrollToPosition(0);
                fabOutAnim();
            }
        });
    }

    /**
     * 悬浮图标隐藏动画
     */
    private void fabOutAnim() {
        if (mFabTop.getVisibility() == View.VISIBLE) {
            ViewCompat.animate(mFabTop)
                    .scaleX(0.0F)
                    .scaleY(0.0F)
                    .alpha(0.0F)
                    .setInterpolator(INTERPOLATOR).withLayer()
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {

                        }

                        @SuppressLint("RestrictedApi")
                        @Override
                        public void onAnimationEnd(View view) {
                            mFabTop.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    }).start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }
}

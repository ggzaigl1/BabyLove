package com.example.gab.babylove.ui.main.ornamental;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gab.babylove.R;
import com.example.gab.babylove.api.ApiService;
import com.example.gab.babylove.base.BaseActivity;
import com.example.gab.babylove.entity.CourseList;
import com.example.gab.babylove.ui.main.ornamental.adapter.OrnamentalListContextAdapter;
import com.ggz.baselibrary.application.IBaseActivity;
import com.ggz.baselibrary.retrofit.NetCallBack;
import com.ggz.baselibrary.retrofit.RequestUtils;
import com.ggz.baselibrary.utils.JumpUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
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
 *
 * @author 初夏小溪
 * @date 2018/4/9 0009
 * 运动课程列表
 */

public class OrnamentalListContextActivity extends BaseActivity implements IBaseActivity {

    @BindView(R.id.ry_data)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    OrnamentalListContextAdapter mAdapter;
    private int mPageNo = 1;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_ornamental_list_context;
    }

    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        initRefresh();
        initRv();
        getCourseDetails(mPageNo);
    }

    private void getCourseDetails(int mPageNo) {
        mKProgressHUD = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setCancellable(true).setAnimationSpeed(2).setDimAmount(0.5f).show();
        RequestUtils.create(ApiService.class)
                .getCourseList(mPageNo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(RequestUtils::addDisposable)
                .subscribe(new NetCallBack<CourseList>() {
                    @Override
                    protected void onSuccess(CourseList t) {
                        if (t.getResult() == 1) {
                            mKProgressHUD.dismiss();
                            if (!t.getData().isEmpty()) {
                                if (mRefreshLayout.isRefreshing()) {
                                    mAdapter.setNewData(t.getData());
                                    mRefreshLayout.finishRefresh();
                                } else if (mRefreshLayout.isLoading()) {
                                    mAdapter.getData().addAll(t.getData());
                                    mRefreshLayout.finishLoadMore();
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    mAdapter.setNewData(t.getData());
                                }
                            }
                        }
                    }

                    @Override
                    protected void updataLayout(int flag) {
                        if (null != mRefreshLayout) {
                            mKProgressHUD.dismiss();
                            if (mRefreshLayout.isRefreshing()) {
                                mRefreshLayout.finishRefresh();
                            }
                            if (mRefreshLayout.isLoading()) {
                                mRefreshLayout.finishLoadMore();
                            }
                        }
                    }
                });
    }

    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new OrnamentalListContextAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<CourseList.DataBean> data = mAdapter.getData();
            Bundle bundle = new Bundle();
            bundle.putInt("id", data.get(position).getId());
            JumpUtils.jumpFade(OrnamentalListContextActivity.this, OrnamentalContextActivity.class, bundle);
        });
    }

    private void initRefresh() {
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mPageNo += 1;
                getCourseDetails(mPageNo);
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                getCourseDetails(1);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
            mKProgressHUD.dismiss();
        }
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
            mKProgressHUD.dismiss();
        }
    }

}

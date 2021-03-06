package com.example.gab.babylove.ui.navigation.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.gab.babylove.R;
import com.example.gab.babylove.entity.NavigationBean;
import com.example.gab.babylove.ui.main.search.SearchArticleActivity;
import com.ggz.baselibrary.utils.ResourceUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * @author 初夏小溪
 * @date 2018/4/19 0019
 * 视图导航
 */

public class NavigationRightAdapter extends BaseQuickAdapter<NavigationBean.ArticlesBean, BaseViewHolder> {

    public NavigationRightAdapter(@Nullable List<NavigationBean.ArticlesBean> data) {
        super(R.layout.item_navigation_right, data);
        List<NavigationBean.ArticlesBean> list = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, NavigationBean.ArticlesBean item) {
        helper.setText(R.id.tv_date, item.getTitle())
                .setTextColor(R.id.tv_date, ResourceUtils.getRandomColor());
        TextView tv_date = helper.getView(R.id.tv_date);
        tv_date.setText(item.getTitle());
        tv_date.setTextColor(ResourceUtils.getRandomColor());
    }
}
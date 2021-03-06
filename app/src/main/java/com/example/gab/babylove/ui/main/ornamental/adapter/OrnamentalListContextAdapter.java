package com.example.gab.babylove.ui.main.ornamental.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.gab.babylove.R;
import com.example.gab.babylove.entity.CourseList;
import com.ggz.baselibrary.utils.imgload.ImgLoadUtils;

import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 *
 * @author 初夏小溪
 * @date 2018/4/9 0009
 * 体育运动
 */

public class OrnamentalListContextAdapter extends BaseQuickAdapter<CourseList.DataBean, BaseViewHolder> {

    public OrnamentalListContextAdapter(@Nullable List<CourseList.DataBean> data) {
        super(R.layout.item_ornamental_list_context, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseList.DataBean item) {
        helper.setText(R.id.tv_name, item.getName()).setText(R.id.tv_Join_num, item.getJoinnum() + "人已参加");
        ImgLoadUtils.loadImage(mContext, item.getIcon(), helper.getView(R.id.imgHead));
        MaterialRatingBar materialRatingBar = helper.getView(R.id.Rb_trainlevel);
        materialRatingBar.setNumStars((item.getTrainlevel()));
    }
}

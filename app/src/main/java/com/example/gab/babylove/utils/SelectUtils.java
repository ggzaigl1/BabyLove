package com.example.gab.babylove.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;

import com.example.gab.babylove.R;
import com.ggz.baselibrary.utils.ResourceUtils;
import com.ggz.baselibrary.utils.TintUtils;
/**
 *
 * 代码设置 选择器
 *
 * @author fangs
 * @date 2018/4/2
 */
public class SelectUtils {

    private SelectUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 获取 指定 ID的 drawable，生成的 选择器
     * @param draId
     * @return
     */
    public static Drawable getSelector(@DrawableRes int draId){
        int[] colors = new int[]{ResourceUtils.getColor(R.color.button_pressed),
                ResourceUtils.getColor(R.color.button_pressed),
                ResourceUtils.getColor(R.color.button_pressed),
                ResourceUtils.getColor(R.color.button_normal)};

        int[][] states = new int[4][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{android.R.attr.state_checked};
        states[3] = new int[]{};

        return TintUtils.tintSelector(TintUtils.getDrawable(draId), colors, states);
    }

    /**
     * 根据 drawable ID ，生成 选择器 </br>
     * normal：白色；
     * pressed：灰色
     * @param draId
     * @return
     */
    public static Drawable getTagSelector(@DrawableRes int draId){
        int[] colors = new int[]{
                ResourceUtils.getColor(R.color.button_pressed2),
                ResourceUtils.getColor(R.color.button_normal2)};

        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};

        Drawable drawable = TintUtils.getDrawable(draId);
//        设置 着色器模式
//        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.MULTIPLY);
        return TintUtils.tintSelector(drawable, colors, states);
    }

    /**
     * 根据 drawable ID ，生成 选择器 </br>
     * normal：白色；
     * pressed：灰色
     * @param draId
     * @return
     */
    public static Drawable getBtnSelector(@DrawableRes int draId){
        int[] colors = new int[]{
                ResourceUtils.getColor(R.color.button_pressed),
                ResourceUtils.getColor(R.color.button_normal)};

        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{};

        Drawable drawable = TintUtils.getDrawable(draId);
//        设置 着色器模式
//        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.MULTIPLY);
        return TintUtils.tintSelector(drawable, colors, states);
    }
}

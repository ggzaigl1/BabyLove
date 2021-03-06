package com.ggz.baselibrary.utils;

/**
 * 常量
 * Created by fangs on 2017/5/8.
 */
public class ConstantUtils {

    public static final String ISNIGHT = "isNight";
    public static final String CURRENT_INDEX = "currentIndex";
    public static final String TRANSITION_PIC = "transitionPic";
    public static final String TRANSITION_SEARCHVIEW = "transitionSearchView";

    /**
     * 动态服务器地址
     */
    public static String custom_Url = "";

    /**
     * 默认的超时时间
     */
    public static int DEFAULT_MILLISECONDS = 60000;

    /**
     * 服务器地址
     */
    public static String BASE_URL = "http://www.wanandroid.com/";

    /**
     * 图片 地址 (可选)
     */
    public static String IMG_BASE_URL = BASE_URL + "image";

    /**
     * 用户id
     */
    public static String userId = "userid";

    /**
     * 操作令牌
     */
    public static String token = "";

    /**
     * 程序是否需要横竖屏切换
     */
    public static boolean isOrientation = false;

    /**
     * 用户是否登录 key
     */
    public static String isLogin = "is_Login";

    /**
     * 缓存用户名 key
     */
    public static String userName = "User_Name";


    /**
     * APP 当前模式 （日间/夜间）
     */
    public static final String APP_MODE = "appModeSwitch";

    /**
     * 用户是否 第一次打开APP
     */
    public static final String isfirstOpenApp = "userIsFirstOpenApp";


    /**
     * 吸附 ViewType
     */
    public static final int StickyType = 58;

    /**
     * 所有下载任务 缓存key
     */
    public static final String AllDownTask = "All_Task_DownLoad";

    /**
     * 一个下载任务 已经下载的进度百分比 数值
     */
    public static final String DownPercent = "Task_DownLoad_Percent";

    /**
     * 一个下载任务 已经下载的总长度
     */
    public static final String DownTask = "Task_DownLoad_length";

    /**
     * 一个线程 下载数据的长度
     */
    public static final String DownTherad = "Therad_DownLoad_length";


    // Activity life Events
    public static final String CREATE = "CREATE";
    public static final String START = "START";
    public static final String RESUME = "RESUME";
    public static final String PAUSE = "PAUSE";
    public static final String STOP = "STOP";
    public static final String DESTROY = "DESTROY";

    // Fragment life  Events
    public static final String ATTACH = "ATTACH";
    public static final String CREATE_VIEW = "CREATE_VIEW";
    public static final String DESTROY_VIEW = "DESTROY_VIEW";
    public static final String DETACH = "DETACH";

}

package com.example.gab.babylove.ui.main.other;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gab.babylove.R;
import com.example.gab.babylove.base.BaseActivity;
import com.example.gab.babylove.widget.fingerprintcore.FingerprintCore;
import com.example.gab.babylove.widget.fingerprintcore.KeyguardLockScreenManager;
import com.ggz.baselibrary.application.IBaseActivity;
import com.ggz.baselibrary.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 指紋相关工具类
 */
public class FingerprintMainActivity extends BaseActivity implements IBaseActivity {

    @BindView(R.id.fingerprint_guide)
    ImageView mFingerprintGuide;
    @BindView(R.id.fingerprint_guide_tip)
    TextView mFingerprintGuideTip;

    private FingerprintCore mFingerprintCore;
    private KeyguardLockScreenManager mKeyguardLockScreenManager;

    @Override
    public boolean isShowHeadView() {
        return true;
    }

    @Override
    public int setView() {
        return R.layout.activity_fingerprint_main;
    }

    @Override
    public void setStatusBar(Activity activity) {
//        MdStatusBar.setColorBar(activity, R.color.statusBar, R.color.statusBar);
    }

    @Override
    public void initData(Activity activity, Bundle savedInstanceState) {
        mFingerprintCore = new FingerprintCore(this);
        mFingerprintCore.setFingerprintManager(mResultListener);
        mKeyguardLockScreenManager = new KeyguardLockScreenManager(this);
    }

    @OnClick({R.id.fingerprint_recognition_start, R.id.fingerprint_recognition_cancel, R.id.fingerprint_recognition_sys_unlock,
            R.id.fingerprint_recognition_sys_setting})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fingerprint_recognition_start:
                startFingerprintRecognition();
                break;
            case R.id.fingerprint_recognition_cancel:
                cancelFingerprintRecognition();
                break;
            case R.id.fingerprint_recognition_sys_unlock:
                startFingerprintRecognitionUnlockScreen();
                break;
            case R.id.fingerprint_recognition_sys_setting:
                openFingerPrintSettingPage(this);
                break;
            default:
                break;
        }
    }

    /**
     * 跳转到设置页面
     *
     * @param context
     */
    public void openFingerPrintSettingPage(Context context) {
        Intent intent = new Intent("android.settings.SETTINGS");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception ignored) {

        }
    }

    /**
     * 开始指纹识别
     */
    private void startFingerprintRecognition() {
        if (mFingerprintCore.isSupport()) {
            if (!mFingerprintCore.isHasEnrolledFingerprints()) {
                T.showShort(getString(R.string.fingerprint_recognition_not_enrolled));
                openFingerPrintSettingPage(this);
                return;
            }
            T.showShort(getString(R.string.fingerprint_recognition_tip));
            mFingerprintGuideTip.setText(R.string.fingerprint_recognition_tip);
            mFingerprintGuide.setBackgroundResource(R.mipmap.icon_fingerprint_guide);
            if (mFingerprintCore.isAuthenticating()) {
                T.showShort(getString(R.string.fingerprint_recognition_authenticating));
            } else {
                mFingerprintCore.startAuthenticate();
            }
        } else {
            T.showShort(getString(R.string.fingerprint_recognition_not_support));
            mFingerprintGuideTip.setText(R.string.fingerprint_recognition_tip);
        }
    }

    /**
     * 取消指纹识别
     */
    private void cancelFingerprintRecognition() {
        if (mFingerprintCore.isAuthenticating()) {
            mFingerprintCore.cancelAuthenticate();
            mFingerprintGuideTip.setText(R.string.fingerprint_recognition_guide_tip);
            mFingerprintGuide.setBackgroundResource(R.mipmap.icon_fingerprint_normal);
        }
    }

    /**
     * 开启屏幕解锁
     */
    private void startFingerprintRecognitionUnlockScreen() {
        if (mKeyguardLockScreenManager == null) {
            return;
        }
        if (!mKeyguardLockScreenManager.isOpenLockScreenPwd()) {
            T.showShort(getString(R.string.fingerprint_not_set_unlock_screen_pws));
            openFingerPrintSettingPage(this);
            return;
        }
        mKeyguardLockScreenManager.showAuthenticationScreen(this);
    }

    /**
     * 指纹识别回调接口
     */
    private FingerprintCore.IFingerprintResultListener mResultListener = new FingerprintCore.IFingerprintResultListener() {
        @Override
        public void onAuthenticateSuccess() {
            T.showShort(getString(R.string.fingerprint_recognition_success));
            mFingerprintGuideTip.setText(R.string.fingerprint_recognition_guide_tip);
            mFingerprintGuide.setBackgroundResource(R.mipmap.icon_fingerprint_normal);
            openFingerPrintSettingPage(FingerprintMainActivity.this);
        }

        @Override
        public void onAuthenticateFailed(int helpId) {
            T.showShort(getString(R.string.fingerprint_recognition_failed));
            mFingerprintGuideTip.setText(R.string.fingerprint_recognition_failed);
        }

        @Override
        public void onAuthenticateError(int errMsgId) {
            mFingerprintGuideTip.setText(R.string.fingerprint_recognition_guide_tip);
            mFingerprintGuide.setBackgroundResource(R.mipmap.icon_fingerprint_normal);
            T.showShort(getString(R.string.fingerprint_recognition_error));
        }

        @Override
        public void onStartAuthenticateResult(boolean isSuccess) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == KeyguardLockScreenManager.REQUEST_CODE_CONFIRM_DEVICE_CREDENTIALS) {
            // Challenge completed, proceed with using cipher
            if (resultCode == RESULT_OK) {
                T.showShort(getString(R.string.sys_pwd_recognition_success));
            } else {
                T.showShort(getString(R.string.sys_pwd_recognition_failed));
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mFingerprintCore != null) {
            mFingerprintCore.onDestroy();
            mFingerprintCore = null;
        }
        if (mKeyguardLockScreenManager != null) {
            mKeyguardLockScreenManager.onDestroy();
            mKeyguardLockScreenManager = null;
        }
        mResultListener = null;
        super.onDestroy();
    }
}

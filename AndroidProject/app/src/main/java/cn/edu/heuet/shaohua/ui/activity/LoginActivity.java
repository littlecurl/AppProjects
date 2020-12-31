package cn.edu.heuet.shaohua.ui.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;

import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengClient;
import com.hjq.umeng.UmengLogin;

import cn.edu.heuet.shaohua.R;
import cn.edu.heuet.shaohua.aop.DebugLog;
import cn.edu.heuet.shaohua.aop.SingleClick;
import cn.edu.heuet.shaohua.common.MyActivity;
import cn.edu.heuet.shaohua.databinding.LoginActivityBinding;
import cn.edu.heuet.shaohua.helper.InputTextHelper;
import cn.edu.heuet.shaohua.http.glide.GlideApp;
import cn.edu.heuet.shaohua.http.model.HttpData;
import cn.edu.heuet.shaohua.http.request.LoginApi;
import cn.edu.heuet.shaohua.http.response.LoginBean;
import cn.edu.heuet.shaohua.other.IntentKey;
import cn.edu.heuet.shaohua.other.KeyboardWatcher;
import cn.edu.heuet.shaohua.wxapi.WXEntryActivity;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 登录界面
 */
public final class LoginActivity extends MyActivity
        implements UmengLogin.OnLoginListener,
        KeyboardWatcher.SoftKeyboardStateListener {

    private LoginActivityBinding binding;

    @DebugLog
    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(IntentKey.PHONE, phone);
        intent.putExtra(IntentKey.PASSWORD, password);
        context.startActivity(intent);
    }

    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.75f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    @Override
    protected void initView() {
        binding = getBinding();
        setOnClickListener(
                binding.tvLoginForget,
                binding.btnLoginCommit,
                binding.ivLoginQq,
                binding.ivLoginWechat);

        InputTextHelper.with(this)
                .addView(binding.etLoginPhone)
                .addView(binding.etLoginPassword)
                .setMain(binding.btnLoginCommit)
                .build();
    }

    @Override
    protected void initData() {

        postDelayed(() -> {
            // 因为在小屏幕手机上面，因为计算规则的因素会导致动画效果特别夸张，所以不在小屏幕手机上面展示这个动画效果
//            if (binding.vLoginBlank.getHeight() > binding.llLoginBody.getHeight()) {
            // 只有空白区域的高度大于登录框区域的高度才展示动画
            KeyboardWatcher.with(LoginActivity.this)
                    .setListener(LoginActivity.this);
//            }
        }, 500);

        // 判断用户当前有没有安装 QQ
        if (!UmengClient.isAppInstalled(this, Platform.QQ)) {
            binding.ivLoginQq.setVisibility(View.GONE);
        }

        // 判断用户当前有没有安装微信
        if (!UmengClient.isAppInstalled(this, Platform.WECHAT)) {
            binding.ivLoginWechat.setVisibility(View.GONE);
        }

        // 如果这两个都没有安装就隐藏提示
        if (binding.ivLoginQq.getVisibility() == View.GONE && binding.ivLoginWechat.getVisibility() == View.GONE) {
            binding.llLoginOther.setVisibility(View.GONE);
        }

        // 填充传入的手机号和密码
        binding.etLoginPhone.setText(getString(IntentKey.PHONE));
        binding.etLoginPassword.setText(getString(IntentKey.PASSWORD));

    }

    @Override
    public void onRightClick(View v) {
        // 跳转到注册界面
        startActivityForResult(RegisterActivity.class, (resultCode, data) -> {
            // 如果已经注册成功，就执行登录操作
            if (resultCode == RESULT_OK && data != null) {
                binding.etLoginPhone.setText(data.getStringExtra(IntentKey.PHONE));
                binding.etLoginPassword.setText(data.getStringExtra(IntentKey.PASSWORD));
                binding.etLoginPassword.setSelection(binding.etLoginPassword.getText().length());
                onClick(binding.btnLoginCommit);
            }
        });
    }

    @Override
    public void onLeftClick(View v) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == binding.tvLoginForget) {
            startActivity(PasswordForgetActivity.class);
        } else if (v == binding.btnLoginCommit) {
            if (binding.etLoginPhone.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }

            if (true) {
                showDialog();
                postDelayed(() -> {
                    hideDialog();
                    startActivity(HomeActivity.class);
                    finish();
                }, 2000);
                return;
            }

            EasyHttp.post(this)
                    .api(new LoginApi()
                            .setPhone(binding.etLoginPhone.getText().toString())
                            .setPassword(binding.etLoginPassword.getText().toString()))
                    .request(new HttpCallback<HttpData<LoginBean>>(this) {

                        @Override
                        public void onSucceed(HttpData<LoginBean> data) {
                            // 更新 Token
                            EasyConfig.getInstance()
                                    .addParam("token", data.getData().getToken());
                            // 跳转到主页
                            startActivity(HomeActivity.class);
                            finish();
                        }
                    });
        } else if (v == binding.ivLoginQq || v == binding.ivLoginWechat) {
            toast("记得改好第三方 AppID 和 AppKey，否则会调不起来哦");
            Platform platform;
            if (v == binding.ivLoginQq) {
                platform = Platform.QQ;
            } else if (v == binding.ivLoginWechat) {
                platform = Platform.WECHAT;
                toast("也别忘了改微信 " + WXEntryActivity.class.getSimpleName() + " 类所在的包名哦");
            } else {
                throw new IllegalStateException("are you ok?");
            }
            UmengClient.login(this, platform, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 友盟登录回调
        UmengClient.onActivityResult(this, requestCode, resultCode, data);
    }

    /**
     * {@link UmengLogin.OnLoginListener}
     */

    /**
     * 授权成功的回调
     *
     * @param platform 平台名称
     * @param data     用户资料返回
     */
    @Override
    public void onSucceed(Platform platform, UmengLogin.LoginData data) {
        // 判断第三方登录的平台
        switch (platform) {
            case QQ:
                break;
            case WECHAT:
                break;
            default:
                break;
        }

        GlideApp.with(this)
                .load(data.getAvatar())
                .circleCrop()
                .into(binding.ivLoginLogo);

        toast("昵称：" + data.getName() + "\n" + "性别：" + data.getSex());
        toast("id：" + data.getId());
        toast("token：" + data.getToken());
    }

    /**
     * 授权失败的回调
     *
     * @param platform 平台名称
     * @param t        错误原因
     */
    @Override
    public void onError(Platform platform, Throwable t) {
        toast("第三方登录出错：" + t.getMessage());
    }

    /**
     * 授权取消的回调
     *
     * @param platform 平台名称
     */
    @Override
    public void onCancel(Platform platform) {
        toast("取消第三方登录");
    }

    /**
     * {@link KeyboardWatcher.SoftKeyboardStateListener}
     */

    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] location = new int[2];
        // 获取这个 View 在屏幕中的坐标（左上角）
        binding.llLoginBody.getLocationOnScreen(location);
        //int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + binding.llLoginBody.getHeight());
        if (keyboardHeight > bottom) {
            // 执行位移动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(binding.llLoginBody, "translationY", 0, -(keyboardHeight - bottom));
            objectAnimator.setDuration(mAnimTime);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();

            // 执行缩小动画
            binding.ivLoginLogo.setPivotX(binding.ivLoginLogo.getWidth() / 2f);
            binding.ivLoginLogo.setPivotY(binding.ivLoginLogo.getHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(binding.ivLoginLogo, "scaleX", 1.0f, mLogoScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(binding.ivLoginLogo, "scaleY", 1.0f, mLogoScale);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(binding.ivLoginLogo, "translationY", 0, 0);
            animatorSet.play(translationY).with(scaleX).with(scaleY);
            animatorSet.setDuration(mAnimTime);
            animatorSet.start();
        }
    }


    @Override
    public void onSoftKeyboardClosed() {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(binding.llLoginBody, "translationY", binding.llLoginBody.getTranslationY(), 0);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();

//        if (binding.ivLoginLogo.getTranslationY() == 0) {
//            return;
//        }
        // 执行放大动画
        binding.ivLoginLogo.setPivotX(binding.ivLoginLogo.getWidth() / 2f);
        binding.ivLoginLogo.setPivotY(binding.ivLoginLogo.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(binding.ivLoginLogo, "scaleX", mLogoScale, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(binding.ivLoginLogo, "scaleY", mLogoScale, 1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(binding.ivLoginLogo, "translationY", 0, 0);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}
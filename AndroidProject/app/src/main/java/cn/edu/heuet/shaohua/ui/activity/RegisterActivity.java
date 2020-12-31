package cn.edu.heuet.shaohua.ui.activity;

import android.content.Intent;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;

import cn.edu.heuet.shaohua.R;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.rxtool.RxClickableSpanShowDialog;
import com.hjq.widget.rxtool.RxDialogSure;
import com.hjq.widget.rxtool.RxTextTool;
import com.hjq.widget.view.CountdownView;

import java.util.ArrayList;
import java.util.List;

import cn.edu.heuet.shaohua.aop.SingleClick;
import cn.edu.heuet.shaohua.common.MyActivity;
import cn.edu.heuet.shaohua.databinding.RegisterActivityBinding;
import cn.edu.heuet.shaohua.helper.InputTextHelper;
import cn.edu.heuet.shaohua.http.model.HttpData;
import cn.edu.heuet.shaohua.http.request.GetCodeApi;
import cn.edu.heuet.shaohua.http.request.RegisterApi;
import cn.edu.heuet.shaohua.http.response.RegisterBean;
import cn.edu.heuet.shaohua.other.IntentKey;
import cn.edu.heuet.shaohua.ui.dialog.MessageDialog;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 注册界面
 */
public final class RegisterActivity extends MyActivity {

    private EditText mPhoneView;
    private CountdownView mCountdownView;

    private EditText mCodeView;

    private EditText mPasswordView1;
    private EditText mPasswordView2;

    private Button mCommitView;
    private RegisterActivityBinding binding;
    private String shaohuaClientServiceTitle;
    private String shaohuaPrivacyPolicyTitle;
    private String shaohuaPlatformServiceTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.register_activity;
    }

    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    protected void initView() {

        binding = getBinding();

        binding.editspinner.setMaxLine(1);
        List<String> list = new ArrayList<>();
        list.add("河北经贸大学");
        list.add("河北体育学院");
        list.add("石家庄铁道大学");
        list.add("石家庄政法职业学院");
        list.add("河北医科大学");
        list.add("河北科技大学");
        list.add("河北大学");
        list.add("河北师范大学");
        binding.editspinner.setItemData(list);

        mPhoneView = findViewById(R.id.et_register_phone);
        mCountdownView = findViewById(R.id.cv_register_countdown);
        mCodeView = findViewById(R.id.et_register_code);
        mPasswordView1 = findViewById(R.id.et_register_password1);
        mPasswordView2 = findViewById(R.id.et_register_password2);
        mCommitView = findViewById(R.id.btn_register_commit);
        setOnClickListener(mCountdownView, mCommitView);

        // 给这个 View 设置沉浸式，避免状态栏遮挡
        ImmersionBar.setTitleBar(this, findViewById(R.id.tv_register_title));

        InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .addView(mPasswordView1)
                .addView(mPasswordView2)
                .setMain(mCommitView)
                .build();

        shaohuaClientServiceTitle = getString(R.string.shaohua_client_service_title);
        shaohuaPrivacyPolicyTitle = getString(R.string.shaohua_privacy_policy_title);
        shaohuaPlatformServiceTitle = getString(R.string.shaohua_platform_service_title);

        RxTextTool.getBuilder("").setBold().setAlign(Layout.Alignment.ALIGN_CENTER)
                .append(getString(R.string.register_agree))
                .append(getString(R.string.shaohua_client_service_title))
                .setClickSpan(
                        new RxClickableSpanShowDialog(new RxDialogSure(this,
                                shaohuaClientServiceTitle,
                                getString(R.string.shaohua_client_service_content)))
                )
                .append("、")
                .append(getString(R.string.shaohua_privacy_policy_title))
                .setClickSpan(
                        new RxClickableSpanShowDialog(new RxDialogSure(this,
                                shaohuaPrivacyPolicyTitle,
                                getString(R.string.shaohua_privacy_policy_content)))
                )
                .append("、")
                .append(getString(R.string.shaohua_platform_service_title))
                .setClickSpan(
                        new RxClickableSpanShowDialog(new RxDialogSure(this,
                                shaohuaPlatformServiceTitle,
                                getString(R.string.shaohua_platform_service_content)))
                )
                .into(binding.tvRegisterNote);
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == mCountdownView) {
            if (mPhoneView.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }

            if (true) {
                toast(R.string.common_code_send_hint);
                mCountdownView.start();
                return;
            }

            // 获取验证码
            EasyHttp.post(this)
                    .api(new GetCodeApi()
                            .setPhone(mPhoneView.getText().toString()))
                    .request(new HttpCallback<HttpData<Void>>(this) {

                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            toast(R.string.common_code_send_hint);
                            mCountdownView.start();
                        }

                        @Override
                        public void onFail(Exception e) {
                            super.onFail(e);
                            mCountdownView.start();
                        }
                    });
        } else if (v == mCommitView) {

            if (mPhoneView.getText().toString().length() != 11) {
                toast(R.string.common_phone_input_error);
                return;
            }
            String password1 = mPasswordView1.getText().toString();
            String password2 = mPasswordView2.getText().toString();
            if (!TextUtils.equals(password1, password2)) {
                toast(R.string.common_password_input_unlike);
                return;
            }

            if (password1.length() < 6) {
                toast(R.string.common_password_length_less_6);
            }
            SpannableStringBuilder content = RxTextTool.getBuilder("")
                    .append(getString(R.string.register_note_content1))
                    .append(shaohuaPrivacyPolicyTitle).setUrl("www.baidu.com")
                    .append(getString(R.string.register_note_content2))
                    .append("\n\n")
                    .append(shaohuaClientServiceTitle).setUrl("www.baidu.com")
                    .append("\n")
                    .append(shaohuaPrivacyPolicyTitle).setUrl("www.baidu.com")
                    .append("\n")
                    .append(shaohuaPlatformServiceTitle).setUrl("www.baidu.com")
                    .create();

            // 设置文本可滑动
//            dialogRegisterNote.getContentView().setMovementMethod(ScrollingMovementMethod.getInstance());

            // 消息对话框
            new MessageDialog.Builder(this)
                    .setTitle(getString(R.string.register_note_title))
                    // 内容必须要填写
                    .setMessage(content)
                    // 确定按钮文本
                    .setConfirm(getString(R.string.common_confirm))
                    // 设置 null 表示不显示取消按钮
                    .setCancel(getString(R.string.common_cancel))
                    // 设置点击按钮后不关闭对话框
                    //.setAutoDismiss(false)
                    .setListener(new MessageDialog.OnListener() {

                        @Override
                        public void onConfirm(BaseDialog dialog) {
                            toast("确定了");
                            // 跳转
                            if (true) {
                                toast(R.string.register_succeed);
                                setResult(RESULT_OK, new Intent()
                                        .putExtra(IntentKey.PHONE, mPhoneView.getText().toString())
                                        .putExtra(IntentKey.PASSWORD, mPasswordView1.getText().toString()));
                                finish();
                                return;
                            }

                            // 提交注册
                            EasyHttp.post(RegisterActivity.this)
                                    .api(new RegisterApi()
                                            .setPhone(mPhoneView.getText().toString())
                                            .setCode(mCodeView.getText().toString())
                                            .setPassword(mPasswordView1.getText().toString()))
                                    .request(new HttpCallback<HttpData<RegisterBean>>(RegisterActivity.this) {

                                        @Override
                                        public void onSucceed(HttpData<RegisterBean> data) {
                                            toast(R.string.register_succeed);
                                            setResult(RESULT_OK, new Intent()
                                                    .putExtra(IntentKey.PHONE, mPhoneView.getText().toString())
                                                    .putExtra(IntentKey.PASSWORD, mPasswordView1.getText().toString()));
                                            finish();
                                        }
                                    });
                        }

                        @Override
                        public void onCancel(BaseDialog dialog) {
                            toast("取消了");
                        }
                    })
                    .show();
        }

    }


    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 不要把整个布局顶上去
                .keyboardEnable(true);
    }

    @Override
    public boolean isSwipeEnable() {
        return false;
    }
}
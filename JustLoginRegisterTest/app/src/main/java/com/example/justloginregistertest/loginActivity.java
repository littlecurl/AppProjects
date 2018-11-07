package com.example.justloginregistertest;
/**
 * 纯粹实现登录注册功能，其它功能都被注释掉了
 * 起作用的代码（连带着packag、import算上） 共 73 行
 * 不多吧？
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by littlecurl 2018/6/24
 */
public class loginActivity extends AppCompatActivity {
    /**
     * 声明自己写的 DBOpenHelper 对象
     *  DBOpenHelper(extends SQLiteOpenHelper) 主要用来
     * 创建数据表
     * 然后再进行数据表的增、删、改、查操作
     */
    private DBOpenHelper mDBOpenHelper;
    /**
     *创建 Activity 时先来重写 onCreate() 方法
     * 保存实例状态
     * super.onCreate(savedInstanceState);
     * 设置视图内容的配置文件
     * setContentView(R.layout.activity_login);
     * 上面这行代码真正实现了把视图层 View 也就是 layout 的内容放到 Activity 中进行显示
     * 将开源库ButterKnife应用到此上下文，这个开源库的规定用法
     * ButterKnife.bind(this);
     * 实例化 DBOpenHelper，待会进行登录验证的时候要用来进行数据查询
     * mDBOpenHelper = new DBOpenHelper(this);
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mDBOpenHelper = new DBOpenHelper(this);
    }
    /**
     * onCreae()中大的布局已经摆放好了，接下来就该把layout里的东西
     * 声明、实例化对象然后有行为的赋予其行为
     * 这样就可以把视图层View也就是layout 与 控制层 Java 结合起来了
     * <p>
     * 这里使用注解的方式注入View,可以不再写findViewById了
     * 我认为这样的方式比以前的方式好理解
     * 比如说，以前声明并实例化一个 Button 是这样的代码：
     * Button button1 = (Button)findViewById(R.id.button1);
     * <p>
     * 用注解的方式是这样的代码（两行代码）：
     * @BindView(R.id.button1)
     * Button button1;
     * <p>
     * 这个Button的例子代码有点短，还不能说明问题，
     * 我直接上个长代码吧：
     * ImageView mIvLoginactivityBack = (ImageView)findViewById(R.id.iv_loginactivity_back);
     * <p>
     * 这样的长代码看的很费劲，可以用注解的方式等价代换成短代码的格式（两行代码）：
     * @BindView(R.id.iv_loginactivity_back)
     * ImageView mIvLoginactivityBack;
     * <p>
     * 直接按着句子读就能理解了
     * 绑定视图（资源id号是iv_loginactivity_back）
     * 实例化ImageView 为 mIvLoginactivityBack
     * <p>
     * 成功声明并实例化一个对象，是不是注解的方式更简单易懂明了？
     * 是不是？是不是？
     * 当然，这么好的方法不是让你说用就用的
     * 你需要拥抱一下开源库，阅读这两篇文章：
     * https://www.sogou.com/link?url=DOb0bgH2eKh1ibpaMGjuy6YnbQPc3cuKC5mI2Qw8RgpomuBEhdSpHkUUZED5fr2Oc1DxBW0w__gl8OT6rYpPtg..
     * https://github.com/JakeWharton/butterknife
     * 读完你就会用了
     * 怎么样？和我一起拥抱开源吧。
     * 接下来敲代码
     */
//   @BindView(R.id.iv_loginactivity_back)
//   ImageView mIvLoginactivityBack;
    @BindView(R.id.tv_loginactivity_register)
    TextView mTvLoginactivityRegister;
    @BindView(R.id.rl_loginactivity_top)
    RelativeLayout mRlLoginactivityTop;
    @BindView(R.id.et_loginactivity_username)
    EditText mEtLoginactivityUsername;
    @BindView(R.id.et_loginactivity_password)
    EditText mEtLoginactivityPassword;
    @BindView(R.id.ll_loginactivity_two)
    LinearLayout mLlLoginactivityTwo;
//    @BindView(R.id.tv_loginactivity_forget)
//    TextView mTvLoginactivityForget;
//    @BindView(R.id.tv_loginactivity_check)
//    TextView mTvLoginactivityCheck;
//    @BindView(R.id.tv_loginactivity_else)
//    TextView mBtLoginactivityElse;
    /**
     * 都声明并实例化之后
     * 需要为实例化的这些对象设置行为
     * 在写 activity_login.xml 的时候
     * 有些控件 Button、ImageView、TextView 都有一个属性：
     * android:onClick="onClick"
     * 其实这也是开源库ButterKnife中的东西
     * 人家不仅解决了findViewById代码过长的问题，
     * 还解决了setOnClickListener(new View.OnClickListener() {}代码过长的问题
     * 让你不再写findViewById、setOnClickListener
     * 所以人家叫 ButterKnife 黄油刀
     * 就是专门管切割长代码的
     * Talk is cheap, show me your code.
     */
    @OnClick({
           // R.id.iv_loginactivity_back,
            R.id.tv_loginactivity_register,
            //R.id.tv_loginactivity_forget,
            //R.id.tv_loginactivity_check,
            R.id.bt_loginactivity_login,
            //R.id.tv_loginactivity_else
    })

    public void onClick(View view) {
        switch (view.getId()) {
            //case R.id.iv_loginactivity_back:
                //TODO 返回箭头功能
            //    finish();//销毁此Activity
            //    break;
            case R.id.tv_loginactivity_register:
                //TODO 注册界面跳转
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            //case R.id.tv_loginactivity_forget:   //忘记密码
                //TODO 忘记密码操作界面跳转
            //    startActivity(new Intent(this, FindPasswordActivity.class));
            //    break;
            //case R.id.tv_loginactivity_check:    //短信验证码登录
                // TODO 短信验证码登录界面跳转
            //    startActivity(new Intent(this, MessageLoginActivity.class));
            //    finish();
            //    break;
            /**
             * 登录验证：
             *
             * 从EditText的对象上获取文本编辑框输入的数据，并把左右两边的空格去掉
             *  String name = mEtLoginactivityUsername.getText().toString().trim();
             *  String password = mEtLoginactivityPassword.getText().toString().trim();
             *  进行匹配验证,先判断一下用户名密码是否为空，
             *  if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password))
             *  再进而for循环判断是否与数据库中的数据相匹配
             *  if (name.equals(user.getName()) && password.equals(user.getPassword()))
             *  一旦匹配，立即将match = true；break；
             *  否则 一直匹配到结束 match = false；
             *
             *  登录成功之后，进行页面跳转：
             *
             *  Intent intent = new Intent(this, MainActivity.class);
             *  startActivity(intent);
             *  finish();//销毁此Activity
             */
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    for(int i=0;i<data.size();i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())){
                            match = true;
                            break;
                        }else{
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();//销毁此Activity
                    }else {
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
            //case R.id.tv_loginactivity_else:
                //TODO 第三方登录，时间有限，未实现
            //    break;
        }
    }
}




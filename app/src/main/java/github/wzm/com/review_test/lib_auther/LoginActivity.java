package github.wzm.com.review_test.lib_auther;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;
import github.wzm.com.review_test.EmptyActivity;
import github.wzm.com.review_test.Main2Activity;
import github.wzm.com.review_test.MapActivity;
import github.wzm.com.review_test.R;

/**
 * 登录界面，分享
 */
public class LoginActivity extends Activity implements View.OnClickListener {
    public static final String TAG = LoginActivity.class.getSimpleName();
    private TextView mViewById;
    private Button mLoginButton;
    private Button mRegister_btn;
    private ImageView mImg_iv;
    private Button mImg_btn;
    private TextInputEditText login_name_et;
    private TextInputEditText login_pwd_et;
    private Button map_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_name_et =   (TextInputEditText)findViewById(R.id.login_name_et);
        login_pwd_et =   (TextInputEditText)findViewById(R.id.login_pwd_et);
        mViewById = (TextView) findViewById(R.id.three_tv);
        mLoginButton = (Button) findViewById(R.id.login_btn);
        mRegister_btn = (Button) findViewById(R.id.register_btn);
        mImg_iv = (ImageView) findViewById(R.id.img_iv);
        mImg_btn = (Button) findViewById(R.id.img_btn);
        map_btn = (Button) findViewById(R.id.map_btn);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            Log.i(TAG, "onCreate:versionCode "+versionCode);
            Log.i(TAG, "onCreate:login_name_et "+login_name_et.getText());
            Log.i(TAG, "onCreate:login_pwd_et "+login_pwd_et);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        mLoginButton.setOnClickListener(this);

        mRegister_btn.setOnClickListener(this);
        mViewById.setOnClickListener(this);

        mImg_btn.setOnClickListener(this);

        map_btn.setOnClickListener(this);
     /*   mImg_iv.setImageResource(R.drawable.anim_list);//设置帧动画
        AnimationDrawable mdrawable = (AnimationDrawable) mImg_iv.getDrawable();//获取帧动画源
        mdrawable.start();
*/

      /*  sendCode(this);//短信注册界面
        Log.i(TAG, "onCreate: ");
        //  addAllO(null);
        addAllS(null);*/

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_btn://登录
                Intent intent1 = new Intent(LoginActivity.this, EmptyActivity.class);
                startActivity(intent1);
                break;
            case R.id.register_btn://注册
                Intent intent2 = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent2);
                break;
            case R.id.three_tv:
                showShare();//分享功能
                break;
            case R.id.img_btn:
                //开启选择动画界面
                Intent intent4 = new Intent(LoginActivity.this, AnimDrawableActivity.class);
                startActivity(intent4);
                break;
            case R.id.map_btn://去地图页
                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }










    public void addAllO(Object a) {
        Log.i(TAG, "onCreate: addAllO ");
    }

    public void addAllS(String a) {
        Log.i(TAG, "onCreate: addAllS ");
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(4));
        Future<?> submit = threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {

            }
        });
        try {
            Object o = submit.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        threadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("LoginActivity");
        MobclickAgent.onResume(this);
        Log.i(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("LoginActivity");
        MobclickAgent.onPause(this);
        Log.i(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: ");
    }

    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl QQ和QQ空间跳转链接,分享内容里附带的超链接
        //   oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //  oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("我是测试评论文本");
        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 以可视化界面完成操作
     *
     * @param context
     */
    public void sendCode(Context context) {
        RegisterPage page = new RegisterPage();
        page.setRegisterCallback(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 处理成功的结果
                    HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country"); // 国家代码，如“86”
                    String phone = (String) phoneMap.get("phone"); // 手机号码，如“13800138000”
                    // TODO 利用国家代码和手机号码进行后续的操作
                } else {
                    // TODO 处理错误的结果
                }
            }
        });
        page.show(context);
    }

    /**
     * 以无界面接口完成操作
     *
     * @param country
     * @param phone
     */
    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                } else {
                    // TODO 处理错误的结果
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                } else {
                    // TODO 处理错误的结果
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    protected void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
        Log.i(TAG, "onDestroy: ");
    }


}

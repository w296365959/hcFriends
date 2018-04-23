package github.wzm.com.review_test;

import android.app.Application;
import android.util.Log;

import com.mob.MobSDK;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/1 16:27 </br>
 * Summary:
 */

public class MyApplication extends Application {
    public static final  String TAG=MyApplication.class.getSimpleName();
    int leave=0;
    @Override
    public void onCreate() {
        super.onCreate();
        //shareSDK
        MobSDK.init(this);
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:友盟 AppKey
         * 参数3:友盟 Channel  渠道
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         * 注意：

         参数1:上下文，必须的参数，不能为空。

         参数2:友盟 AppKey，非必须参数，如果Manifest文件中已配置AppKey，该参数可以传空，则使用Manifest中配置的AppKey，否则该参数必须传入。

         参数3:友盟 Channel，非必须参数，如果Manifest文件中已配置Channel，该参数可以传空，则使用Manifest中配置的Channel，否则该参数必须传入，Channel命名请详见Channel渠道命名规范。

         参数4:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机。

         参数5:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空。
         */

        //友盟统计
        UMConfigure.init(this,"1fe6a20054bcef865eeb0991ee84525b","Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.setLogEnabled(true);//显示友盟日志
        MobclickAgent.openActivityDurationTrack(false); //禁止默认的页面统计方式，这样将不会再自动统计Activity。
        MobclickAgent.setDebugMode( true );//打开友盟测试模式（集成测试（需要注册测试设备）或普通测试）
        leave++;
        Log.i(TAG, "onCreate: 走次"+leave);
        Log.i(TAG, "onCreate: 哈希值"+MyApplication.this.hashCode());
    }
}

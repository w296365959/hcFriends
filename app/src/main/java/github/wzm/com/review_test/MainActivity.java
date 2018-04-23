package github.wzm.com.review_test;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;

import github.wzm.com.review_test.annotation.MyAnnotation;
import github.wzm.com.review_test.audioRecord.ActionListener;
import github.wzm.com.review_test.audioRecord.MyAudioRecord;
import github.wzm.com.review_test.lib_auther.LoginActivity;

public class MainActivity extends AppCompatActivity implements ActionListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    @MyAnnotation(name = "小明")
    private String you;
/*    @MyAnnotation(getXX = MyAnnotation.Addressz.Address1)
    private String h;*/
    private MyButton mBtn;
    private Button mLogin_activity_btn;

    public String getYou() {
        return you;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: ");
        TextView tv = (TextView) findViewById(R.id.main_name_tv);
        mBtn = (MyButton) findViewById(R.id.button);
        mLogin_activity_btn = (Button) findViewById(R.id.login_activity_btn);
        tv.setText("第一个");

        mLogin_activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        mBtn.setOnActionListener(this);

        getInfo(this);
        Log.i(TAG, "onCreate:== " + getYou());
       // Log.i(TAG, "onCreate:== " + h);
    }

    private static void getInfo(Object object) {
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(MyAnnotation.class)) {//判断该程序元素上面是否有指定类型的注解
                MyAnnotation annotation = declaredField.getAnnotation(MyAnnotation.class);//返回该程序元素上指定类型的注解,没有则返回null

                declaredField.setAccessible(true);//反射获取类的私有变量时需要调用这个
                try {
                    declaredField.set(object, annotation.name());//给我们要找的字段设置值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            //    Log.i(TAG, "getInfo: " + annotation.getXX());
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2101) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "onRequestPermissionsResult: 申请成功");
                new MyAudioRecord().getNoiseLevel(); //打印分贝值
            } else {
                Log.i(TAG, "onRequestPermissionsResult: 申请失败");
            }
        } else {
            Log.i(TAG, "onRequestPermissionsResult: 不是这个申请权限");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.i(TAG, "onSaveInstanceState: ");
    }


    @Override
    public void setOnActionDown() {
        System.out.println("打印");
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {//没有申请权限，这执行申请权限
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    2101);
        } else {//已近申请了 可以进程后续操作
            Log.i(TAG, "onClick: 申请过了");
            if (mMyAudioRecord == null) {
                mMyAudioRecord = new MyAudioRecord(); //打印分贝值
                mMyAudioRecord.getNoiseLevel();
            }
        }
    }

    private MyAudioRecord mMyAudioRecord;

    @Override
    public void setOnActionUp() {
        Log.i(TAG, "setOnActionUp: ");
        mMyAudioRecord.stop();
        mMyAudioRecord = null;
    }
}

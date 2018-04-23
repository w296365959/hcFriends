package github.wzm.com.review_test.lib_auther;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import github.wzm.com.review_test.R;

import static github.wzm.com.review_test.R.id.imageView2;
import static github.wzm.com.review_test.R.id.imageView_iv;

/**
 * 选择动画界面
 * 四种动画 帧，补间，属性。过渡 动画
 */
public class AnimDrawableActivity extends AppCompatActivity {
    public static final String TAG = AnimDrawableActivity.class.getSimpleName();
    @Bind(R.id.frameAnimation_tv)
    TextView frameAnimationTv;
    @Bind(R.id.TweenAnimation_tv)
    TextView TweenAnimationTv;
    @Bind(R.id.propertyAnimation_tv)
    TextView propertyAnimationTv;
    @Bind(R.id.transitionAnimation_tv)
    TextView transitionAnimationTv;
    @Bind(R.id.imageView_iv)
    ImageView imageViewIv;
    private AnimationDrawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_drawable);
        ButterKnife.bind(this);

    }

    private boolean flag = true;

    @OnClick({R.id.frameAnimation_tv, R.id.TweenAnimation_tv, R.id.propertyAnimation_tv, R.id.transitionAnimation_tv})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.frameAnimation_tv://帧动画

                imageViewIv.setImageResource(R.drawable.anim_list);
                mDrawable = (AnimationDrawable) imageViewIv.getDrawable();
                if (flag) {
                    Log.i(TAG, "onViewClicked: 开启帧动画");
                    mDrawable.start();//开启帧动画
                    flag = !flag;
                } else {
                    Log.i(TAG, "onViewClicked: 关闭帧动画");
                    mDrawable.stop();
                    flag = !flag;
                }

                break;
            case R.id.TweenAnimation_tv://补间动画,旋转缩放位移透明度
                tweenAnimation();
                break;
            case R.id.propertyAnimation_tv://属性动画
                break;
            case R.id.transitionAnimation_tv://过渡动画
                break;
        }
    }

    /**
     * 属性动画
     */
    private void tweenAnimation() {
        imageViewIv.setImageResource(R.mipmap.ic_launcher);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.ainm_list2);
        imageViewIv.startAnimation(animation);
    }
}

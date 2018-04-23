package github.wzm.com.review_test.lib_auther;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import github.wzm.com.review_test.R;

/**
 * 用于播放动画
 */
public class ImgActivity extends AppCompatActivity {

    @Bind(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        ButterKnife.bind(this);
        imageView2.setImageResource(R.drawable.anim_list);
        AnimationDrawable drawable = (AnimationDrawable) imageView2.getDrawable();
        drawable.start();//开启帧动画

    }

}

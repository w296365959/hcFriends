package github.wzm.com.review_test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import github.wzm.com.review_test.audioRecord.ActionListener;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/5 16:55 </br>
 * Summary:
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {
    public static final String TAG=MyButton.class.getSimpleName();
    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent: ");
                mActionListener.setOnActionDown();
                break;
            case MotionEvent.ACTION_MOVE:
          //      Log.i(TAG, "onTouchEvent: ");
                break;
            case MotionEvent.ACTION_UP:
                Log.i(TAG, "onTouchEvent: ");
                mActionListener.setOnActionUp();
                break;
            default:
                Log.i(TAG, "onTouchEvent: ");
                break;
        }
        return true;
    }


    private ActionListener mActionListener;

    public void setOnActionListener(ActionListener onActionListener){
        this.mActionListener=onActionListener;
    }

}

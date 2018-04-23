package github.wzm.com.review_test.audioRecord;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/5 15:39 </br>
 * Summary:
 */

public class MyAudioRecord {
    private static final String TAG = MyAudioRecord.class.getSimpleName();
    static final int SAMPLE_RATE_IN_HZ = 8000;//音频的采样频率
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ,
            AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
    AudioRecord mAudioRecord;
    boolean isGetVoiceRun;
    Object mLock;
    Context mContext;

    public MyAudioRecord() {
        mLock = new Object();
    }

    public void stop() {
        isGetVoiceRun=false;//停止录音

        if (mAudioRecord != null) {
            Log.i(TAG, "stop: 释放");
            try {
                mAudioRecord.stop();
                mAudioRecord.release();
                mAudioRecord = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Log.i(TAG, "stop:  null");
        }
    }

    public void getNoiseLevel() {
        if (isGetVoiceRun) {
            Log.e(TAG, "还在录着呢");
            return;
        }
        if (mAudioRecord != null) {
            Log.i(TAG, "getNoiseLevel: ！=null");
        }
        try {
            if (mAudioRecord == null) {
                mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
                        SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE);
                Log.i(TAG, "getNoiseLevel:  初始化完成");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mAudioRecord == null) {
            Log.e("sound", "mAudioRecord初始化失败");
        }
        isGetVoiceRun = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "run: 1");
                mAudioRecord.startRecording();
                Log.i(TAG, "run: 2");
                short[] buffer = new short[BUFFER_SIZE];
                while (isGetVoiceRun) {
                    Log.i(TAG, "run: 3");

                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = mAudioRecord.read(buffer, 0, BUFFER_SIZE);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < buffer.length; i++) {
                        v += buffer[i] * buffer[i];
                    }
                    Log.i(TAG, "run: 4");
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);
                    Log.d(TAG, "分贝值:" + volume);
                    // 大概一秒十次
                    synchronized (mLock) {
                        try {
                            mLock.wait(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }).start();
    }
}

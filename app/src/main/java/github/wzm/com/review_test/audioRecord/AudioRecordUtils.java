package github.wzm.com.review_test.audioRecord;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Okline(Hangzhou)co,Ltd<br/>
 * Author: wangzhongming<br/>
 * Email:  wangzhongming@okline.cn</br>
 * Date :  2018/3/5 15:56 </br>
 * Summary:
 */

public class AudioRecordUtils {
   /* private final int audioSource = MediaRecorder.AudioSource.MIC;
    // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050,16000,11025
    private final int sampleRateInHz = 16000;
    // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
    private final int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
    // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
    private final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private int inBufSize = 0;

    private AudioRecord audioRecord;

    private AACEncoder encoder = null;

    private ProgressDialog mProgressDialog = null;

    private boolean isRecord = false;

    private Context mContext;
    *//**
     * 录制的音频文件名称
     *//*
    private String mAudioRecordFileName;

    private static final int RECORDED_INIT_DELETE = 0;

    private static final int RECORDED_COMPLETED_DELETE = 1;

    private Handler mHandler;

    *//**
     * 是否可以录音 true 可以录音
     *//*
    private boolean recordEnable = false;

    public AudioRecordUtils(Context context,String audioRecordFileName, Handler handler){
        mContext = context;
        mAudioRecordFileName = audioRecordFileName;
        mHandler = handler;
        initAudioRecord();
    }

    *//**
     * 初始化对象
     *//*
    private void initAudioRecord(){

        inBufSize = AudioRecord.getMinBufferSize(
                sampleRateInHz,
                channelConfig,
                audioFormat);

        audioRecord  = new AudioRecord(
                audioSource,
                sampleRateInHz,
                channelConfig,
                audioFormat,
                inBufSize);

        encoder = new AACEncoder();
        deleteAllFiles(RECORDED_INIT_DELETE);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setTitle("提示");
        mProgressDialog.setMessage("正在保存录音，请耐心等候......");

    }

    *//**
     * 开始录音
     *//*
    public void startRecord(){
        new AudioRecordTask().execute();
    }

    *//**
     * 暂停录音
     *//*
    public void pauseRecord(){
        isRecord = false;
    }

    *//**
     * 停止录音
     *//*
    public void stopRecord(){
        new AudioEncoderTask().execute();
    }

    *//**
     * 重新录制
     *//*
    public void reRecord(){
        //重新录制时，删除录音文件夹中的全部文件
        deleteAllFiles(RECORDED_INIT_DELETE);
    }

    private void encodeAudio(){
        try {
            //读取录制的pcm音频文件
            DataInputStream mDataInputStream = new DataInputStream(new FileInputStream(
                    FileUtils.getPcmFilePath(mAudioRecordFileName)));
            byte[] b = new byte[(int) new File(FileUtils.
                    getPcmFilePath(mAudioRecordFileName)).length()];
            mDataInputStream.read(b);
            //初始化编码配置
            encoder.init(32000, 2, sampleRateInHz, 16, FileUtils.
                    getAAcFilePath(mAudioRecordFileName));
            //对二进制代码进行编码
            encoder.encode(b);
            //编码完成
            encoder.uninit();
            //关闭流
            mDataInputStream.close();
            try {
                //将aac文件转码成m4a文件
                new AACToM4A().convert(mContext, FileUtils.getAAcFilePath(mAudioRecordFileName),
                        FileUtils.getM4aFilePath(mAudioRecordFileName));
            } catch (IOException e) {
                Log.e("ERROR", "error converting", e);
            }
            deleteAllFiles(RECORDED_COMPLETED_DELETE);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    class AudioRecordTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            if(audioRecord == null){
                initAudioRecord();
            }
            RandomAccessFile mRandomAccessFile = null;
            try {
                mRandomAccessFile = new RandomAccessFile(new File(
                        FileUtils.getPcmFilePath(mAudioRecordFileName)), "rw");
                byte[] b = new byte[inBufSize/4];
                //开始录制音频
                try{
                    // 防止某些手机崩溃，例如联想
                    audioRecord.startRecording();
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }

                //判断是否正在录制
                isRecord = true;
                long wait = 0;
                long maxWait = 10;
                while(isRecord){
                    //r是实际读取的数据长度，一般而言r会小于buffersize
                    int r = audioRecord.read(b, 0, b.length);
                    long v = 0;
                    // 将 buffer 内容取出，进行平方和运算
                    for (int i = 0; i < b.length; i++) {
                        v += b[i] * b[i];
                    }
                    // 平方和除以数据总长度，得到音量大小。
                    double mean = v / (double) r;
                    double volume = 10 * Math.log10(mean);

                    wait++;
                    if(wait > maxWait){
                        wait = 0;
                        Log.d(this.getClass().getName(), "分贝值:" + volume + " " + (volume > 0));
                        if(volume > 0){
                            recordEnable = true;
                        }
                        Message msg = new Message();
                        msg.what = AnswerActivity.STATUS_PREPARE;
                        Bundle bundle = new Bundle();
                        bundle.putDouble(Device.VOICE_AMPLITUDE, volume);
                        msg.obj = bundle;
                        mHandler.sendMessage(msg);
                    }

                    //向文件中追加内容
                    mRandomAccessFile.seek(mRandomAccessFile.length());
                    mRandomAccessFile.write(b, 0, b.length);
                }
                //停止录制
                try {
                    // 防止某些手机崩溃，例如联想
                    audioRecord.stop();
                    // 彻底释放资源
                    audioRecord.release();
                    audioRecord = null;
                }catch (IllegalStateException e){
                    e.printStackTrace();
                }
                mRandomAccessFile.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }

    class AudioEncoderTask extends AsyncTask<Void, Void, Long>{

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            if(mProgressDialog != null && !mProgressDialog.isShowing()){
//				mProgressDialog.show();
                Message msg = new Message();
                msg.what = 2;
                mHandler.sendMessage(msg);
            }
        }

        @Override
        protected Long doInBackground(Void... params) {
            // TODO Auto-generated method stub
            encodeAudio();
            return null;
        }

        @Override
        protected void onPostExecute(Long result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(mProgressDialog.isShowing()){
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
            }
        }
    }

    *//**
     * 清空音频录制文件夹中的所有文件
     * @param isRecorded
     *//*
    public void deleteAllFiles(int isRecorded){
        File[] files = new File(FileUtils.getAudioRecordFilePath()).listFiles();
        switch (isRecorded) {
            case RECORDED_INIT_DELETE:
                for(File file: files){
                    file.delete();
                }
                break;
            case RECORDED_COMPLETED_DELETE:
                for(File file: files){
                    if(!file.getName().equals(mAudioRecordFileName + Constants.M4A_SUFFIX)){
                        file.delete();
                    }
                }
                break;
            default:
                break;
        }
    }

    public boolean isRecordEnable() {
        return recordEnable;
    }*/
}

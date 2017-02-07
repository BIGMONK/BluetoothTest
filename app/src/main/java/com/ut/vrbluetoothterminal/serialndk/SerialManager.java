package com.ut.vrbluetoothterminal.serialndk;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description:
 * Author：Giousa
 * Date：2016/8/15
 * Email：giousa@chinayoutu.com
 */
public class SerialManager {

    private final String TAG = SerialManager.class.getSimpleName();
    private static SerialManager mSerialManager;
    private final Timer timer = new Timer();
    private TimerTask task;

    public static SerialManager getInstance() {
        Log.d("SerialManager", "getInstance");
        if (mSerialManager == null) {
            mSerialManager = new SerialManager();
        }

        return mSerialManager;

    }

    public interface SerialSpeedChangeListener {
        void onSerialSpeedChanged(short speed);
    }

    public interface SerialAngleChangeListener {
        void onSerialAngleChanged(float angle);
    }

    private SerialSpeedChangeListener mSerialSpeedChangeListener;
    private SerialAngleChangeListener mSerialAngleChangeListener;

    public void setSerialSpeedChangeListener(SerialSpeedChangeListener serialSpeedChangeListener) {
        mSerialSpeedChangeListener = serialSpeedChangeListener;
    }

    public void setSerialAngleChangeListener(SerialAngleChangeListener serialAngleChangeListener) {
        mSerialAngleChangeListener = serialAngleChangeListener;
    }


//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            // TODO Auto-generated method stub
//            // 要做的事情
//            readSerial();
//            super.handleMessage(msg);
//        }
//    };

    private Handler mHandler = new Handler();
    private Runnable mTimerRunnable = new Runnable() {
        @Override
        public void run() {
            readSerial();
            mHandler.postDelayed(mTimerRunnable, 50);
        }
    };

    public void openSerial() {
        Log.d(TAG, "openSerial");
        Serial.OpenSerial(4);
        setSerialBaud(555);

//        task = new TimerTask() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                Message message = new Message();
//                message.what = 1;
//                handler.sendMessage(message);
//            }
//        };
//
//        timer.schedule(task, 50, 100);
        mHandler.postDelayed(mTimerRunnable, 0);
    }

    public void closeSerial() {
        mHandler.removeCallbacks(mTimerRunnable);
        Serial.CloseSerial();

    }

    public void setSerialBaud(long baud) {
        Serial.SetSerialBaud(baud);
    }

    public void readSerial() {

        short[] mShorts = new short[9];

        int serialBuf = Serial.ReadSerialBuf(mShorts, 9);
//        Log.d(TAG, "serialBuf:" + serialBuf);

        if(serialBuf == 9){
//            if(mShorts[0] == 0x55 && mShorts[1] == 0x01 && mShorts[8] == 0xAA){
//                parseSerialData(mShorts);
//            }
            parseSerialData(mShorts);
        }
    }

    public short mSpeed = 0;
    public float mAngle = 15;

    private void parseSerialData(short[] mShorts) {

        short speed = mShorts[2];
        speed = (short) ((Math.round(speed*100))/100);
//        Log.d(TAG,"parseSerialData speed="+speed);
        if (mSpeed != speed) {
            mSpeed = speed;
            if(mSerialSpeedChangeListener != null){
                mSerialSpeedChangeListener.onSerialSpeedChanged(mSpeed);
            }
        }

        float angle = mShorts[3];
        if(mAngle != angle){
            mAngle = angle;
            if(mSerialAngleChangeListener != null){
                mSerialAngleChangeListener.onSerialAngleChanged(mAngle);
            }
        }

    }

    public void writeSerial(short[] WriteBuf) {
        Log.d(TAG,"writeSerial:"+WriteBuf[2]+"     length:"+WriteBuf.length);
        Serial.WriteSerialBuf(WriteBuf, WriteBuf.length);
    }
}
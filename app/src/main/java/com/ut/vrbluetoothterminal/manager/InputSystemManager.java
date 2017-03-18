package com.ut.vrbluetoothterminal.manager;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;

import com.ut.vrbluetoothterminal.bluetooth.BleDeviceBean;
import com.ut.vrbluetoothterminal.bluetooth.BlueToothLeManager;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liuenbao on 1/23/16.
 */
public class InputSystemManager extends GestureDetector.SimpleOnGestureListener
        implements   BlueToothLeManager.BlueToothConnectStateChangedListener, BlueToothLeManager.DataValuesChangedListener {

    private static final String TAG = InputSystemManager.class.getSimpleName();

    private static InputSystemManager instance;

    public static final int EMPTY = 0;
    public static final int LEFT = 1;
    public static final int UP = 2;
    public static final int RIGHT = 3;
    public static final int DOWN = 4;
    public static final int ENTER = 5;
    public static final int BACK = 6;

    private Context mContext;

    private WifiStateManager mWifiStateManager;
    private BlueToothLeManager mBlueToothLeManager;
    private List<BlueToothDataValuesChangedListener>  mBlueToothDataValuesChangedListeners;

    //An array of observers


    private InputSystemManager() {
        mBlueToothDataValuesChangedListeners= new LinkedList<>();
    }

    public static InputSystemManager getInstance() {
        if (instance == null) {
            instance = new InputSystemManager();
        }

        return instance;
    }




    @Override
    public void onBlueToothConnectState(String add,int state) {
        mBlueToothConnectStateEvevtListener.onBlueToothConnectStateChanged(add,state);
    }

    //Input System Public Interface begin
    //若在某个Activity里面监听该事件需要继承这些listener

    private BlueToothConnectStateEvevtListener mBlueToothConnectStateEvevtListener;

    @Override
    public void onDataValuesChanged(String add, byte[] values) {
//        mBlueToothDataValuesChangedListener.onBlueToothDataValuesChanged(add,values);
        if (mBlueToothDataValuesChangedListeners != null) {
            for (BlueToothDataValuesChangedListener listener : mBlueToothDataValuesChangedListeners) {
                listener.onBlueToothDataValuesChanged(add,values);
            }
        }
    }


    public interface BlueToothConnectStateEvevtListener {
        void onBlueToothConnectStateChanged(String add,int state);
    }

    public void setBlueToothConnectStateEvevtListener(BlueToothConnectStateEvevtListener listener) {
        mBlueToothConnectStateEvevtListener = listener;
    }

    public interface BlueToothDataValuesChangedListener {
        void onBlueToothDataValuesChanged(String add,byte[] values);
    }

    private BlueToothDataValuesChangedListener mBlueToothDataValuesChangedListener;

//    public void setBlueToothDataValuesChangedListener(BlueToothDataValuesChangedListener listener) {
//        mBlueToothDataValuesChangedListener = listener;
//    }
    public void registerBlueToothDataValuesChangedListener(BlueToothDataValuesChangedListener listener){
        mBlueToothDataValuesChangedListeners.add(listener);
    }
    public void unRegisterBlueToothDataValuesChangedListener(BlueToothDataValuesChangedListener listener){
        mBlueToothDataValuesChangedListeners.remove(listener);
    }


    public interface WifiStateSystemEventListener {
        void onWifiStateChanged(InputSystemManager inputSystemManager, boolean isWifiActive);
    }


    //Input System Public Interface end

    //Android Origin Event input begin

    //注意，此处的Context一定是Activity的context

    public boolean initWithContext(Context context,HashMap<String, BleDeviceBean> devicesMap) {

        //这里的context只能是Activity的context
        mContext = context;

        if (mBlueToothLeManager == null)
            mBlueToothLeManager = new BlueToothLeManager(context);
//        设置监听事件
        mBlueToothLeManager.setBlueToothConnectStateChangedListener(this);
        mBlueToothLeManager.setDataValuesChangedListener(this);
        mBlueToothLeManager.initBlueToothInfo(devicesMap);
        return true;
    }

    public boolean reConnectBlueTooth(String add) {
        Log.d(TAG,"reConnectBlueTooth    "+add);
        return mBlueToothLeManager.reConnectDevice(add);
    }

    public void sendData(String add,byte[] data) {
        if (mBlueToothLeManager != null) {
            mBlueToothLeManager.sendData(add,data);
        }
    }

    public void sendData(String add,String data) {
        if (mBlueToothLeManager != null) {
            mBlueToothLeManager.sendData(add,data);
        }
    }

    public boolean setCharacteristicNotification(String add) {
        return mBlueToothLeManager.setCharacteristicNotification(add);
    }

    public void disconnectDevice(String add) {
        if (mBlueToothLeManager != null) {
            mBlueToothLeManager.disconnectDevice(add);
            Log.d(TAG, "disconnectDevice  "+add);
        }
    }

}

package com.ut.vrbluetoothterminal.manager;

import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import android.view.GestureDetector;


import com.ut.vrbluetoothterminal.bluetooth.BlueToothLeManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by liuenbao on 1/23/16.
 */
public class InputSystemManager extends GestureDetector.SimpleOnGestureListener
        implements WifiStateManager.WifiStateChangedListener, BlueToothLeManager.SpeedChangedListener,
        BlueToothLeManager.AngleChangedListener, BlueToothLeManager.HeartBeatChangedListener, BlueToothLeManager.BlueToothConnectStateChangedListener {

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

    //An array of observers
    private List<SpeedSystemEventListener> mSpeedSystemEventListeners;
    private List<AngleSystemEventListener> mAngleSystemEventListeners;
    private List<HeartBeatSystemEventListener> mHeartBeatSystemEventListeners;
    private List<WifiStateSystemEventListener> mWifiStateSystemEventListeners;

    private InputSystemManager() {
        mAngleSystemEventListeners = new LinkedList<>();
        mSpeedSystemEventListeners = new LinkedList<>();
        mHeartBeatSystemEventListeners = new LinkedList<>();
        mWifiStateSystemEventListeners = new LinkedList<>();
    }

    public static InputSystemManager getInstance() {
        if (instance == null) {
            instance = new InputSystemManager();
        }

        return instance;
    }

    @Override
    public void onWifiStateChanged(WifiStateManager manager, boolean isWifiActive) {
        if (mWifiStateSystemEventListeners != null) {
            for (WifiStateSystemEventListener listener : mWifiStateSystemEventListeners) {
                listener.onWifiStateChanged(this, isWifiActive);
            }
        }
    }

    //蓝牙消息

    @Override
    public void onSpeedChanged(short speed) {
        if (mSpeedSystemEventListeners != null) {
            for (SpeedSystemEventListener listener : mSpeedSystemEventListeners) {
                listener.onSpeedChanged(this, speed);
            }
        }
    }

    @Override
    public void onAngleChanged(float angle) {
        if (mAngleSystemEventListeners != null) {
            for (AngleSystemEventListener listener : mAngleSystemEventListeners) {
                listener.onAngleChanged(this, angle);
            }
        }
    }

    @Override
    public void onHeartBeatChanged(int heartBeat) {
        if (mHeartBeatSystemEventListeners != null) {
            for (HeartBeatSystemEventListener listener : mHeartBeatSystemEventListeners) {
                listener.onHeartBeatChanged(this, heartBeat);
            }
        }
    }

    @Override
    public void onBlueToothConnectState(int state) {
        Log.d(TAG, "onBlueToothConnectState" + state);
        mBlueToothConnectStateEvevtListener.onBlueToothConnectStateChanged(state);
    }

    //Input System Public Interface begin
    //若在某个Activity里面监听该事件需要继承这些listener

    private BlueToothConnectStateEvevtListener mBlueToothConnectStateEvevtListener;

    public interface BlueToothConnectStateEvevtListener {
        void onBlueToothConnectStateChanged(int state);
    }

    public void setBlueToothConnectStateEvevtListener(BlueToothConnectStateEvevtListener listener) {
        mBlueToothConnectStateEvevtListener = listener;
    }

    public interface SpeedSystemEventListener {
        void onSpeedChanged(InputSystemManager inputSystemManager, short speed);
    }

    public interface AngleSystemEventListener {
        void onAngleChanged(InputSystemManager inputSystemManager, float angle);
    }

    public interface HeartBeatSystemEventListener {
        void onHeartBeatChanged(InputSystemManager inputSystemManager, int heartBeat);
    }


    public interface WifiStateSystemEventListener {
        void onWifiStateChanged(InputSystemManager inputSystemManager, boolean isWifiActive);
    }

    //todo 事件的注册，如果某个Activity需要监听该事件需要注册事件
    public void registerHeartBeatSystemEventListener(HeartBeatSystemEventListener listener) {
        mHeartBeatSystemEventListeners.add(listener);
    }


    public void unregisterHeartBeatSystemEventListener(HeartBeatSystemEventListener listener) {
        mHeartBeatSystemEventListeners.remove(listener);
    }

    public void registerAngleSystemEventListener(AngleSystemEventListener listener) {
        mAngleSystemEventListeners.add(listener);
    }

    public void unregisterAngleSystemEventListener(AngleSystemEventListener listener) {
        mAngleSystemEventListeners.remove(listener);
    }

    public void registerSpeedSystemEventListener(SpeedSystemEventListener listener) {
        mSpeedSystemEventListeners.add(listener);
    }

    public void unregisterSpeedSystemEventListener(SpeedSystemEventListener listener) {
        mSpeedSystemEventListeners.remove(listener);
    }

    public void registerWifiStateSystemEventListener(WifiStateSystemEventListener listener) {
        mWifiStateSystemEventListeners.add(listener);
    }

    public void unregisterWifiStateSystemEventListener(WifiStateSystemEventListener listener) {
        mWifiStateSystemEventListeners.remove(listener);
    }

    //Input System Public Interface end

    //Android Origin Event input begin

    //注意，此处的Context一定是Activity的context
    //// TODO: 2016/6/30 0030 初始化蓝牙信息，以及监听事件
    public boolean initWithContext(Context context, String address, String bluetoothName) {

        //这里的context只能是Activity的context
        mContext = context;

//        registerWifiStatusManager();
        if (mBlueToothLeManager == null)
            mBlueToothLeManager = new BlueToothLeManager(context);


//        设置监听事件
        mBlueToothLeManager.setSpeedChangedListener(this);
        mBlueToothLeManager.setHeartBeatChangedListener(this);
        mBlueToothLeManager.setAngleChangedListener(this);
        mBlueToothLeManager.setBlueToothConnectStateChangedListener(this);

        //设置设备的名称，一台设备只能有一个名字
        mBlueToothLeManager.setDeviceName(bluetoothName);
        mBlueToothLeManager.setDeviceAddress(address);
        mBlueToothLeManager.initBlueToothInfo();

        return true;
    }

    public void reConnectBlueTooth() {
        mBlueToothLeManager.reConnectDevice();
    }

    public void sendData(byte[] data) {
        if (mBlueToothLeManager != null) {
            mBlueToothLeManager.sendData(data);
        }
    }

    public void sendData(String data) {
        if (mBlueToothLeManager != null) {
            mBlueToothLeManager.sendData(data);
        }
    }

    public boolean setCharacteristicNotification() {
        return mBlueToothLeManager.setCharacteristicNotification();
    }

    public void disconnectDevice() {
        if (mBlueToothLeManager != null) {
            mBlueToothLeManager.disconnectDevice();
            Log.d(TAG, "disconnectDevice");
        }
    }

    private void registerWifiStatusManager() {
        IntentFilter filter = new IntentFilter("android.net.wifi.RSSI_CHANGGED");
        mWifiStateManager = new WifiStateManager();
        mContext.registerReceiver(mWifiStateManager, filter);

        mWifiStateManager.setWifiStateChangedListener(this);
    }
    //Android Origin Event input end
}

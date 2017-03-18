package com.ut.vrbluetoothterminal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ut.vrbluetoothterminal.bluetooth.BluetoothStatus;
import com.ut.vrbluetoothterminal.manager.InputSystemManager;

import static com.ut.vrbluetoothterminal.bluetooth.BluetoothStatus.STATE_NULL_WRITE_BGC;

public class Main2Activity extends AppCompatActivity implements
        InputSystemManager.BlueToothDataValuesChangedListener, InputSystemManager.BlueToothConnectStateEvevtListener {

    private InputSystemManager mInputSystemManager;
    private boolean isActivityOnTop;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TAG = this.getClass().getSimpleName();
        mInputSystemManager = InputSystemManager.getInstance();
        //监听蓝牙连接状态
        mInputSystemManager.setBlueToothConnectStateEvevtListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        isActivityOnTop = true;
    }

    /**
     * 蓝牙设备状态监听
     *
     * @param state
     */

    @Override
    public void onBlueToothConnectStateChanged(String add, int state) {
        if (isActivityOnTop) {
            switch (state) {
                case BluetoothStatus.STATE_CONNECTED:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_CONNECTED");

                    break;
                case BluetoothStatus.STATE_CONNECTING:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_CONNECTING");

                    break;
                case BluetoothStatus.STATE_SCAN_TIMEOUT:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_SCAN_TIMEOUT");
                    break;
                case BluetoothStatus.STATE_DISCONNECTED:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_DISCONNECTED");
                    break;
                case BluetoothStatus.STATE_NULL_NOTIFY_BGC:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_NULL_NOTIFY_BGC");

                    break;
                case STATE_NULL_WRITE_BGC:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_NULL_WRITE_BGC");

                    break;
                case BluetoothStatus.STATE_SCANING:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_SCANING");

                    break;
                case BluetoothStatus.STATE_NOTIFY_TRUE:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_NOTIFY_TRUE");

                    break;
                case BluetoothStatus.STATE_NOTIFY_FALSE:
                    Log.d(TAG, "onBlueToothConnectStateChanged STATE_NOTIFY_FALSE");

                    break;
            }
        }
    }

    @Override
    public void onBlueToothDataValuesChanged(String add, byte[] values) {

    }
}

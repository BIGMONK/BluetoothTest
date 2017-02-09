package com.ut.vrbluetoothterminal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ut.vrbluetoothterminal.manager.InputSystemManager;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements InputSystemManager.HeartBeatSystemEventListener, View.OnClickListener, InputSystemManager.BlueToothConnectStateEvevtListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private InputSystemManager inputSystemManager;
    private TextView textViewSentData;
    private Random mRandom;
    private Button disConnectButton;
    private Button connectButton;
    private int mBlueToothConnectState;

    private String mDevicesAddress="F4:5E:AB:AF:1D:BF";
//    private String mDevicesAddress="E6:A3:04:CC:50:D5";
    private String mDevicesName="HMSoft";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSentData = (TextView) findViewById(R.id.text_sent_data);
        disConnectButton = (Button) findViewById(R.id.disconnect);
        connectButton = (Button) findViewById(R.id.connect);
        textViewSentData.setOnClickListener(this);
        disConnectButton.setOnClickListener(this);
        connectButton.setOnClickListener(this);
//        Intent intent = new Intent(MainActivity.this, TService.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        this.startService(intent);

        inputSystemManager = InputSystemManager.getInstance();
        inputSystemManager.registerHeartBeatSystemEventListener(this);
        //监听蓝牙连接状态
        inputSystemManager.setBlueToothConnectStateEvevtListener(this);
        inputSystemManager.initWithContext(this,mDevicesAddress, mDevicesName);

    }

    int i = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
                Log.d(TAG,"onClick connect mBlueToothConnectState="+mBlueToothConnectState);
                if (mBlueToothConnectState == 0)
                    //依次根据地址或者名称连接指定的蓝牙设备
                    inputSystemManager.initWithContext(this,mDevicesAddress, mDevicesName);
                break;
            case R.id.disconnect:
                Log.d(TAG,"onClick disconnect mBlueToothConnectState="+mBlueToothConnectState);
                if (mBlueToothConnectState == 2)
                    inputSystemManager.disconnectDevice();
                break;
            case R.id.text_sent_data:
                if (mBlueToothConnectState == 2) {
                    i++;
                    inputSystemManager.sendData("" + i);
                    textViewSentData.setText(i + "");
                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
//        Intent sevice = new Intent(this, TService.class);
//        this.startService(sevice);
        inputSystemManager.unregisterHeartBeatSystemEventListener(this);
        super.onDestroy();
    }

    @Override
    public void onHeartBeatChanged(InputSystemManager inputSystemManager, int heartBeat) {
        Log.d(TAG, "onHeartBeatChanged=" + heartBeat + "");
    }


    @Override
    public void onBlueToothConnectStateChanged(int state) {
        //0 断开  1 正在连接 2 已经连接 -1正在扫描
        Log.d(TAG, "onBlueToothConnectStateChanged=" + state);
        mBlueToothConnectState = state;
        if (state == 0) {
            //如果断开就重连
            inputSystemManager.initWithContext(this,mDevicesAddress, mDevicesName);
//            inputSystemManager.reConnectBlueTooth();
        }
    }
}

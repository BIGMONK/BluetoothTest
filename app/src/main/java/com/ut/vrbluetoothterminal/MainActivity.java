package com.ut.vrbluetoothterminal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ut.vrbluetoothterminal.manager.InputSystemManager;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements InputSystemManager.HeartBeatSystemEventListener,
        View.OnClickListener, InputSystemManager.BlueToothConnectStateEvevtListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private InputSystemManager inputSystemManager;
    private TextView textViewSentData;
    private Random mRandom;
    private Button disConnectButton;
    private Button connectButton;
    private int mBlueToothConnectState;

//            private String mDevicesAddress="F4:5E:AB:AF:1D:BF";
//    private String mDevicesName="HMSoft";
    private String mDevicesAddress = "FA:0A:50:A3:2D:1D";
    private String mDevicesName = "STEPER";

    private Button send0x01;
    private Button send0x02;
    private Button send0xFE;
    private Button send0xFF;
    private Button notify;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

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
        inputSystemManager.initWithContext(this, mDevicesAddress, mDevicesName);

    }

    int i = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect:
                Log.d(TAG, "onClick connect mBlueToothConnectState=" + mBlueToothConnectState);
                if (mBlueToothConnectState == 0)
                    //依次根据地址或者名称连接指定的蓝牙设备
                    inputSystemManager.initWithContext(this, mDevicesAddress, mDevicesName);
                break;
            case R.id.disconnect:
                Log.d(TAG, "onClick disconnect mBlueToothConnectState=" + mBlueToothConnectState);
                if (mBlueToothConnectState == 2)
                    inputSystemManager.disconnectDevice();
                break;
            case R.id.text_sent_data:
                if (mBlueToothConnectState == 2) {
                    i++;
                    byte[] bytes = ("" + i).getBytes();
                    inputSystemManager.sendData(bytes);
                    StringBuffer byteString = new StringBuffer();
                    for (int j = 0; j < bytes.length; j++) {
                        byteString.append(bytes[i] + "  ");
                    }
                    textViewSentData.setText(byteString.toString());
                }
                break;
            case R.id.send0x01:
//                inputSystemManager.sendData(UIUtils.hexStringToByteArray("01"));
                inputSystemManager.sendData("0x01");
                break;
            case R.id.send0x02:
                inputSystemManager.sendData("0x02");
                break;
            case R.id.send0xFE:
                inputSystemManager.sendData("0xFE");
                break;
            case R.id.send0xFF:
                inputSystemManager.sendData("0xFF");
                break;
            case R.id.notify:
                inputSystemManager.setCharacteristicNotification();
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
        //TODO 加个广播？
        //0 断开  1 正在连接 2 已经连接 -1正在扫描
        Log.d(TAG, "onBlueToothConnectStateChanged=" + state);
        mBlueToothConnectState = state;
//        if (state == 0) {
//            //如果断开就重连
//            inputSystemManager.initWithContext(this,mDevicesAddress, mDevicesName);
////            inputSystemManager.reConnectBlueTooth();
//        }
    }

    private void initView() {
        send0x01 = (Button) findViewById(R.id.send0x01);
        send0x02 = (Button) findViewById(R.id.send0x02);
        send0xFE = (Button) findViewById(R.id.send0xFE);
        send0xFF = (Button) findViewById(R.id.send0xFF);
        notify = (Button) findViewById(R.id.notify);

        send0x01.setOnClickListener(this);
        send0x02.setOnClickListener(this);
        send0xFE.setOnClickListener(this);
        send0xFF.setOnClickListener(this);
        notify.setOnClickListener(this);
    }
}

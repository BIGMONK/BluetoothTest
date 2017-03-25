package com.ut.vrbluetoothterminal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ut.vrbluetoothterminal.bluetooth.BleDeviceAdapter;
import com.ut.vrbluetoothterminal.bluetooth.BleDeviceBean;
import com.ut.vrbluetoothterminal.manager.InputSystemManager;
import com.ut.vrbluetoothterminal.utils.ByteUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements
        InputSystemManager.BlueToothConnectStateEvevtListener,
        InputSystemManager.BlueToothDataValuesChangedListener, View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private InputSystemManager inputSystemManager;
    private HashMap<String, BleDeviceBean> devicesMap = new HashMap<>();
    private RecyclerView devicesRecyclerView;
    private BleDeviceAdapter adapter;
    private EditText edittext_data;
    private TextView text_ready_data;
    private TextView text_receive_after_send;
    private Button btn_add_data;
    private Button btn_clear_data;
    private Button btn_send_data;
    private RecyclerView devicesList;
    private Button btn_start_update;
    private Button btn_get_hard_version;
    private Button btn_get_soft_version;
    private Button btn_set_soft_version;
    private Button btn_set_hard_version;
    private Button btn_set_app_addr;
    private Button btn_set_file_length;
    private Button btn_set_data;
    private Button btn_reset;
    private String mainBoardMac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        devicesMap = new HashMap<>();
        //1  手环   2  主控板   3 计步器   4  三角心率计
        String handBandMac = "D3:CB:8F:A3:20:BA";
        mainBoardMac = "D8:B0:4C:B6:50:91";
        //        mainBoardMac = "D8:B0:4C:B6:50:D8";
        //mainBoardMac = "D8:B0:4C:B6:50:A9";
        String HRMMac = "98:7B:F3:C4:D5:7E";
        String steperMac = "D3:CB:8F:A3:20:BA";
        //        BleDeviceBean ble = new BleDeviceBean("手环", handBandMac, 1);
        BleDeviceBean ble2 = new BleDeviceBean("主控板", mainBoardMac, 2);
        //        BleDeviceBean ble3 = new BleDeviceBean("计步器", mainBoardMac, 3);
        //        BleDeviceBean ble4 = new BleDeviceBean("三角心率计", HRMMac, 4);
        //        devicesMap.put(handBandMac, ble);
        devicesMap.put(mainBoardMac, ble2);
        //        devicesMap.put(steperMac, ble3);
        //        devicesMap.put(HRMMac, ble4);


        initView();
        inputSystemManager = InputSystemManager.getInstance();
        inputSystemManager.registerBlueToothDataValuesChangedListener(this);
        //监听蓝牙连接状态
        inputSystemManager.setBlueToothConnectStateEvevtListener(this);
        inputSystemManager.initWithContext(this, devicesMap);
    }


    @Override
    protected void onDestroy() {
        inputSystemManager.unRegisterBlueToothDataValuesChangedListener(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    @Override
    public void onBlueToothConnectStateChanged(String add, int state) {
        Log.d(TAG, add + "   状态     " + state);
        devicesMap.get(add).setState(state);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //写数据回复
                case 666:
                    byte[] data = (byte[]) (msg.getData().get("data"));

                    text_receive_after_send.setText("长度：" + data.length + "  内容:" + ByteUtils.getHexString(data));

                    break;
            }

        }
    };

    public void write3Bytes(byte[] bytes) {
        if (bytes.length < 3) {
            Log.d(TAG, "write3Bytes err");
            return;
        }
        inputSystemManager.sendData(mainBoardMac,
                new byte[]{(byte) 0xAA, bytes[0], bytes[1],
                        bytes[2], 0x00, 0x00, 0x00, 0x00, 0x55});
    }

    public void write4Bytes(byte[] bytes) {
        if (bytes.length < 4) {
            Log.d(TAG, "write3Bytes err");
            return;
        }
        inputSystemManager.sendData(mainBoardMac,
                new byte[]{(byte) 0xAA, 0x21, bytes[0], bytes[1],
                        bytes[2], bytes[3], 0x00, 0x00, 0x55});
    }

    boolean isRunableRunning;
    Runnable runable = new Runnable() {
        @Override
        public void run() {
            isRunableRunning = true;
            try {
                InputStream input = getResources().getAssets().open("master_control_B.bin");
                BufferedInputStream bis = new BufferedInputStream(input);
                int fileByteLength = bis.available();
                if (bis != null) {
                    byte[] bs = new byte[fileByteLength];
                    bis.read(bs);
                    int sendCount = 0;
                    int i = 0;
                    while (i < bs.length) {
                        if (i + 1 == bs.length) {
                            write4Bytes(new byte[]{(byte) (sendCount / 256), (byte) (sendCount % 256), (byte) bs[i], 0});
                            Log.d(TAG, "LLLLLLL  data=" + (byte) (sendCount / 256) + "  "
                                    + (byte) (sendCount % 256) + "  " + (byte) bs[i] + "  " + 0);
                        } else {
                            write4Bytes(new byte[]{(byte) (sendCount / 256), (byte) (sendCount % 256), (byte) bs[i], (byte) bs[i + 1]});
                            Log.d(TAG, "LLLLLLL  data=" + (byte) (sendCount / 256) + "  "
                                    + (byte) (sendCount % 256) + "  " + (byte) bs[i] + "  " + (byte) bs[i + 1]);
                        }
                        i++;
                        i++;
                        sendCount++;
                        SystemClock.sleep(50);
                    }


                    //                    byte[] bs = new byte[512];
                    //                    while (bis.available() > 512) {
                    //                        bis.read(bs);
                    //                        for (int i = 0; i < bs.length; i++) {
                    //                            write4Bytes(new byte[]{});
                    //                            write3Bytes(new byte[]{0x21, (byte) bs[i], (byte) bs[i + 1]});
                    //                            i++;
                    //                            Log.d(TAG, "LLLLLLL  data=" + (byte) bs[i] + "  " + (byte) bs[i + 1]);
                    //                            SystemClock.sleep(50);
                    //
                    //                            thread.wait();
                    //                        }
                    //                    }
                    //                    // 处理不足512的剩余部分
                    //                    int remain = bis.available();
                    //                    byte[] last = new byte[remain];
                    //                    for (int i = 0; i < bs.length; i++) {
                    //                        if (i + 1 == bs.length) {
                    //                            write3Bytes(new byte[]{0x21, (byte) bs[i], (byte) 0});
                    //                        } else {
                    //                            write3Bytes(new byte[]{0x21, (byte) bs[i], (byte) bs[i + 1]});
                    //                        }
                    //                        i++;
                    //                        SystemClock.sleep(50);
                    //                        thread.wait();
                    //                    }
                    //                    bis.read(last);
                    bis.close();
                    input.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            isRunableRunning = false;
        }
    };


    @Override
    public void onBlueToothDataValuesChanged(String add, byte[] values) {

        Log.d(TAG, add + "DataValuesChanged" + values.length + "  数据：" + ByteUtils.getHexString(values));
        if (values.length >= 9 && values[0] == 0x55 && values[8] == (byte) 0xAA) {
            if (values[1] == (byte) 0xFF) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putByteArray("data", values);
                message.setData(bundle);
                message.what = 666;
                handler.sendMessage(message);
            }
        }
        //        3 设置指令成功
        //        0x55 0xFF 0x4F 0x4B 0x00 0x00 0x00 0x00 0xAA
        else if (values.length >= 9
                && values[0] == (byte) 0x55
                && values[1] == (byte) 0xFF
                && values[2] == (byte) 0x4F
                && values[3] == (byte) 0x4B
                && values[8] == (byte) 0xAA) {

        }
        //        4 设置指令错误
        //        0x55 0xFF 0x45 0x52 0x52 0x00 0x00 0x00 0xAA
        else if (values.length >= 9
                && values[0] == (byte) 0x55
                && values[1] == (byte) 0xFF
                && values[2] == (byte) 0x45
                && values[2] == (byte) 0x52
                && values[2] == (byte) 0x52
                && values[8] == (byte) 0xAA) {
        }


        devicesMap.get(add).setValues(values);

        //手环就绪
        if (devicesMap.get(add).getType() == 1 && values[6] == 3) {
            inputSystemManager.sendData(add, new byte[]{(byte) -85, (byte) 0, (byte) 4, (byte) -1, (byte) 49, (byte) 0x0a, (byte) 1});
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });


    }


    private void initView() {

        devicesRecyclerView = (RecyclerView) findViewById(R.id.devicesList);
        adapter = new BleDeviceAdapter(this, devicesMap);
        //设置布局管理器
        devicesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置Adapter
        devicesRecyclerView.setAdapter(adapter);
        edittext_data = (EditText) findViewById(R.id.edittext_data);
        edittext_data.setOnClickListener(this);
        text_ready_data = (TextView) findViewById(R.id.text_ready_data);
        text_receive_after_send = (TextView) findViewById(R.id.text_receive_after_send);
        text_ready_data.setOnClickListener(this);
        btn_add_data = (Button) findViewById(R.id.btn_add_data);
        btn_add_data.setOnClickListener(this);
        btn_clear_data = (Button) findViewById(R.id.btn_clear_data);
        btn_clear_data.setOnClickListener(this);
        btn_send_data = (Button) findViewById(R.id.btn_send_data);
        btn_send_data.setOnClickListener(this);
        devicesList = (RecyclerView) findViewById(R.id.devicesList);
        devicesList.setOnClickListener(this);
        btn_start_update = (Button) findViewById(R.id.btn_start_update);
        btn_start_update.setOnClickListener(this);
        btn_get_hard_version = (Button) findViewById(R.id.btn_get_hard_version);
        btn_get_hard_version.setOnClickListener(this);
        btn_get_soft_version = (Button) findViewById(R.id.btn_get_soft_version);
        btn_get_soft_version.setOnClickListener(this);
        btn_set_soft_version = (Button) findViewById(R.id.btn_set_soft_version);
        btn_set_soft_version.setOnClickListener(this);
        btn_set_hard_version = (Button) findViewById(R.id.btn_set_hard_version);
        btn_set_hard_version.setOnClickListener(this);
        btn_set_app_addr = (Button) findViewById(R.id.btn_set_app_addr);
        btn_set_app_addr.setOnClickListener(this);
        btn_set_file_length = (Button) findViewById(R.id.btn_set_file_length);
        btn_set_file_length.setOnClickListener(this);
        btn_set_data = (Button) findViewById(R.id.btn_set_data);
        btn_set_data.setOnClickListener(this);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_reset.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_data:
                submit();
                break;
            case R.id.btn_clear_data:
                for (int i = 0; i < dataRead.length; i++) {
                    dataRead[i] = 0;
                }
                dex = 0;
                dataReadyString.delete(0, dataReadyString.length());
                text_ready_data.setText(dataReadyString.toString());
                break;
            case R.id.btn_send_data:
                break;

            //1 开始固件升级
            //  0xAA 0xAB 0xAC 0xAD 0xAE 0xAF 0x00 0x00 0x55
            case R.id.btn_start_update:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, (byte) 0xAB, (byte) 0xAC, (byte) 0xAD, (byte) 0xAE, (byte) 0xAF, 0x00, 0x00, 0x55});
                break;
            // 8 获取软件版本号
            //0xAA 0x81 0x00 0x00 0x00 0x00 0x00 0x00 0x55
            case R.id.btn_get_soft_version:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, (byte) 0x81, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x55});
                break;
            // 9 获取硬件版本号
            // 0xAA 0x82 0x00 0x00 0x00 0x00 0x00 0x00 0x55
            case R.id.btn_get_hard_version:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, (byte) 0x82, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x55});
                break;
            //  2 设置软件版本号
            //  0xAA 0x11 0x00 0x00 0x00 0x00 0x00 0x00 0x55 （软件版本号，高位在前，低位在后）
            case R.id.btn_set_soft_version:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, 0x11, 0x11, 0x11, 0x00, 0x00, 0x00, 0x00, 0x55});
                break;
            // 3 设置硬件版本号
            //   0xAA 0x12 0x00 0x00 0x00 0x00 0x00 0x00 0x55 （硬件版本号，高位在前，低位在后）
            case R.id.btn_set_hard_version:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, 0x12, 0x12, 0x12, 0x00, 0x00, 0x00, 0x00, 0x55});
                break;
            //   4 app_addr
            // 0xAA 0x13 0x00 0x00 0x00 0x00 0x00 0x00 0x55 （app_addr（0x3400或者0x9800）高位在前，低位在后）
            case R.id.btn_set_app_addr:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, 0x13, (byte) 0x98, 0x00, 0x00, 0x00, 0x00, 0x00, 0x55});
                break;
            //  5 文件长度
            //  0xAA 0x14 0x00 0x00 0x00 0x00 0x00 0x00 0x55 （文件长度，字节数/2，高位在前，低位在后）
            case R.id.btn_set_file_length:
                //inputSystemManager.sendData(mainBoardMac,
                //new byte[]{(byte) 0xAA, (byte) 0x14, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x55});
                InputStream input = null;
                try {
                    input = getResources().getAssets().open("master_control_B.bin");
                    BufferedInputStream bis = new BufferedInputStream(input);
                    Log.d(TAG, "LLLLLLL实际字节数=" + bis.available());
                    int fileByteLength = bis.available() / 2 + bis.available() % 2;
                    Log.d(TAG, "LLLLLLL除以2=" + fileByteLength);
                    Log.d(TAG, "LLLLLLL高位=" + (byte) (fileByteLength / 256));
                    Log.d(TAG, "LLLLLLL低位=" + (byte) (fileByteLength % 256));

                    //写入文件长度
                    write3Bytes(new byte[]{0x14, (byte) (fileByteLength / 256), (byte) (fileByteLength % 256)});
                } catch (IOException e) {
                    e.printStackTrace();
                }


                break;
            //  6 data写入升级包
            // 0xAA 0x21 0x00 0x00 0x00 0x00 0x00 0x00 0x55 （包序号，高位在前，低位在后）（data，高位在前，低位在后）
            case R.id.btn_set_data:

                if (!isRunableRunning) {
                    new Thread(runable).start();
                }else {
                    Log.d(TAG,"isRunableRunning  = true");
                }
                break;
            // 7 强制复位
            //  0xAA 0xFA 0xFB 0xFC 0xFD 0xFE 0x00 0x00 0x55
            case R.id.btn_reset:
                inputSystemManager.sendData(mainBoardMac,
                        new byte[]{(byte) 0xAA, (byte) 0xFA, (byte) 0xFB, (byte) 0xFC, (byte) 0xFD, (byte) 0xFE, 0x00, 0x00, 0x55});
                break;
        }
    }

    byte[] dataRead = new byte[20];
    int dex = 0;
    StringBuilder dataReadyString = new StringBuilder();

    private void submit() {
        // validate
        String data = edittext_data.getText().toString().trim();
        if (TextUtils.isEmpty(data)) {
            Toast.makeText(this, "输入数据", Toast.LENGTH_SHORT).show();
            return;
        }
        byte[] bytes = data.getBytes();

        for (int i = 0; i < bytes.length; i++) {
            if (!((bytes[i] >= 30 && bytes[i] <= 39) || (bytes[i] >= 41 && bytes[i] <= 46) || (bytes[i] >= 61 && bytes[i] <= 66))) {
                Toast.makeText(this, "输入数据异常", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        // TODO validate success, do something

        int dd = Integer.parseInt(data);
        dataRead[dex] = (byte) dd;
        dex++;
        dataReadyString.append(dataRead[dex] + "  ");
        text_ready_data.setText(dataReadyString.toString());

    }
}

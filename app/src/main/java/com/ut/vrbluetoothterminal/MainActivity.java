package com.ut.vrbluetoothterminal;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ut.vrbluetoothterminal.bluetooth.BleDeviceAdapter;
import com.ut.vrbluetoothterminal.bluetooth.BleDeviceBean;
import com.ut.vrbluetoothterminal.manager.InputSystemManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.HAND_BAND_RECEIVE_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.HAND_BAND_SEND_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.HAND_BAND_SERVICE_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.HRM_RECEIVE_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.HRM_SEND_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.HRM_SERVICE_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.MAINBOARD_RECEIVE_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.MAINBOARD_SEND_UUID;
import static com.ut.vrbluetoothterminal.bluetooth.SampleGattAttributes.MAINBOARD_SERVICE_UUID;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, InputSystemManager.BlueToothConnectStateEvevtListener,
        InputSystemManager.BlueToothDataValuesChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private InputSystemManager inputSystemManager;
    private HashMap<String, BleDeviceBean> devicesMap = new HashMap<>();
    private RecyclerView devicesRecyclerView;
    private BleDeviceAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1  手环   2  主控板   3 计步器   4  三角心率计
        BleDeviceBean ble = new BleDeviceBean("手环", "D3:CB:8F:A3:20:BA", 1,
                HAND_BAND_SERVICE_UUID, HAND_BAND_RECEIVE_UUID, HAND_BAND_SEND_UUID, CLIENT_CHARACTERISTIC_CONFIG);
        BleDeviceBean ble2 = new BleDeviceBean("三角心率计", "98:7B:F3:C4:D5:7E", 4,
                HRM_SERVICE_UUID, HRM_RECEIVE_UUID, HRM_SEND_UUID, CLIENT_CHARACTERISTIC_CONFIG);
        BleDeviceBean ble3 = new BleDeviceBean("主控板", "D8:B0:4C:B6:4E:D6", 2,
                MAINBOARD_SERVICE_UUID, MAINBOARD_RECEIVE_UUID, MAINBOARD_SEND_UUID, CLIENT_CHARACTERISTIC_CONFIG);
        devicesMap.put("D3:CB:8F:A3:20:BA", ble);
        devicesMap.put("98:7B:F3:C4:D5:7E", ble2);
//        devicesMap.put("D8:B0:4C:B6:4E:D6", ble3);

        initView();

        inputSystemManager = InputSystemManager.getInstance();
//        inputSystemManager.setBlueToothDataValuesChangedListener(this);
        inputSystemManager.registerBlueToothDataValuesChangedListener(this);
        //监听蓝牙连接状态
        inputSystemManager.setBlueToothConnectStateEvevtListener(this);
        inputSystemManager.initWithContext(this, devicesMap);

    }


    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onDestroy() {
        inputSystemManager.unRegisterBlueToothDataValuesChangedListener(this);
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }


    /**
     * 创建文件
     *
     * @param filepath 文件
     * @return 是否创建成功，成功则返回true
     */
    public static boolean createFile(Context context, String filepath) {
        Boolean bool = false;

        File file = new File(filepath);
        try {
            //如果文件不存在，则创建新的文件
            if (!file.exists()) {
                file.createNewFile();
                bool = true;
                System.out.println("success create file,the file is " + file.getAbsolutePath());
                //创建文件成功后，写入内容到文件里

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bool;
    }

    /**
     * B方法追加文件：使用FileWriter
     */
    public static void appendMethodB(Context context, String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            if (!new File(fileName).exists()) {
                createFile(context, fileName);
            }
            FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void onBlueToothDataValuesChanged(String add, byte[] values) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < values.length; j++) {
            sb.append(values[j] + "    ");
        }
        Log.d(TAG, add + "    数据     " + sb.toString());

        devicesMap.get(add).setValues(values);

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
    }


}

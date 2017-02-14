package com.ut.vrbluetoothterminal.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * Created by black on 2016/4/12.
 */
public class BlueToothLeManager extends Thread implements BluetoothLeTool.BluetoothLeDataListener,
        BluetoothLeTool.BluetoothLeDiscoveredListener,
        BluetoothLeTool.BluetoothLeStatusListener {

    private Runnable mRunnable;
    private String mDeviceAddress;
    private BluetoothGattCharacteristic mSendBluetoothGattCharacteristic;

    @Override
    public void onBlueToothConnectState(int state) {
        mBlueToothConnectStateListener.onBlueToothConnectState(state);
    }

    public interface SpeedChangedListener {
        void onSpeedChanged(short speed);
    }

    public interface AngleChangedListener {
        void onAngleChanged(float angle);
    }

    public interface HeartBeatChangedListener {
        void onHeartBeatChanged(int heartBeat);
    }

    public interface BlueToothConnectStateChangedListener {
        void onBlueToothConnectState(int state);
    }

    private HeartBeatChangedListener mHeartBeatChangedListener;
    private SpeedChangedListener mSpeedChangedListener;
    private AngleChangedListener mAngleChangedListener;
    private BlueToothConnectStateChangedListener mBlueToothConnectStateListener;

    public void setBlueToothConnectStateChangedListener(BlueToothConnectStateChangedListener listener) {
        mBlueToothConnectStateListener = listener;
    }

    public void setAngleChangedListener(AngleChangedListener angleChangedListener) {
        mAngleChangedListener = angleChangedListener;
    }

    public void setSpeedChangedListener(SpeedChangedListener speedChangedListener) {
        mSpeedChangedListener = speedChangedListener;
    }

    public void setHeartBeatChangedListener(HeartBeatChangedListener heartBeatChangedListener) {
        mHeartBeatChangedListener = heartBeatChangedListener;
    }

    private final String TAG = BlueToothLeManager.class.getSimpleName();
    private Context mContext;
    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private ArrayList<BluetoothDevice> mLeDevices;
    private Map<String, BluetoothDevice> mDeviceMap = new HashMap<>();
    private BluetoothDevice mBluetoothDevice;
    private BluetoothLeTool mBluetoothTool;
    private BluetoothGattCharacteristic mBluetoothGattCharacteristic;
    private int mCharProp;
    private String mDeviceName;
    private long mLastSendTimestamps;

    private List<Integer> mSendDataList = null;

    public BlueToothLeManager(Context pContext) {
        mContext = pContext;
        handler = new Handler();
    }


    Handler handler;

    public void initBlueToothInfo() {
        mBluetoothTool = new BluetoothLeTool();
        //打开蓝牙
        if (blueToothInit()) {
            //扫描蓝牙
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    if (mBluetoothTool.getmConnectionState() != 1 && mBluetoothTool.getmConnectionState() != 2) {
                        //要做的事情

                        scanLeDevice(!mBluetoothAdapter.isDiscovering());
                    }
                }
            };
            handler.postDelayed(mRunnable, 0);
        }
    }

    public void unintBlueToothInfo() {

    }

    private boolean blueToothInit() {

        mLeDevices = new ArrayList<BluetoothDevice>();

        if (!mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return false;
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        Log.i(TAG, "mBluetoothAdapter=" + String.valueOf(mBluetoothAdapter));

        if (mBluetoothAdapter == null) {
            return false;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                if (mContext instanceof Activity)
//                    ((Activity) mContext).startActivityForResult(enableBtIntent, 1);
                mBluetoothAdapter.enable();
            }
        }

        mSendDataList = new LinkedList<Integer>();

        mLastSendTimestamps = System.currentTimeMillis();

        return true;
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
////                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
////                    connectDevice();
//                }
//            }, 5000);
            mBlueToothConnectStateListener.onBlueToothConnectState(BluetoothLeTool.STATE_SCANING);
            mDeviceMap.clear();
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    Log.d(TAG, "BlueToothLeManager onLeScan device=" + device.getAddress() + "   " + device.getName());

                    final String deviceName = device.getName();
                    final String deviceAdd = device.getAddress();
                    if (mBluetoothTool.getmConnectionState() == 0) {
//                        UIUtils.runInMainThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                            }
//                        });
//                        if (!mDeviceMap.containsKey(deviceAdd)) {
//                            mDeviceMap.put(deviceAdd, device);
//                        } else
                        {
                            Log.d(TAG, "runInMainThread device put=" + device.getAddress() + "   " + device.getName());
                            //优先根据MAC查找
                            if (!TextUtils.isEmpty(deviceAdd) && deviceAdd.equals(mDeviceAddress)) {
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                handler.removeCallbacks(mRunnable);
                                mBluetoothDevice = device;
                                mDeviceName = mBluetoothDevice.getName();
//                                        mBluetoothDevice = mDeviceMap.get(deviceAdd);
//                                        if (mBluetoothDevice != null) {
                                Log.d(TAG + "蓝牙设备findByAdd：", deviceName + "  " + deviceAdd);
                                connectDevice(deviceAdd);
//                                        }
                            } else if (!TextUtils.isEmpty(deviceName) && deviceName.equals(mDeviceName)) {
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                handler.removeCallbacks(mRunnable);
                                mBluetoothDevice = device;
                                mDeviceAddress = device.getAddress();
//                                        mBluetoothDevice = mDeviceMap.get(deviceAdd);
//                                        if (mBluetoothDevice != null) {
                                Log.d(TAG + "蓝牙设备findByName：", deviceName + "  " + deviceAdd);
                                connectDevice(deviceAdd);//                                        }
                            }
                        }
                    }
                }
            };

    public BluetoothDevice getDevice() {
        int count = mLeDevices.size();
        for (int i = 0; i < count; i++) {
            if (mDeviceName == null)
                return null;

            Log.d(TAG, "devices : " + mLeDevices.get(i).getName() + " mDeviceName  : " + mDeviceName);


            if (mLeDevices.get(i).getName().equals(mDeviceName)) {
                return mLeDevices.get(i);
            }
        }
        return null;
    }

    /**
     * 根据MAC连接蓝牙设备
     * @param address
     * @return
     */
    public boolean connectDevice(String address) {

        if (!mBluetoothTool.initialize()) {
            Log.i(TAG, "Unable to initialize Bluetooth");
            return false;
        }

        mBluetoothTool.setBluetoothLeDataListener(this);
        mBluetoothTool.setBluetoothLeDiscoveredListener(this);
        mBluetoothTool.setBluetoothLeStatusListener(this);

        if (mBluetoothTool != null) {
            final boolean result = mBluetoothTool.connect(address);
            Log.i(TAG, "Connect request result=" + result);

            return result;
        }
        return false;
    }

    public boolean reConnectDevice() {

        if (!mBluetoothTool.initialize()) {
            Log.i(TAG, "Unable to initialize Bluetooth");
            return false;
        }

        if (mBluetoothTool != null && mBluetoothDevice != null) {
            final boolean result = mBluetoothTool.connect(mBluetoothDevice.getAddress());
            Log.i(TAG, "Connect request result=" + result);
            return result;
        }
        return false;
    }


    public void disconnectDevice() {
        if (mBluetoothDevice != null)
            mBluetoothTool.disconnect();
    }

    public void sendData(byte[] value) {
        if (mBluetoothTool.getmConnectionState() == BluetoothLeTool.STATE_CONNECTED) {
            mSendBluetoothGattCharacteristic.setValue(value);
            mBluetoothTool.writeCharacteristic(mSendBluetoothGattCharacteristic);
        }

    }

    public void sendData(String value) {
        if (mBluetoothTool.getmConnectionState() == BluetoothLeTool.STATE_CONNECTED) {
            mSendBluetoothGattCharacteristic.setValue(value);
            mBluetoothTool.writeCharacteristic(mSendBluetoothGattCharacteristic);
        }

    }

    public boolean setCharacteristicNotification() {
        return mBluetoothTool.setCharacteristicNotification(mBluetoothGattCharacteristic, true);


    }

    public void onDataAvailable(byte[] value) {


//        if (value[0] == 0x04 && value[7] == 0x05) {
//            synchronized (mSendDataList) {
//                if (mSendDataList != null && mSendDataList.size() > 0) {
//                    Integer sendData = mSendDataList.get(0);
//                    byte bys = sendData.byteValue();
//                    byte[] sendBuffer = new byte[]{4, 64, bys, 5};
//                    mBluetoothTool.setCharacteristicNotification(mBluetoothGattCharacteristic, true);
//                    mBluetoothGattCharacteristic.setValue(sendBuffer);
//                    mBluetoothTool.writeCharacteristic(mBluetoothGattCharacteristic);
//                    mSendDataList.remove(0);
//                }
//            }
//        }
//
//        if (value[0] != 0x04 || value[7] != 0x05) {
//            return;
//        }
//
        if (System.currentTimeMillis() - mLastSendTimestamps > 50) {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < value.length; i++) {
                sb.append(value[i] + "\t");
            }
            Log.d(TAG, "计步器数据onCharacteristicChanged:" + sb.toString());


//            UserInputInfo userInputInfo = parseUserInputInfo(value);
//
//            if (userInputInfo == null) {
//                return;
//            }
//
//            doMyWork(userInputInfo);
            //圈数
            mAngleChangedListener.onAngleChanged(value[1] + value[2] * 256);
            //速度
            mSpeedChangedListener.onSpeedChanged((short)(value[3] + value[4] * 256));

            mLastSendTimestamps = System.currentTimeMillis();
        }
    }

    private void doMyWork(UserInputInfo userInputInfo) {
        //如果有其它的变化，全部放到这儿来处理
        if ((userInputInfo.changeValueStatus & UserInputInfo.VALUE_CHANGED_HEARTRATE)
                == UserInputInfo.VALUE_CHANGED_HEARTRATE) {
            if (mHeartBeatChangedListener != null) {
                mHeartBeatChangedListener.onHeartBeatChanged(userInputInfo.heartBeat);
            }
        }

        if ((userInputInfo.changeValueStatus & UserInputInfo.VALUE_CHANGED_ANGLE)
                == UserInputInfo.VALUE_CHANGED_ANGLE) {
            if (mAngleChangedListener != null) {
                mAngleChangedListener.onAngleChanged(userInputInfo.angle);
            }
        }

        if (mSpeedChangedListener != null) {
            mSpeedChangedListener.onSpeedChanged(userInputInfo.speed);
        }
//        if ((userInputInfo.changeValueStatus & UserInputInfo.VALUE_CHANGED_SPEED)
//                == UserInputInfo.VALUE_CHANGED_SPEED) {
//            if (mSpeedChangedListener != null) {
//                mSpeedChangedListener.onSpeedChanged(userInputInfo.speed);
//            }
//        }
    }


//    public void onConnected() {
//        Log.d(TAG, "****ACTION_GATT_CONNECTED********");
//        mBlueToothConnectStateListener.onBlueToothConnectState(mBluetoothTool.STATE_CONNECTED);
//    }
//
//    public void onDisconnected() {
//        Log.d(TAG, "*********ACTION_GATT_DISCONNECTED***********");
//        mBlueToothConnectStateListener.onBlueToothConnectState(mBluetoothTool.STATE_DISCONNECTED);
//    }

    public void onDiscovered(List<BluetoothGattService> supportedService) {
        List<BluetoothGattService> mGattServices = supportedService;
        for (int i = 0; i < mGattServices.size(); i++) {
            UUID uuid = mGattServices.get(i).getUuid();
            Log.d(TAG, "**********onDiscovered" + uuid);
            //找到心率service
            if (uuid.equals(SampleGattAttributes.HAND_BAND_SERVICE_UUID)) {
                BluetoothGattService mBluetoothGattService = mGattServices.get(i);
                List<BluetoothGattCharacteristic> ListBlueChar = mBluetoothGattService.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : ListBlueChar) {
                    Log.d(TAG, "**********onDiscovered--------characteristic"
                            + "  getProperties=" + characteristic.getProperties()
                            + "  getUuid=" + characteristic.getUuid()
                            + "  getValue=" + (characteristic.getValue() == null ? "null" : characteristic.getValue().toString())
                            + "  getService().getUuid()=" + characteristic.getService().getUuid()
                    );

                    if (characteristic != null && characteristic.getUuid().equals(SampleGattAttributes.HAND_BAND_RECEIVE_UUID)) {
                        //接收数据的BGC
                        mBluetoothGattCharacteristic = characteristic;

                        //直接notify
//                        boolean setNotify=setCharacteristicNotification();
//                        Log.d(TAG, "******onDiscovered------" + characteristic.getUuid().toString()+"   setNotify="+setNotify);
                        mCharProp = characteristic.getProperties();
                    } else if (characteristic != null && characteristic.getUuid().equals(SampleGattAttributes.HAND_BAND_SEND_UUID)) {
                        //发送数据的BGC
                        mSendBluetoothGattCharacteristic = characteristic;
                        Log.d(TAG, "******onDiscovered------" + characteristic.getUuid().toString());
                    }
                }
            } else if (uuid.toString().equals("0000ffe0-0000-1000-8000-00805f9b34fb")) {
                BluetoothGattService mBluetoothGattService = mGattServices.get(i);
                List<BluetoothGattCharacteristic> ListBlueChar = mBluetoothGattService.getCharacteristics();
                for (BluetoothGattCharacteristic characteristic : ListBlueChar) {
                    Log.d(TAG, "--------------" + characteristic.getProperties() + "");
                    if (characteristic != null) {
                        mBluetoothGattCharacteristic = characteristic;
                        setCharacteristicNotification();
                        mCharProp = characteristic.getProperties();
                    }
                }
            }


        }
    }

    public void setDeviceName(String name) {
        mDeviceName = name;
    }

    public void setDeviceAddress(String add) {
        mDeviceAddress = add;
    }

    /**
     * Created by black on 2016/1/24 0024.
     */
    // TODO: 2016/6/30 0030 基本数据类 
    public static class UserInputInfo implements Cloneable {
        public static final int VALUE_CHANGED_SPEED = 0x00000008;
        public static final int VALUE_CHANGED_ANGLE = 0x00000010;
        public static final int VALUE_CHANGED_HEARTRATE = 0x00000040;

        public int changeValueStatus = 0;
        public short speed;
        public float angle;
        public int heartBeat;
        public int resistance;

        public UserInputInfo() {

        }

        public void resetChanged() {
            changeValueStatus = 0x00000000;
        }
    }

    //// TODO: 2016/6/30 0030 解析数据 
    private UserInputInfo parseUserInputInfo(byte[] inBuffer) {

        UserInputInfo userInputInfo = new UserInputInfo();
        boolean hasAnyChanged = false;

//        float speed = (short) (inBuffer[6]*256 + inBuffer[7]);//速度
//        float speed = (float) (inBuffer[2] * 256 + inBuffer[3]) / 100;//速度
//        if (userInputInfo.speed != speed) {
//            userInputInfo.speed = speed;
//            userInputInfo.changeValueStatus |= UserInputInfo.VALUE_CHANGED_SPEED;
//            hasAnyChanged = true;
//        }
        short speed = (short) (inBuffer[2] & 0xFF);
//        if(speed < 0){
//            speed = (short) (256+speed);
//        }
        if (speed == 0) {
            userInputInfo.speed = 0;
            hasAnyChanged = true;
        } else {
            if (userInputInfo.speed != speed) {
                userInputInfo.speed = speed;
//                userInputInfo.changeValueStatus |= UserInputInfo.VALUE_CHANGED_SPEED;
                hasAnyChanged = true;
            }
        }
//        int heartBeat = inBuffer[8];//心率
        int heartBeat = inBuffer[4];//心率
        if (userInputInfo.heartBeat != heartBeat) {
            userInputInfo.heartBeat = heartBeat;
            userInputInfo.changeValueStatus |= UserInputInfo.VALUE_CHANGED_HEARTRATE;
            hasAnyChanged = true;
        }

        //float angle = (float)(((short) (inBuffer[9]*256 + inBuffer[10])-3200)/22.0f);//角度
//        float angle = (float) (inBuffer[5] * 256 + inBuffer[6]) / 100;//角度
        float angle = inBuffer[5];//角度
        if (userInputInfo.angle != angle) {
            userInputInfo.angle = angle;
            userInputInfo.changeValueStatus |= UserInputInfo.VALUE_CHANGED_ANGLE;
            hasAnyChanged = true;
        }

        if (!hasAnyChanged) {
            return null;
        }

        return userInputInfo;
    }

}

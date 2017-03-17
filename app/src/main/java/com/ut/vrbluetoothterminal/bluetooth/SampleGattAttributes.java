package com.ut.vrbluetoothterminal.bluetooth;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by black on 2016/4/12.
 */
public class SampleGattAttributes {


    //通用参数
    public static UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    //主控板  连接就能收到数据  发送数据格式：AA 02 06 00 00 00 00 00 55  //9位数组
//    private static String mDevicesAddress = "D8:B0:4C:B6:4E:D6";//
    public static UUID MAINBOARD_SERVICE_UUID = UUID.fromString("0003cdd0-0000-1000-8000-00805f9b0131");
    public static UUID MAINBOARD_RECEIVE_UUID = UUID.fromString("0003cdd1-0000-1000-8000-00805f9b0131");
    public static UUID MAINBOARD_SEND_UUID = UUID.fromString("0003cdd2-0000-1000-8000-00805f9b0131");
    //手环参数
    public static UUID HAND_BAND_SERVICE_UUID = UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static UUID HAND_BAND_RECEIVE_UUID = UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public static UUID HAND_BAND_SEND_UUID = UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    // 计步器
    public static UUID STEPER_SERVICE_UUID = UUID.fromString("6e40ffe1-b5a3-f393-e0a9-e50e24dcca9e");
    public static UUID STEPER_RECEIVE_UUID = UUID.fromString("6e40ffe3-b5a3-f393-e0a9-e50e24dcca9e");
    public static UUID STEPER_SEND_UUID = UUID.fromString("6e40ffe2-b5a3-f393-e0a9-e50e24dcca9e");  //
    //三角心率计
    private static String mDevicesAddress = "98:7B:F3:C4:D5:7E";//
    public static UUID HRM_SERVICE_UUID = UUID.fromString("0000FFE0-0000-1000-8000-00805f9b34fb");
    public static UUID HRM_RECEIVE_UUID = UUID.fromString("0000ffe2-0000-1000-8000-00805f9b34fb");
    public static UUID HRM_SEND_UUID = UUID.fromString("0000ffe3-0000-1000-8000-00805f9b34fb");


    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static byte[] HAND_BAND_RUN_VALUE = {0x31};
    public static byte[] HAND_BAND_BOAT_VALUE = {0x31};
    public static byte[] HAND_BAND_CYCLE_VALUE = {0x32};
    public static byte[] HAND_BAND_START_VALUE = {0x41};
    public static byte[] HAND_BAND_END_VALUE = {0x42};

    static {
        // Sample Services.
        attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
        attributes.put("0000180a-0000-1000-8000-00805f9b34fb", "Device Information Service");
        // Sample Characteristics.
        attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
        attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }

}

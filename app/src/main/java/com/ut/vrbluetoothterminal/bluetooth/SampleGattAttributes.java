package com.ut.vrbluetoothterminal.bluetooth;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by black on 2016/4/12.
 */
public class SampleGattAttributes {

    private static HashMap<String, String> attributes = new HashMap();
    public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
    public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
    public static UUID HAND_BAND_SERVICE_UUID=UUID.fromString("6e400001-b5a3-f393-e0a9-e50e24dcca9e");
    public static UUID HAND_BAND_RECEIVE_UUID=UUID.fromString("6e400003-b5a3-f393-e0a9-e50e24dcca9e");
    public static UUID HAND_BAND_SEND_UUID=UUID.fromString("6e400002-b5a3-f393-e0a9-e50e24dcca9e");
    public static byte[] HAND_BAND_RUN_VALUE={0x01};
    public static byte[] HAND_BAND_BOAT_VALUE={0x01};
    public static byte[] HAND_BAND_CYCLE_VALUE={0x02};
    public static byte[] HAND_BAND_START_VALUE={0x02};
    public static byte[] HAND_BAND_END_VALUE={0x02};
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

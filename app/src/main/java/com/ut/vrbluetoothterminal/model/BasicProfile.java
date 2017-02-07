package com.ut.vrbluetoothterminal.model;

/**
 * Created by bobmarshall on 7/24/16.
 */
public class BasicProfile {

    private String deviceName;
    private String serverIp;
    private int serverPort;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

}

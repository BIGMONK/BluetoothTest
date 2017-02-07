package com.ut.vrbluetoothterminal.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by black on 2016/2/19 0019.
 */
public class WifiStateManager extends BroadcastReceiver {
    private Context mContext;
    private WifiStateChangedListener mWifiStateChangedListener;

    public interface WifiStateChangedListener{
        void onWifiStateChanged(WifiStateManager manager, boolean isWifiActive);
    }

    public void setWifiStateChangedListener(WifiStateChangedListener wifiStateChangedListener){
        mWifiStateChangedListener = wifiStateChangedListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        boolean isWifiActive = getActiveNetwork(context);
        if(mWifiStateChangedListener != null)
            mWifiStateChangedListener.onWifiStateChanged(WifiStateManager.this,isWifiActive);
    }

    private boolean getActiveNetwork(Context context){
        ConnectivityManager mConnMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();  // 获取活动网络连接信息
        if(networkInfo != null && networkInfo.isAvailable()){
            return true;
        }
        else
            return false;
    }
}

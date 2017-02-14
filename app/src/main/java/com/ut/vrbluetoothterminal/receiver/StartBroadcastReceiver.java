package com.ut.vrbluetoothterminal.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Description:
 * Author：Giousa
 * Date：2016/8/3
 * Email：giousa@chinayoutu.com
 */
public class StartBroadcastReceiver extends BroadcastReceiver {

    private static final String ACTION = "Android.intent.action.BOOT_COMPLETED";


    @Override
    public void onReceive(Context context, Intent intent) {

//        if (intent.getAction().equals(ACTION)){
//            Intent i= new Intent(Intent.ACTION_RUN);
//            i.setClass(context, TService.class);
//            context.startService(i);
//        }

//        Intent service = new Intent(context,TService.class);
//        context.startService(service);
//        Log.d("TAG", "开机自动服务自动启动.....");

    }
}


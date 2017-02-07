package com.ut.vrbluetoothterminal.client.handlers;


import android.util.Log;
import com.ut.vrbluetoothterminal.client.LTNConstants;
import com.ut.vrbluetoothterminal.client.interfaces.ILTNClient;
import com.ut.vrbluetoothterminal.common.beans.CommandObject;
import com.ut.vrbluetoothterminal.common.beans.PackageObject;
import com.ut.vrbluetoothterminal.serialndk.SerialManager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LTNClientHandler extends SimpleChannelInboundHandler<PackageObject> implements LTNConstants {

    private final String TAG = LTNClientHandler.class.getSimpleName();
    private ILTNClient mLTNClient = null;
    private SerialManager mSerialManager;


    public LTNClientHandler() {

    }

    public LTNClientHandler(ILTNClient ltnClient) {
        mLTNClient = ltnClient;
        mSerialManager = SerialManager.getInstance();
        mSerialManager.openSerial();
    }

    public interface ResistanceListener{
        void onResistance(int resistanceNum);
    }

    private ResistanceListener mResistanceListener;

    public void setResistanceListener(ResistanceListener resistanceListener){
        mResistanceListener = resistanceListener;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable errMsg) throws Exception {
        Log.d(TAG,"Client exceptionCaught");

        if (mLTNClient != null) {
            mLTNClient.clientExceptionCaught(mLTNClient, errMsg);
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG,"Client handlerAdded");
        if (mLTNClient != null) {
            mLTNClient.clientAdded(mLTNClient);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Log.d(TAG,"Client handlerRemoved");
        if (mLTNClient != null) {
            mLTNClient.clientRemoved(mLTNClient);
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PackageObject packageObject) throws Exception {
//        Log.d(TAG,"channelRead000: "+packageObject);
        CommandObject.MessageObject commandObject = packageObject.getCommandObject();
        if (commandObject.getCommand().equals(LTN_CURRENT_RESISTANCE)) {

//            Log.d(TAG, "channelRead00101:"+Integer.parseInt(commandObject.getParams().get(LTN_PARAM_RESISTANCE)));
//            Log.d(TAG, "channelRead00102:"+Integer.parseInt(commandObject.getParams().get(LTN_RESISTANCE_LEVEL)));
            sendNowelData(Integer.parseInt(commandObject.getParams().get(LTN_PARAM_RESISTANCE)),Integer.parseInt(commandObject.getParams().get(LTN_RESISTANCE_LEVEL)));

        }
        if (packageObject.getType() == PackageObject.TYPE_HEARTBEAT) {
            ctx.writeAndFlush(packageObject);
        }

    }

    private short[] mNoweData = new short[9];

    private void sendNowelData(int resistanceNum,int resistanceLevel) {

        if (resistanceLevel==0) {
            resistanceNum -= 20;
        } else if (resistanceLevel==1) {

        } else if (resistanceLevel==2) {
            resistanceNum += 20;
        }

        mNoweData[0] = 0x55;
        mNoweData[1] = 0x02;
        mNoweData[2] = (short) (resistanceNum);
        mNoweData[3] = 0x00;
        mNoweData[4] = 0x00;
        mNoweData[5] = 0x00;
        mNoweData[6] = 0x00;
        mNoweData[7] = 0x00;
        mNoweData[8] = 0x00;

//        Log.d(TAG,"resistanceNum:"+resistanceNum+"  resistanceLevel:"+resistanceLevel);
        mSerialManager.writeSerial(mNoweData);

    }

}

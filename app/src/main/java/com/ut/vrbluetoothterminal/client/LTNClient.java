package com.ut.vrbluetoothterminal.client;

import android.util.Log;

import com.ut.vrbluetoothterminal.client.initializers.LTNClientInitializer;
import com.ut.vrbluetoothterminal.client.interfaces.ILTNClient;
import com.ut.vrbluetoothterminal.common.beans.CommandObject;
import com.ut.vrbluetoothterminal.common.beans.PackageObject;
import java.util.Map;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by liuenbao on 6/20/16.
 */
public class LTNClient implements ILTNClient {
	
	private static final String TAG = LTNClient.class.getSimpleName();
	
	private static final String CLIENT_VERSION = "v1.0";
	
	public enum LTNClientStatus {
		LTNClientStatusInit,
		LTNClientStatusException,
		LTNClientStatusDisconnecting,
		LTNClientStatusDisconnected,
		LTNClientStatusConnecting,
		LTNClientStatusConnected,
	};

    private String mHost;
    private short mPort;
    private EventLoopGroup mWorkerGroup;
    private ChannelFuture mChannelFuture;
    
    private LTNClientStatus mStatus;

    public LTNClient(String host, short port) {
    	mHost = host;
    	mPort = port;
    	
    	mStatus = LTNClientStatus.LTNClientStatusInit;
    }

    public void connectToHost() {
        
        mStatus = LTNClientStatus.LTNClientStatusConnecting;
    	
        mWorkerGroup = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(mWorkerGroup);
        b.channel(NioSocketChannel.class);
        b.handler(new LTNClientInitializer(this));

		Log.d(TAG, "Before connect to server");
		mChannelFuture = b.connect(mHost, mPort);
		Log.d(TAG, "After connect to server");

    }

    public boolean disconnectFromHost() {

		mStatus = LTNClientStatus.LTNClientStatusDisconnecting;
    	
    	mWorkerGroup.shutdownGracefully();
    	mWorkerGroup = null;
		if(mChannelFuture != null) {
			mChannelFuture.channel().closeFuture().syncUninterruptibly();
			mChannelFuture = null;
		}
		
        return true;
    }
    
    public LTNClientStatus getStatus() {
    	return mStatus;
    }

	@Override
	public void clientExceptionCaught(ILTNClient client, Throwable errMsg) throws Exception {
		mStatus = LTNClientStatus.LTNClientStatusException;
		
		if (mLTNClientExceptionListener != null) {
			mLTNClientExceptionListener.exceptionWhileConnect(this, mHost, mPort);
		}
	}

	@Override
	public void clientAdded(ILTNClient client) throws Exception {
		mStatus = LTNClientStatus.LTNClientStatusConnected;

		Log.d(TAG, "The connect status is : " + mStatus);
		
		if (mLTNClientAddedListener != null) {
			mLTNClientAddedListener.connectedTo(this, mHost, mPort);
		}
	
	}

	@Override
	public void clientRemoved(ILTNClient client) throws Exception {
		mStatus = LTNClientStatus.LTNClientStatusDisconnected;
		
		if (mLTNClientRemovedListener != null) {
			mLTNClientRemovedListener.disconnectedFrom(this, mHost, mPort);
		}
	}
	
	//interface
	public interface ILTNClientAddedListener {
		void connectedTo(ILTNClient client, String host, short port);
	}
	
	public interface ILTNClientRemovedListener {
		void disconnectedFrom(ILTNClient client, String host, short port);
	}
	
	public interface ILTNClientExceptionListener {
		void exceptionWhileConnect(ILTNClient client, String host, short port);
	}
	
	private ILTNClientAddedListener mLTNClientAddedListener = null;
	public void setClientAddedListener(ILTNClientAddedListener listener) {
		mLTNClientAddedListener = listener;
	}
	
	private ILTNClientRemovedListener mLTNClientRemovedListener = null;
	public void setClientRemovedListener(ILTNClientRemovedListener listener) {
		mLTNClientRemovedListener = listener;
	}
	
	private ILTNClientExceptionListener mLTNClientExceptionListener = null;
	public void setClientExceptionListener(ILTNClientExceptionListener listener) {
		mLTNClientExceptionListener = listener;
	}

	@Override
	public boolean sendMessage(String message) {
		if (mStatus != LTNClientStatus.LTNClientStatusConnected) {
    		return false;
    	}
    	
        if(mChannelFuture != null && mChannelFuture.channel().isWritable()) {
            Log.d(TAG, "send message" + message);
			mChannelFuture.channel().write(message);
            return true;
        }else {
        	//Reconnect to the server
        	connectToHost();     
        }
        return false;
	}
	
	public boolean sendCommand(String command, Map<String, String> params) {
		if (mStatus != LTNClientStatus.LTNClientStatusConnected) {
			Log.e(TAG, "The Status is not connected : " + mStatus);
    		return false;
    	}

        if(mChannelFuture != null && mChannelFuture.channel().isWritable()) {

			Log.d(TAG, "connect to " + mChannelFuture.channel());

        	CommandObject.MessageObject.Builder builder = CommandObject.MessageObject.newBuilder();
        	builder.setVersion(CLIENT_VERSION);
        	builder.setCommand(command);
        	builder.putAllParams(params);
        	
        	PackageObject packageObject = new PackageObject();
        	packageObject.setCommandObject(builder.build());

			Log.d(TAG, "packageObject object : " + packageObject.toString());

			mChannelFuture.channel().writeAndFlush(packageObject);

			return true;
        }else {
        	//Reconnect to the server
        	connectToHost();     
        }
        return false;
	}

}

package com.ut.vrbluetoothterminal.client.initializers;

import android.util.Log;

import com.ut.vrbluetoothterminal.client.handlers.LTNClientHandler;
import com.ut.vrbluetoothterminal.client.interfaces.ILTNClient;
import com.ut.vrbluetoothterminal.common.codecs.TransferProtocolCodec;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Created by liuenbao on 6/20/16.
 */
public class LTNClientInitializer extends ChannelInitializer<SocketChannel> {

    private TransferProtocolCodec protoCodec = new TransferProtocolCodec();
    private LTNClientHandler mClientHandler = null;

    public LTNClientInitializer(ILTNClient client) {
        mClientHandler = new LTNClientHandler(client);
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
    	/**
		 * type 	: 4 bit
		 * reversed : 4 bit
		 * checksum : 8 bit
		 * length   : 16 bit
		 * value    : length byte
		 **/
		ch.pipeline().addFirst("packageFrame", new LengthFieldBasedFrameDecoder(65535, 2, 2, 0, 0));
    	
    	ch.pipeline().addLast("protocolCodec", protoCodec);
        
        ch.pipeline().addLast("clientHandler", mClientHandler);
    }

}

package com.ut.vrbluetoothterminal.common.codecs;

import com.ut.vrbluetoothterminal.common.beans.CommandObject;
import com.ut.vrbluetoothterminal.common.beans.PackageObject;

import org.apache.log4j.Logger;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

/**
 * Created by liuenbao on 6/20/16.
 */
@ChannelHandler.Sharable
public class TransferProtocolCodec extends MessageToMessageCodec<ByteBuf, PackageObject> {
	
	private static final Logger logger = Logger.getLogger(TransferProtocolCodec.class);
	
	private static final byte HIGH_HALF_BYTE_MASK = (byte) 0xf0;
	private static final byte LOW_HALF_BYTE_MASK = (byte) 0x0f;
	
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, PackageObject proto, List<Object> list) throws Exception {
        logger.info("Begin encode buffer");
    	
    	ByteBuf byteBuf = ByteBufAllocator.DEFAULT.buffer();
        
        //Write type
        byte trData = (byte)(proto.getType() << 4 | proto.getReversed());
        byteBuf.writeByte(trData);
        
        logger.info("Encode trData is : " + trData);
        
        //Write checksum
        byteBuf.writeByte(proto.getChecksum());
        
        if (proto.getCommandObject() != null) {
        	//Write protobuf
        	byte[] protoBuf = proto.getCommandObject().toByteArray();
        
        	byteBuf.writeShort(protoBuf.length);
        	byteBuf.writeBytes(protoBuf);
        } else {
        	byteBuf.writeShort((short)0);
        }
        
        list.add(byteBuf);
        
        logger.info("End encode buffer");
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
    	logger.info("Begin decode buffer");
    	
    	PackageObject proto = new PackageObject();
    	
    	byte trData = byteBuf.readByte();
    	
        logger.info("Decode trData is : " + trData);
    	
    	//Read Type
    	proto.setType((byte)((trData >>> 4) & LOW_HALF_BYTE_MASK));
    	proto.setReversed((byte)(trData & LOW_HALF_BYTE_MASK));
    	
    	//Read checksum
    	proto.setChecksum((byte)byteBuf.readByte());
    	proto.setLength((int)byteBuf.readShort());
    	
    	//Read protobuf
    	byte[] commandObject = new byte[proto.getLength()];
    	byteBuf.readBytes(commandObject);
    	
    	proto.setCommandObject(CommandObject.MessageObject.parseFrom(commandObject));
    	
        list.add(proto);
        
        logger.info("End decode buffer");
    }
}

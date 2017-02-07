package com.ut.vrbluetoothterminal.common.beans;

import com.ut.vrbluetoothterminal.common.beans.CommandObject.MessageObject;

/**
 * Created by liuenbao on 6/20/16.
 */
public class PackageObject {

    public static final byte TYPE_HANDSHAKE = 0x00 & 0x0f;
    public static final byte TYPE_HEARTBEAT = 0x0f & 0x0f;
    public static final byte TYPE_VALIDDATA = 0x01 & 0x0f;
    public static final byte TYPE_REQUEST 	= 0x02 & 0x0f;
    public static final byte TYPE_RESPONSE  = 0x03 & 0x0f;

    private byte type;
    private byte reversed = 0x00;
    private byte checksum;
    private int length;	//use int to hold the unsinged short
    private MessageObject messageObject;
	
    public byte getType() {
		return type;
	}
	
	public void setType(byte type) {
		this.type = type;
	}
	
	public byte getReversed() {
		return reversed;
	}
	
	public void setReversed(byte reversed) {
		this.reversed = reversed;
	}
	
	public byte getChecksum() {
		return checksum;
	}
	
	public void setChecksum(byte checksum) {
		this.checksum = checksum;
	}
	
	public int getLength() {
		return length;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public MessageObject getCommandObject() {
		return messageObject;
	}
	
	public void setCommandObject(MessageObject messageObject) {
		this.messageObject = messageObject;
	}
	
	@Override
	public String toString() {
		return "PackageObject [type=" + type 
				+ ", reversed=" + reversed 
				+ ", checksum=" + checksum
				+ ", length=" + length
				+ ", messageObject=" + messageObject + "]";
	}
}

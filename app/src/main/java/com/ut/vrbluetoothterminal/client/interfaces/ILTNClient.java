package com.ut.vrbluetoothterminal.client.interfaces;

import java.util.Map;

/**
 * Created by liuenbao on 6/20/16.
 */
public interface ILTNClient {

	void clientExceptionCaught(ILTNClient client, Throwable errMsg) throws Exception;
	
	void clientAdded(ILTNClient client) throws Exception;
	
	void clientRemoved(ILTNClient client) throws Exception;

	boolean sendMessage(String string);
	
	boolean sendCommand(String command, Map<String, String> params);


}
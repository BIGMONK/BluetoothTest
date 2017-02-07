package com.ut.vrbluetoothterminal.clientdemo;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ut.vrbluetoothterminal.client.LTNClient;
import com.ut.vrbluetoothterminal.client.LTNClient.ILTNClientAddedListener;
import com.ut.vrbluetoothterminal.client.LTNClient.ILTNClientExceptionListener;
import com.ut.vrbluetoothterminal.client.LTNClient.ILTNClientRemovedListener;
import com.ut.vrbluetoothterminal.client.interfaces.ILTNClient;

public class ClientDemo {
	
	private static final Logger logger = Logger.getLogger(LTNClient.class);
	
	static LTNClient sInstance = null;
	
	public static void main(String[] args) {
		
		LTNClient client = new LTNClient("127.0.0.1", (short)9090);
		
		client.setClientAddedListener(new ILTNClientAddedListener() {
			@Override
			public void connectedTo(ILTNClient client, String host, short port) {
				
				logger.info("Connect to Host");
			}
		});
		client.setClientRemovedListener(new ILTNClientRemovedListener() {
			@Override
			public void disconnectedFrom(ILTNClient client, String host, short port) {
				logger.info("We are removed from server");
			}
		});
		client.setClientExceptionListener(new ILTNClientExceptionListener() {

			@Override
			public void exceptionWhileConnect(ILTNClient client, String host, short port) {
				logger.info("We are got exception");
			}
			
		});
		
		client.connectToHost();
		
		while (client.getStatus() != LTNClient.LTNClientStatus.LTNClientStatusConnected) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			logger.info("client.getStatus() = " + client.getStatus());
		}
		
		Map<String, String> params = new HashMap<>();
		client.sendCommand("HelloWorld", params);
			
		while(true) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
}

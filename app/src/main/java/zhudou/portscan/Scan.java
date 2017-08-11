package zhudou.portscan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Scan {
	private final Handler h;
	private String[] ipArray=new String[254];

	public Scan(Handler h){
		this.h=h;
		this.ipArray=(new Ip()).getAllIp();
	}
	
	public void scanOne(final String ip, final int port) {
		(new Thread(new Runnable(){
			@Override
			public void run() {
				Socket socket = new Socket();
				SocketAddress socketAddress = new InetSocketAddress(ip, port);
				try {
					socket.connect(socketAddress, 200);
					Message msg = h.obtainMessage();
					msg.what = 1;
					Bundle b = new Bundle();
					b.putString("ip", ip);
					b.putString("port", port + "");
					msg.setData(b);
					msg.sendToTarget();
					//System.out.println(ip+":"+port);
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
		})).start();

	}
	public void scanLocalHostNet(final int port){
        for (int i = 0; i < ipArray.length; i++) {
        	String ip=ipArray[i];
			this.scanOne(ip,port);
			Message msg = h.obtainMessage();
			msg.what = 2;
			Bundle b = new Bundle();
			b.putString("ip", ip);
			b.putString("port", port + "");
			msg.setData(b);
			msg.sendToTarget();
        }
	}
}

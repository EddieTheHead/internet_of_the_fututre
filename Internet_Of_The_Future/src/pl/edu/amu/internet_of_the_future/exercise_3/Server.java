package pl.edu.amu.internet_of_the_future.exercise_3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
	public static void main(String[] args) {
		byte[] receivedData = new byte[256];
		
		DatagramPacket receivedPacket = new DatagramPacket(receivedData, receivedData.length);
		try {
			DatagramSocket serverSocket = new DatagramSocket(1234);
			while(true){
				serverSocket.receive(receivedPacket);
				String message = new String( receivedPacket.getData(),
						receivedPacket.getOffset(), receivedPacket.getLength());
				System.out.println(message);
				//exit on command
				if(message.contains("!quit")) break;
			}
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

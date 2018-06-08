package pl.edu.amu.internet_of_the_future.exercise_3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Client {

	public static void main(String[] args) {
		try {
			DatagramSocket sendSocket = new DatagramSocket();
			// prepare message
			final int PACKET_SIZE = 256;
			final String message = "Hello UDP Server\n";
			final ByteBuffer outputBuffer = ByteBuffer.wrap(new byte[PACKET_SIZE]);
			
			InetAddress address = InetAddress.getByName("127.0.0.1");
			outputBuffer.put(message.getBytes());
			DatagramPacket packet = new DatagramPacket(outputBuffer.array(), PACKET_SIZE, address, 1234);
			//send it to server
			sendSocket.send(packet);
			//end communication
			sendSocket.close();
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

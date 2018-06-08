package pl.edu.amu.internet_of_the_future.exercise_1_and_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(1234);
			Socket socket = serverSocket.accept();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			BufferedWriter responseWriter = new BufferedWriter( 
					new OutputStreamWriter(socket.getOutputStream())); 
			
			String inputMessage;
			do{
				//read message from server
				inputMessage = bufferedReader.readLine();
				
				StringBuilder sb = new StringBuilder();
				sb.append("RECIVED: ");
				sb.append(inputMessage.toUpperCase());
				sb.append("\n");
				
				System.out.println(sb.toString());
				//echo that message
				responseWriter.write(sb.toString());
				responseWriter.flush();
			}while(! inputMessage.equals("quit"));
			
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

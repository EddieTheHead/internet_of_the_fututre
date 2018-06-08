package pl.edu.amu.internet_of_the_future.exercise_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		BufferedReader userInputReader = new BufferedReader(
				new InputStreamReader(System.in));
		try {
			Socket clientSocket = new Socket("127.0.0.1", 1234);
			BufferedReader messageReader = new BufferedReader( 
					new InputStreamReader(clientSocket.getInputStream()));
			OutputStreamWriter messageWriter = new OutputStreamWriter(clientSocket.getOutputStream());
			
			while(true){
				//get user input
				if(userInputReader.ready()){
					String command = userInputReader.readLine();
					//send user input to server
					messageWriter.write(command + "\n");
					messageWriter.flush();
					if(command.equals("quit")) break;
				}
				//read response from server
				if(messageReader.ready()){
					String response = messageReader.readLine();
					//display server response
					System.out.println(response);	
				}
			}
			clientSocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}

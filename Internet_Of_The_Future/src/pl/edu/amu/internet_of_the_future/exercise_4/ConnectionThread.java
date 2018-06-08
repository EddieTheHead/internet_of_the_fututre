package pl.edu.amu.internet_of_the_future.exercise_4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;

public class ConnectionThread implements Runnable{
	private int clientId;
	private Socket socket;
	private BlockingQueue<Message> messagesToSend;
	private BlockingQueue<Message> receivedMessages;	
	
	public BlockingQueue<Message> getMessagesToSend() {
		return messagesToSend;
	}

	public void setMessagesToSend(BlockingQueue<Message> messagesToSend) {
		this.messagesToSend = messagesToSend;
	}

	
	ConnectionThread( int clientId, Socket socket, BlockingQueue<Message> messagesToSend, BlockingQueue<Message> receivedMessages){
		this.clientId = clientId;
		this.socket = socket;
		this.messagesToSend = messagesToSend;
		this.receivedMessages = receivedMessages;
	}
	
	@Override
	public void run() {
		try {
			
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			BufferedWriter responseWriter = new BufferedWriter( 
					new OutputStreamWriter(socket.getOutputStream())); 
			
			String inputMessage;
			while(true){
				//read message from socket if available
				if(bufferedReader.ready()){
					
					inputMessage = bufferedReader.readLine();
					System.out.println(String.format("Connection Thread: Client nr: %d send text: %s",clientId,inputMessage));
					String parts[] = inputMessage.split(":");
					if( parts.length == 2){
						Message msg = new Message(Integer.parseInt(parts[0]), clientId , parts[1]);
						System.out.println(String.format("Connection Thread: Forwarding to client: %d", msg.getReceiverId()));
						//put new message in queue
						receivedMessages.put(msg);
					}
					else if( inputMessage.equals("quit")) break;
				}
				//send all messages addressed to this client
				Message msg;
				while ((msg = messagesToSend.poll()) != null){
					System.out.println(String.format("Connection Thread: Sending message to client %d", clientId));
					responseWriter.write(msg.getMessage() + "\n");
				}
				responseWriter.flush();
			}
			
			socket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}		
	
}

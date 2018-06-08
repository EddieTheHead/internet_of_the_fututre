package pl.edu.amu.internet_of_the_future.exercise_4;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {
	public static void main(String[] args){
		int connectionCount = 1;
		
		Map<Integer, ConnectionThread> connections = new ConcurrentHashMap<Integer, ConnectionThread>();
		
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(1234);
			BlockingQueue<Message> mainMessageQueue = new LinkedBlockingQueue<Message>();
			
			//create main message queue manager thread
			QueueManager queueMaster = new QueueManager(mainMessageQueue , connections);
			Thread queueMasterThread = new Thread(queueMaster);
			queueMasterThread.start();
			
			while(true){
				//create new connection on request
				BlockingQueue<Message> threadInputQueue = new LinkedBlockingQueue<Message>();
				ConnectionThread connection = new ConnectionThread(
						connectionCount , serverSocket.accept(), threadInputQueue, mainMessageQueue);
				//move connection to separate thread
				Thread thread = new Thread(connection);
				//store connection for future use
				connections.put(connectionCount++,connection);
				//start newly created thread
				thread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

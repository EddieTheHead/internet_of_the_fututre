package pl.edu.amu.internet_of_the_future.exercise_4;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

// This thread manages communication between different client connection threads

public class QueueManager implements Runnable{
	//all connection threads put received messages into this queue
	BlockingQueue<Message> mainMessageQueue;	
	//connections maintained by this manager
	Map<Integer, ConnectionThread> connections;
	
	public QueueManager(BlockingQueue<Message> mainMessageQueue, Map<Integer, ConnectionThread> connections) {
		super();
		this.mainMessageQueue = mainMessageQueue;
		this.connections = connections;
	}

	@Override
	public void run(){
		Message msg;
		
		while(true){
			//System.out.println(String.format("There are %d active connections", connections.size()) );
			//if there are new messages to process
			if((msg = mainMessageQueue.poll()) != null){
				System.out.println("QueueManagerThread: There is a new message in main queue!");
				//if receiver exists					
				ConnectionThread connection;
				if((connection = connections.get(msg.getReceiverId())) != null){
					try {
						// tell connection thread to send our message
						System.out.println(String.format("QueueManagerThread: Putting new message into clients %d send queue", msg.getReceiverId()));
						connection.getMessagesToSend().put(msg);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}

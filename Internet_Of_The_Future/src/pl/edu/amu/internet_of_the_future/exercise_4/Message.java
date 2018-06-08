package pl.edu.amu.internet_of_the_future.exercise_4;

public class Message {
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public Message(int receiverId, int senderId, String message) {
		super();
		this.receiverId = receiverId;
		this.senderId = senderId;
		this.message = message;
	}
	
	
	private int receiverId;
	private int senderId;
	private String message;
	
}

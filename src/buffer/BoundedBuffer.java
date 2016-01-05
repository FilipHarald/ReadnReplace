package buffer;

import gui.Controller;

public class BoundedBuffer {
	private Controller controller;
	
	private String[] buffer;
	private BufferStatus[] status;
	
	private int max; 
	private int writePos, readPos, findPos;
	
	private Object lock;
	
	public BoundedBuffer(Controller c, int max){
		controller = c;
		this.max = max;
		initBuffer();
		writePos = 0;
		readPos = 0;
		findPos = 0;
		lock = new Object();
	}
	
	
	
	
	
	
	
	private void initBuffer(){
		buffer = new String[max];
		status = new BufferStatus[max];
		for(int i = 0; i < max; i++){
			buffer[i] = "";
			status[i] = BufferStatus.EMPTY;
		}
	}
	
}

package threads;

import buffer.BoundedBuffer;
import gui.Controller;

public class BufferReader extends Thread{
	private Controller controller;
	private BoundedBuffer buffer;
	private int readLines;
	
	public BufferReader(BoundedBuffer buffer, Controller controller) {
		this.controller = controller;
		this.buffer = buffer;
		readLines = 0;
	}
	@Override
	public void run() {
		while(controller.getNbrOfLines() > readLines){
			String line = buffer.read();
			if(line != null){
				controller.writeToDest(line);
				readLines++;
			}
		}
		controller.stopReplacer();
		System.out.println("READER FINISHED");
	}


}

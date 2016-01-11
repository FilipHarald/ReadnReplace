package threads;

import buffer.BoundedBuffer;
import gui.Controller;

/**
 * A reader for the bounded buffer
 * 
 * @author Filip
 *
 */
public class BufferReader extends Thread{
	private Controller controller;
	private BoundedBuffer buffer;
	private int readLines;
	
	/**
	 * creates the reader
	 *  
	 * @param buffer
	 * @param controller
	 */
	public BufferReader(BoundedBuffer buffer, Controller controller) {
		this.controller = controller;
		this.buffer = buffer;
		readLines = 0;
	}
	@Override
	public void run() {
		for(int i = 0; i < controller.getNbrOfLines(); i++){
			String line;
			try {
				line = buffer.read();
				controller.writeToDest(line);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		controller.stopReplacer();
		System.out.println("READER FINISHED");
		
//		while(controller.getNbrOfLines() > readLines){
//			String line = buffer.read();
//			if(line != null){
//				controller.writeToDest(line);
//				readLines++;
//			}
//		}
	}


}

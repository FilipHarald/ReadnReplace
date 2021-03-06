package threads;

import buffer.BoundedBuffer;
import gui.Controller;

/**
 * A writer for the buffer
 * 
 * @author Filip
 *
 */
public class BufferWriter extends Thread {
	private Controller controller;
	private BoundedBuffer buffer;
	private int writtenLines;
	private String[] source;

	/**
	 * Creates the writer
	 * 
	 * @param buffer
	 * @param controller
	 * @param source
	 */
	public BufferWriter(BoundedBuffer buffer, Controller controller, String source) {
		this.controller = controller;
		this.buffer = buffer;
		writtenLines = 0;
		this.source = source.split("\n");
		controller.setNbrOfLines(this.source.length);
	}
	@Override
	public void run() {
		for(int i = 0; i < source.length; i++){
			try {
				buffer.write(source[i]);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
//		while(source.length > writtenLines){
//			if(buffer.write(source[writtenLines]))
//				writtenLines++;
//		}
		System.out.println("WRITER FINISHED");
	}
}

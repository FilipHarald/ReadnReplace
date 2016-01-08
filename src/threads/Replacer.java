package threads;

import buffer.BoundedBuffer;
import gui.Controller;

/**
 * A class used to replace substrings in the buffer.
 * @author Filip
 *
 */
public class Replacer extends Thread {
	private Controller controller;
	private BoundedBuffer buffer;
	private String find;
	private String replace;
	
	private boolean writerIsDone;

	/**
	 * Creates the replacer 
	 * 
	 * @param buffer
	 * @param controller
	 * @param find
	 * @param replace
	 */
	public Replacer(BoundedBuffer buffer, Controller controller, String find, String replace) {
		this.controller = controller;
		this.buffer = buffer;
		this.find = find;
		this.replace = replace;
		writerIsDone = false;
	}

	@Override
	public void run() {
		while(!writerIsDone){
			buffer.replace(find, replace);
		}
		System.out.println("REPLACER FINISHED");
	}

	/**
	 * Used to stop the replacer-loop
	 */
	public void readerIsDone() {
		writerIsDone = true;
	}

}

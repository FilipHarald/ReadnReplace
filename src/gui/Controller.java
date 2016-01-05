package gui;

import buffer.BoundedBuffer;
import threads.*;

public class Controller {
//	private BoundedBuffer buffer;
//	private BufferWriter writer;
//	private BufferReader reader;
//	private Replacer replacer;
	
	private GUIMonitor gui;

	public Controller(GUIMonitor test) {
		gui = test;
	}
	
	public void startThreads(String find, String replace, boolean notify){
		BoundedBuffer buffer = new BoundedBuffer(this, 10, notify);
		new BufferWriter(buffer, this).start();
		new Replacer(buffer, this, find, replace).start();
		new BufferReader(buffer, this).start();
	}

	public boolean checkIfReplace(String line, int i, String find) {
		
		return false;
	}

}

package gui;

import buffer.BoundedBuffer;
import threads.*;

public class Controller {
//	private BoundedBuffer buffer;
//	private BufferWriter writer;
//	private BufferReader reader;
	private Replacer replacer;
	
	private int nbrOfLines;
	
	private GUIMonitor gui;

	public Controller(GUIMonitor test) {
		gui = test;
	}
	
	public void startThreads(String find, String replace, boolean notify, String source){
		BoundedBuffer buffer = new BoundedBuffer(this, 10, notify);
		new BufferWriter(buffer, this, source).start();
		replacer = new Replacer(buffer, this, find, replace);
		replacer.start();
		new BufferReader(buffer, this).start();
	}

	public boolean checkIfReplace(String line, int i, String find) {
		return gui.checkIfReplace(line, i, find);
	}
	
	public void setNbrOfLines(int nbr){
		nbrOfLines =nbr;
	}
	
	public int getNbrOfLines(){
		return nbrOfLines;
	}
	
	public void stopReplacer(){
		replacer.writerIsDone();
	}

	public void writeToDest(String line) {
		gui.appendDestText(line);
	}

	public void setReplaceCounter(int i) {
		gui.setReplaceCounter(i);
	}

}

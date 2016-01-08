package gui;

import buffer.BoundedBuffer;
import threads.*;

/**
 * Coordinates communication between threads, buffer and GUI
 * 
 * @author Filip
 *
 */
public class Controller {
	private Replacer replacer;
	
	private int nbrOfLines;
	
	private GUIMonitor gui;

	/**
	 * creates controller.
	 * 
	 * @param test
	 */
	public Controller(GUIMonitor test) {
		gui = test;
	}
	
	/**
	 * initializes buffer and starts the threads
	 * 
	 * @param find
	 * @param replace
	 * @param notify
	 * @param source
	 */
	public void startThreads(String find, String replace, boolean notify, String source){
		BoundedBuffer buffer = new BoundedBuffer(this, 10, notify);
		new BufferWriter(buffer, this, source).start();
		replacer = new Replacer(buffer, this, find, replace);
		replacer.start();
		new BufferReader(buffer, this).start();
	}

	/**
	 * confirms with the user (via the GUI) if the current word should be replaced.
	 * 
	 * @param line
	 * @param i
	 * @param find
	 * @return
	 */
	public boolean checkIfReplace() {
		return gui.checkIfReplace();
	}
	
	/**
	 * sets the number of lines for the current text
	 * 
	 * @param nbr
	 */
	public void setNbrOfLines(int nbr){
		nbrOfLines =nbr;
	}
	
	/**
	 * returns the number of lines of the text.
	 * 
	 * @return
	 */
	public int getNbrOfLines(){
		return nbrOfLines;
	}
	
	/**
	 * called by the reader to flag that it's done. 
	 */
	public void stopReplacer(){
		replacer.readerIsDone();
	}

	/**
	 * writes the specified line to the destination text in the gui 
	 * 
	 * @param line
	 */
	public void writeToDest(String line) {
		gui.appendDestText(line);
	}

	/**
	 * sets the replacecounter to the specified int
	 * 
	 * @param i
	 */
	public void setReplaceCounter(int i) {
		gui.setReplaceCounter(i);
	}

}

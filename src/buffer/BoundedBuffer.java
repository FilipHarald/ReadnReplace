package buffer;

import gui.Controller;

/**
 * A thread-safe bounded buffer 
 * 
 * @author Filip
 *
 */
public class BoundedBuffer {
	private Controller controller;
	
	private String[] buffer;
	private BufferStatus[] status;
	
	private int max; 
	private int writePos, readPos, findPos;
	
	private int replacedCount;
	
	private boolean notify;
	
	private Object lock;
	
	/**
	 * Creates a bounded buffer
	 * 
	 * @param c
	 * @param max theMaxSize of the buffer
	 * @param notify
	 */
	public BoundedBuffer(Controller c, int max, boolean notify){
		controller = c;
		this.max = max;
		initBuffer();
		writePos = 0;
		readPos = 0;
		findPos = 0;
		replacedCount = 0;
		this.notify = notify;
		lock = new Object();
	}
	
	/**
	 * Writes the string to the buffer.
	 * 
	 * @param line
	 * @return true if it was succesfully written. false if not.
	 */
	public boolean write(String line){
		if(status[writePos] == BufferStatus.EMPTY){
			synchronized(lock){
				System.out.println("Writer lock                				|||||||||||||||| BoundedBuffer");
				System.out.println(line);
				buffer[writePos] = line;
				status[writePos] = BufferStatus.CHECKED;
				writePos = (writePos + 1) % max;
				return true;
			}				
		}else{
			System.out.println("Trying to write, buffer status not EMPTY 	|||||||||||||||| BoundedBuffer");
			return false;
		}
	}
	

	/**
	 * Replaces the string find with replacement. Asks the user for replacement if notify is true.
	 * 
	 * @param find
	 * @param replacement
	 */
	public void replace(String find, String replacement){
		if(status[findPos] == BufferStatus.CHECKED){
			synchronized(lock){
				System.out.println("Replace lock                			|||||||||||||||| BoundedBuffer");
				if(find.length() > 0){
					boolean replace = checkIfReplace(buffer[findPos].indexOf(find));
					while(replace){
						buffer[findPos] = buffer[findPos].replaceFirst(find, replacement);
						replace = checkIfReplace(buffer[findPos].indexOf(find));
					}
				}
				status[findPos] = BufferStatus.NEW;
				findPos = (findPos + 1) % max;					
			}				
		}else{
			System.out.println("Trying to replace, buffer status not CHECKED |||||||||||||||| BoundedBuffer");
		}
	}
	
	/**
	 * Returns the next string in the buffer.
	 * 
	 * @return The next String if there is one. Otherwise it returns null.
	 */
	public String read(){
		String temp = null;
		if(status[readPos] == BufferStatus.NEW){
			synchronized(lock){
				System.out.println("Reader lock                				|||||||||||||||| BoundedBuffer");
				temp = buffer[readPos];
				status[readPos] = BufferStatus.EMPTY;
				readPos = (readPos + 1) % max;
			}				
		}else{
			System.out.println("Trying to read, buffer status not NEW 		|||||||||||||||| BoundedBuffer");
		}
		return temp;
	}
	
	/**
	 * Checks if the current word/String exists in the String and if it should be replaced.
	 * 
	 * @param string
	 * @param indexOf
	 * @param find
	 * @return true if the word exists and it should be replaced (either by user approval or default)
	 */
	private boolean checkIfReplace(int indexOf) {
		boolean replace = false;
		if(indexOf > -1){
			if(notify){				
				replace = controller.checkIfReplace();
			}else{
				replace = true;
			}
		}
		if(replace) controller.setReplaceCounter(++replacedCount);
		return replace;
	}

	/**
	 * initializes the buffer.
	 */
	private void initBuffer(){
		buffer = new String[max];
		status = new BufferStatus[max];
		for(int i = 0; i < max; i++){
			buffer[i] = "";
			status[i] = BufferStatus.EMPTY;
		}
	}
	
}

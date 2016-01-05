package buffer;

import gui.Controller;

public class BoundedBuffer {
	private Controller controller;
	
	private String[] buffer;
	private BufferStatus[] status;
	
	private int max; 
	private int writePos, readPos, findPos;
	
	private boolean notify;
	
	private Object lock;
	
	public BoundedBuffer(Controller c, int max, boolean notify){
		controller = c;
		this.max = max;
		initBuffer();
		writePos = 0;
		readPos = 0;
		findPos = 0;
		this.notify = notify;
		lock = new Object();
	}
	
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
	
	public boolean replace(String find, String replacement){
		if(status[findPos] == BufferStatus.CHECKED){
			synchronized(lock){
				System.out.println("Replace lock                			|||||||||||||||| BoundedBuffer");
				//mark current string to replace and ask controller if it wish to continue
				boolean replace = checkIfReplace(buffer[findPos], buffer[findPos].indexOf(find), find);
				while(replace){
					buffer[findPos].replaceFirst(find, replacement);
					replace = checkIfReplace(buffer[findPos], buffer[findPos].indexOf(find), find);
				}
				status[findPos] = BufferStatus.NEW;
				findPos = (findPos + 1) % max;
				return true;
			}				
		}else{
			System.out.println("Trying to replace, buffer status not CHECKED |||||||||||||||| BoundedBuffer");
			return false;
		}
	}
	
	/**
	 * @return The next String if there is one. Otherwise it returns null.
	 */
	public String read(){
		String temp = null;
		if(status[writePos] == BufferStatus.NEW){
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
	 * @return
	 */
	private boolean checkIfReplace(String string, int indexOf, String find) {
		boolean replace = false;
		if(indexOf > -1){
			if(notify){				
				replace = controller.checkIfReplace(string, indexOf, find);
			}else{
				replace = true;
			}
		}
		return replace;
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

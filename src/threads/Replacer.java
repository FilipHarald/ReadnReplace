package threads;

import buffer.BoundedBuffer;
import gui.Controller;

public class Replacer extends Thread {
	private Controller controller;
	private BoundedBuffer buffer;
	private String find;
	private String replace;

	public Replacer(BoundedBuffer buffer, Controller controller, String find, String replace) {
		this.controller = controller;
		this.buffer = buffer;
		this.find = find;
		this.replace = replace;
	}

	@Override
	public void run() {
		while(!Thread.interrupted()){
			buffer.replace(find, replace);
			//TODO: HUR SKA DEN SLUTA!?!??!
		}
	}

}

package threads;

import buffer.BoundedBuffer;
import gui.Controller;

public class BufferWriter extends Thread {
	private Controller controller;
	private BoundedBuffer buffer;
	private int writtenLines;
	private String[] source;

	public BufferWriter(BoundedBuffer buffer, Controller controller, String source) {
		this.controller = controller;
		this.buffer = buffer;
		writtenLines = 0;
		this.source = source.split("\n");
		controller.setNbrOfLines(this.source.length);
	}
	@Override
	public void run() {
		while(source.length > writtenLines){
			if(buffer.write(source[writtenLines]))
				writtenLines++;
		}
		System.out.println("WRITER FINISHED");
	}
}

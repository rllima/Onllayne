package file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class FileComm  implements Iterable<byte[]>, Iterator<byte[]>{
	
	private File file;
	private long size;
	private long counter;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private byte[] buffer;
	private double percent;
	
	public FileComm(String path, int bufferSize) throws FileNotFoundException{
		this.file = new File(path);
		this.size = this.file.length();
		this.counter = 0;
		this.fis = new FileInputStream(this.file);
		this.bis = new BufferedInputStream(fis);
		this.buffer = new byte[bufferSize];
		this.percent = 0;
	}
	
    public Iterator<byte[]> iterator() {
		
		try {
			this.size = this.file.length();
		    this.counter = 0;
			this.fis = new FileInputStream(this.file);
			this.bis = new BufferedInputStream(fis);
			this.percent = 0.0;
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		return this;
	}
	
	public boolean hasNext() {
		if(this.counter < this.getSize()) return true;
		return false;
	}

	public void EOF(){
		try {
			this.bis.close();
			this.fis.close();
		} catch (IOException e) {}
	}
	
	public byte[] next() {
		try {
			int size = bis.read(buffer);
			this.counter += size;
			this.percent = (double)((double)this.counter/(double)this.size)*100.0;
			return buffer;
		} catch (IOException e) {
			//e.printStackTrace();
		}
		return null;
	}

	public long getSize() {
		return this.size;
	}
	public double getPercent() {
		return this.percent;
	}
}
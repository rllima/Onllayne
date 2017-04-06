package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

import crypto.Cryptography;

public class FileReciev implements Runnable {

	private boolean isCanceled;
	private boolean isPaused;
	private String path;
	private String IP;
	private boolean control = false;
	private int port;
	private int portACK;
	private ACKReciev ack;
	private FileReceiver file;
	private long remainTime;
	private double rtt = 0;
	private long velocidade = 0;
	private String chatID;
	private long fileSize;
	
	
	public FileReciev(String path, String IP, String chatID, long fileSize, int port) throws UnknownHostException, IOException {
		this.fileSize = fileSize;
		this.isCanceled = false;
		this.isPaused = false;
		this.path = path;
		this.IP = IP;
		this.control = false;
		this.port = port;	
		this.portACK = port + 1;
		this.file = new FileReceiver(this.path, this.fileSize);
		this.chatID = chatID;
	}
	
	
	public void run() {
		try {
			Socket sock = new Socket(IP, this.port);
			BufferedOutputStream bosACK = new BufferedOutputStream(sock.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(sock.getInputStream());
			//this.ack = new ACKReciev(this.IP, this.portACK);
			//ACKReciev thread = new ACKReciev(this.IP, this.portACK);
			sock.setSoTimeout(20000);
			Cryptography crypt = new Cryptography(this.chatID);
			int size = 1, sizeA = 0;
			long[] mean = new long[5];
			long timeAcum = 0;
			long speed = 0, bytesTime = 0, total = 0;
			long init = System.currentTimeMillis();
			int pointer = 0, i = 0, p = 0;
			int bufferSize = 1024*1024;
			byte[] buffer = new byte[bufferSize];
			byte[] bufferAux = new byte[bufferSize];
			boolean flag = true;
			int n;
			
			/*byte[] data = new byte[32];
			int n = bis.read(data);
			sendingState(bosACK, "A");*/
			
			this.ack = new ACKReciev(this.IP, this.portACK);
			this.ack.start();
			//String fileSizeString = new String(Arrays.copyOf(data, n));
			//long fileSize = Long.parseLong(fileSizeString);
			byte[] text = new byte[bufferSize];
			int ite = 0;
			while (flag) {
				try {
					//if((System.currentTimeMillis()-ini) >= 21000 && cond) isCanceled = true;
					while (pointer < bufferSize && size > 0) {
						size = bis.read(buffer);
						if (size > 0) {
							sizeA += size;
							bytesTime += size;
						}
						for (i = 0; i < size && pointer < bufferSize; i++) {
							bufferAux[pointer++] = buffer[i];
						}
						
					}
					if (size > 0) {
						for (int j = 0; j < bufferSize; j++) {
							text[j] = bufferAux[j];
						}
					}
					
					byte[] texto = crypt.decryptFile(text);
					
					timeAcum = System.currentTimeMillis() - init;
					if (timeAcum >= 1000) {
						speed = getSpeed(bytesTime, timeAcum, mean, p);
						p++;
						//System.out.println("Velocidade = " + speed / 1000000 + " MBs/segundo");
						this.velocidade = speed/1000000;
						setRemainingTime(fileSize, total, speed);
						//System.out.println("Tempo restante: " + getRemainingTime() + " segundos");
						if (p > 4)
							p = 0;
						timeAcum = 0;
						bytesTime = 0;
						init = System.currentTimeMillis();
					}
					if(isCanceled()) {
						size = -1;
						file.deleteFile();
						sendingState(bosACK, "C");
						break;
					} else if(isPaused()) {
						//ini = System.currentTimeMillis();
						while(isPaused()) {
							if(isCanceled()){
								size = -1;
								file.deleteFile();
								sendingState(bosACK, "C");
								break;
							}
							sendingState(bosACK, "P");
						}
						if(!isCanceled())
							sendingState(bosACK, "R");
					} else {
						sendingState(bosACK, "A");
					}
					if (size <= 0)
						flag = false;
					else {
						ite++;
						total += bufferSize;
						if (total > fileSize) {
							file.append(texto, 0, (int) (fileSize % bufferSize));
						} else {
							file.append(texto, 0, bufferSize);
						}
					}
					if (sizeA > bufferSize) {
						sizeA %= bufferSize;
						pointer = 0;
						for (; i < size; i++) {
							bufferAux[pointer++] = buffer[i];
						}
					} else {
						sizeA = 0;
						pointer = 0;
					}
				} catch (SocketTimeoutException e) {
					control = true;
					if(total>=fileSize) flag = false;
					sock.close();
					while(true){
						try{
							while(!netIsAvailable()) {}
							sock = new Socket(IP, this.port);
							sock.setSoTimeout(20000);
							pointer = 0;
							bytesTime -= sizeA;
							sizeA = 0;
							bosACK = new BufferedOutputStream(sock.getOutputStream());
							bis = new BufferedInputStream(sock.getInputStream());
							bosACK.write((ite+"").getBytes());
							bosACK.flush();
							byte[] data1 = new byte[32]; 
							n = bis.read(data1);
							break;
						}catch(ConnectException e1){
						}
					}
				} catch (SocketException e) {
					control = true;
					if(total>=fileSize) flag = false;
					sock.close();
					while(true){
						try{
							while(!netIsAvailable()) {}
							sock = new Socket(IP, this.port);
							sock.setSoTimeout(20000);
							pointer = 0;
							bytesTime -= sizeA;
							sizeA = 0;
							bosACK = new BufferedOutputStream(sock.getOutputStream());
							bis = new BufferedInputStream(sock.getInputStream());
							bosACK.write((ite+"").getBytes());
							bosACK.flush();
							byte[] data1 = new byte[30]; 
							n = bis.read(data1);
							break;
						}catch(ConnectException e1){
						}
					}
				}
			}
			if(control) {
				sendingState(bosACK, "F");
			}
			ack.destroySock(); 
			this.ack.stop();
			bis.close();
			this.file.EOF();
			sock.close();
			//System.out.println("recieved");
		} catch (Exception e) {
		}
	}
	
    public double getPercent() {
		return this.file.getPercent();
	}
    

	private void sendingState(BufferedOutputStream bosACK, String str) throws IOException {
		bosACK.write((str).getBytes());
		bosACK.flush();
	}

	private long getSpeed(long bytesTime, long timeAcum, long[] medias, int i) {

		long speed = bytesTime / (timeAcum / 1000);
		long sum = 0;
		int n = 0;

		for (int j = 0; j < medias.length; j++) {
			if (medias[j] > 0) {
				sum += medias[j];
				n++;
			}
		}
		if (sum != 0)
			speed = (long) ((0.2 * speed) + (0.8 * (sum / n)));
		medias[i] = speed;
		return speed;
	}

	public long getRemainingTime() {
		return this.remainTime;
	}
	
	private void setRemainingTime(long fileSize, long total, long speed) {
		remainTime =  (fileSize - total) / speed;
	}
	
	public boolean netIsAvailable() {
		try {
			final URL url = new URL("http://www.google.com");
			final URLConnection conn = url.openConnection();
			conn.connect();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

	public boolean isCanceled() {
		return this.isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public boolean isPaused() {
		return this.isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}
	
	public long getVelocidade() {
		return this.velocidade;
	}
	
	public double getRTT() {
		return this.ack.getEstimatedRTT();
	}


	public int getPort() {
		return port;
	}
}
package file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Arrays;

public class ACKReciev extends Thread {
	private double alpha = 0.125;
	private double estimatedRTT;
	private double RTT;
	private Socket sock;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	private String IP;
	private int port;

	public ACKReciev(String IP, int port) throws UnknownHostException, IOException {
		this.estimatedRTT = 0;
		this.RTT = 0;
		this.IP = IP;
		this.port = port;
	}

	
	public void run() {
		try {
			this.sock = new Socket(IP, this.port);
			this.sock.setSoTimeout(20000);
			while(!sock.isConnected()){
				this.sock = new Socket(IP, this.port);
				this.sock.setSoTimeout(20000);
			}
			bis = new BufferedInputStream(sock.getInputStream());
			bos = new BufferedOutputStream(sock.getOutputStream());
			long timeInit = 0, timeACK = 0;
			
			while (true) {
				try {
					byte[] data = new byte[1];
					int size;

					size = bis.read(data);
					String ACK = new String(Arrays.copyOf(data, size));
					timeACK = System.nanoTime();

					if (timeInit != 0) {
						setRTT((int) (timeACK - timeInit));
						setEstimatedRTT(getRTT());
						//System.out.println("RTT = " + getRTT() / 1000000 + " milisegundos");
						//System.out.println("RTTEstimado = " + getEstimatedRTT() / 1000000 + " milisegundos");
					}

					if (ACK.equals("A")) {
						timeInit = System.nanoTime();
						bos.write("A".getBytes());
						bos.flush();
					}
				} catch (SocketException e) {
					this.sock.close();
					while (true) {
						try {
							while(!netIsAvailable()) {}
							this.sock = new Socket(IP, this.port);
							this.sock.setSoTimeout(20000);
							bis = new BufferedInputStream(sock.getInputStream());
							bos = new BufferedOutputStream(sock.getOutputStream());
							break;
						} catch (ConnectException f) {
						}

					}
				} catch (SocketTimeoutException e) {
					this.sock.close();
					while (true) {
						try {
							while(!netIsAvailable()) {}
							this.sock = new Socket(IP, this.port);
							this.sock.setSoTimeout(20000);
							bis = new BufferedInputStream(sock.getInputStream());
							bos = new BufferedOutputStream(sock.getOutputStream());
							break;
						} catch (ConnectException f) {
						}

					}
				}

			}

		} catch (IOException e) {
		}
	}
	

	public double getEstimatedRTT() {
		return this.estimatedRTT;
	}

	public double getRTT() {
		return this.RTT;
	}

	private void setRTT(int rTT) {
		this.RTT = rTT;
	}

	private void setEstimatedRTT(double sampleRTT) {
		this.estimatedRTT = ((this.estimatedRTT * (1 - alpha)) + (alpha * sampleRTT));
	}

	public void destroySock() throws IOException {
		this.sock.close();

	}
	
	private static boolean netIsAvailable() {
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
	
}
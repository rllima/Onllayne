package comm;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.UnknownHostException;
import java.net.Socket;

/**
 * Classe de comunicação em rede, criação de socket, recebimento e envio de dados.
 * @author lar
 */
public class Comm {
	
	private Socket socket;
	private BufferedInputStream bis;
	private BufferedOutputStream bos;
	
	/**
	 * Metodo construtor para a criação de uma comunicação 
	 * @param ip String - endereço IP
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public Comm(String ip, int port) throws UnknownHostException, IOException {
		this.socket = new Socket(ip, port);
		this.bis = new BufferedInputStream(socket.getInputStream());
		this.bos = new BufferedOutputStream(socket.getOutputStream());
	}
	
	/**
	 * Metodo que envia uma cadeia de bytes ao servidor
	 * @param b byte[] - conjunto de dados a serem enviados
	 * @throws IOException - exceção de entrada e saida de dados
	 */
	public void write(byte[] b) throws IOException {
		this.bos.write(b,0,b.length);
		this.bos.flush();
	}
	
	/**
	 * Metodo que envia uma cadeia de bytes ao servidor
	 * @param b byte[] - conjunto de dados a serem enviados
	 * @param off int - ponto inicial do envio de dados
	 * @param len int - comprimento do dado a ser enviado
	 * @throws IOException - exceção de entrada e saida de dados
	 */
	public void write(byte[] b, int off, int len) throws IOException {
		this.bos.write(b, off, len);
		this.bos.flush();
	}
	
	/**
	 * Metodo que recebe um conjunto de dados do servidor.
	 * @return byte[] - conjunto de dados recebido
	 * @throws IOException - exceção de entrada e saida de dados
	 */
	public byte[] read() throws IOException{
		byte[] code = new byte[5];
		this.bis.read(code, 0, 5);
		int len = this.bis.available();
		byte[] b = new byte[5+len];
		for(int i=0 ; i<5 ; i++) b[i] = code[i];
		this.bis.read(b, 5, len);
		return b;
	}
	
	/**
	 * Metodo que encerra a conexão do socket.
	 * @throws IOException - exceção de entrada e saida de dados
	 */
	public void end() throws IOException{
		bis.close();
		bos.close();
		socket.close();
	}
}
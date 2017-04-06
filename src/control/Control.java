package control;

import basic.Chat;
import basic.Date;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.DefaultListModel;

import basic.User;
import comm.Comm;
import crypto.Cryptography;
import crypto.CryptographyException;
import gui.GUI;
import repository.RepositoryChat;

public class Control extends Thread {
	
	/**
	 * classe que implementa uma relação de cada comando texto com um arquivo
	 * @author Lucas Alves Rufino
	 */
	public class Set {
		public byte[] b;		//comando de texto
		public String code;		//Codigo o pedido
		public File file;	 	//arquivo associado
		
		/**
		 * Metodo cosntrutor de par com file associado para comunicação de arquivos.
		 * @param b byte[] - conjunto de texto associado
		 * @param file File - arquivo anexado
		 */
		public Set(byte[] b, String code, File file){
			this.b = b;
			this.code = code;
			this.file = file;
		}
		
		/**
		 * Metodo cosntrutor de par com file null associado para comunicação de texto.
		 * @param b byte[] - conjunto de texto associado
		 */
		public Set(byte[] b, String code){
			this(b, code, null);
		}
	}
	
	/**
	 * Classe que implementa uma comunicação do tipo envio e resposta.
	 * @author Lucas Alves Rufino
	 */
	public class SimpleSend extends Thread {
		
		private byte[] b;
		private String code;
		
		/**
		 * Metodo construtor para enviar uma mensagem simples
		 * @param b byte[] - conjunto de arquivos associados.
		 */
		public SimpleSend(byte[] b, String code){
			this.b = b;
			this.code = code;
			this.start();
		}
		
		/**
		 * Metodo de envio e recebimento de forma autonoma.
		 */
		public void run(){
			try {
				Comm comm = new Comm(ip, port);
				comm.write(b);
				bq.put(new Set(comm.read(), this.code));
				comm.end();
			} catch (UnknownHostException e) {
				//e.printStackTrace();
			} catch (IOException e) {
				//e.printStackTrace();
			} catch (InterruptedException e) {
				//e.printStackTrace();
			}
		}
	}
	
	private RepositoryChat repChat;
	private LinkedBlockingQueue<Set> bq;
	private String ip;
	private int port;
	private GUI gui;
	private User user;
	
	/**
	 * Metodo construtor da classe control
	 * @param ip String - codigo de ip em uso para conexão.
	 * @param port int  - cogido de porta para conexão.
	 */
	public Control(String ip, int port, GUI gui, RepositoryChat repChat){
		this.repChat = repChat;
		this.user = null;
		this.ip = ip;
		this.port = port;
		this.gui = gui;
		this.bq = new LinkedBlockingQueue<Set>();
		this.start();
	}
	
	//Metodos Getters////////////////////
	public String getIp() {
		return ip;
	}
	
	public int getPort() {
		return port;
	}
	
	//Metodos Setters////////////////////
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Metodo que faz uma requisicao ao servidor de cadastro de usuario  
	 * @param name String -  nome do usuario
	 * @param lastName String - sobrenome do usuario
	 * @param email String - email do usuario
	 * @param nick String - nick que identificara o usuario
	 * @param password String - senha
	 * @param day int - dia do nascimento do usuario
	 * @param mouth int - mes do nascimento do usuario
	 * @param year int - ano do nascimento do usuario
	 * @param gender char- sexo
	 * @throws IOException - Exceção de entrada e saida
	 * @throws InterruptedException -  Excecao de interrupcao
	 * @throws UserExist - excecao para nick ja existente 
	 */
	public void registerUser(String name, String lastName, String email, String nick, String password, 
								int day, int month, int year, char gender) {
		
		StringBuffer sb = new StringBuffer();
		sb.append("$ctdu\n");
		sb.append(name + "\n");
		sb.append(lastName + "\n");
		sb.append(nick + "\n");
		sb.append(password + "\n");
		sb.append(email + "\n");
		sb.append(gender + "\n");
		sb.append(day+ "\n");
		sb.append(month + "\n");
		sb.append(year + "\n");
		sb.append("$term\n");
		

		new SimpleSend(sb.substring(0).getBytes(), "$ctdu");
	}
	
	public void logIn(String nickname, String password) {

		StringBuffer sb = new StringBuffer();
		sb.append("$logi\n");
		sb.append(nickname + "\n");
		sb.append(password + "\n");
		sb.append("$term\n");

		new SimpleSend(sb.substring(0).getBytes(), "$logi");
	}
	
	public void listUserRep() {
		StringBuffer sb = new StringBuffer();
		sb.append("$lstu\n");
		new SimpleSend(sb.substring(0).getBytes(), "$lstu");
	}
	
	public void registerChatPersonal(String nickname) {

		StringBuffer sb = new StringBuffer();
		sb.append("$ctdp\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append(nickname + "\n");
		sb.append("$term/n");
		new SimpleSend(sb.substring(0).getBytes(), "$ctdp");
	}
	
	public void registerChatGroup(String name, String[] users) {

		StringBuffer sb = new StringBuffer();
		sb.append("$ctdg\n");
		sb.append(name + "\n");
		sb.append(this.user.getNickname() + "\n");
		for(int i=0, len=users.length ; i<len ; i++){
			sb.append(users[i] + "\n");
		}
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$ctdg");
	}
	
	public void push(String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("$push\n");
		sb.append(id + "\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$push");
		
	}
	
	public void pushUser() {
		
		StringBuffer sb = new StringBuffer();
		sb.append("$pshu\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$pshu");
	}
	
	public void sendMsg(String id, String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("$sndm\n");
		sb.append(id + "\n");
		sb.append(this.user.getNickname() + "\n");
		Cryptography cy = new Cryptography(id);
		byte[] b = new byte[0];
		try {
			b = cy.encryptMessage(msg);
		} catch (CryptographyException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		for(int i=0, len=b.length ; i<len ; i++){
			sb.append(b[i] + " ");
		}
		sb.append("\n$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$sndm");
	}
	
	public void seenMsg(String id){
		StringBuffer sb = new StringBuffer();
		sb.append("$senm\n");
		sb.append(id + "\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$senm");
	}
	
	public void initUpload(String id, String filename, long fileSize){
		StringBuffer sb = new StringBuffer();
		sb.append("$flui\n");
		sb.append(id +"\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append(filename + "\n");
		sb.append(fileSize + "\n");
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$flui");
	}
	
	public void initDownload(String id, String filename){
		StringBuffer sb = new StringBuffer();
		sb.append("$fldi\n");
		sb.append(id +"\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append(filename + "\n");
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$fldi");
	}
	
	public void endDownload(String id, String filename, int port) {
		StringBuffer sb = new StringBuffer();
		sb.append("$flde\n");
		sb.append(id + "\n");
		sb.append(this.user.getNickname() + "\n");
		sb.append(filename + "\n");
		sb.append(port + "\n");
		sb.append("$term\n");
		new SimpleSend(sb.substring(0).getBytes(), "$flde");
	}
	
	public void run(){
		boolean make = true;
		
		while(make){
			if(bq.size() > 0){
				Set set = bq.poll();
				byte[] b = Arrays.copyOfRange(set.b, 0, 5);
				String code = new String(b);
				switch(set.code + code){
					case "$ctdu$done" : this.gui.singInOK();
								  		break;
					case "$ctdu$exc1" : this.gui.singInExc1();
								  		break;
					case "$logi$done" : this.controlLogInDone(set.b);
			  							break;
					case "$logi$exc1" : this.gui.logInException1();
										break;
					case "$logi$exc2" : this.gui.logInException2();
										break;
					case "$lstu$done" : this.controllistUserRep(set.b);
										break;
					case "$ctdp$done" : this.controlRegisterChatPersonal(set.b);
										break;
					case "$ctdp$exc1" : this.gui.registerChatPersonalException1();
										break;
					case "$ctdg$done" : this.controlRegisterChatGroup(set.b);
										break;
					case "$ctdg$exc1" : this.gui.registerChatGroupException1();
										break;
					case "$push$done" : this.controlPush(set.b);
										break;
					case "$pshu$done" : this.controlPushUser(set.b);
										break;
					case "$sndm$done" : this.controlSendMessage(set.b);
										break;
					case "$senm$done" : this.controlSeenMessage(set.b);
										break;
					case "$flui$done" : this.controlUploadFile(set.b);
										break;
					case "$fldi$done" : this.controlDownloadFile(set.b);
										break;
					case "$flde$done" : this.controlDownloadEnd(set.b);
				}
			}
		}
	}
	
	private void controlDownloadEnd(byte[] b) {}

	private void controlDownloadFile(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		this.gui.downloadFile(in.nextLine(), in.nextLine(), in.nextLong(), in.nextInt());
		in.close();
	}

	private void controlUploadFile(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		int port = in.nextInt();
		this.gui.uploadFile(port);
	}

	private void controlPushUser(byte[] b) {
		
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		while(true){
			String id = in.nextLine();
			if(id.equals("$term")) break;
			this.user.insertChat(id);
		}
		in.close();
	}

	private void controlSeenMessage(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		Chat chat = this.repChat.search(in.nextLine());
		if(chat != null){
			chat.setSeen(true);
			this.repChat.update(chat);
			in.close();
		}
	}

	private void controlSendMessage(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		String id = in.nextLine();
		Chat chat = this.repChat.search(id);
		chat.setSent(in.nextLine().charAt(0) == 't');
		chat.setSeen(in.nextLine().charAt(0) == 't');
		HashSet<String> hs = new HashSet<String>();
		int numFile = in.nextInt();
		in.nextLine();
		for(int i=0 ; i<numFile ; i++){
			hs.add(in.nextLine());
		}
		chat.setFiles(hs);
		Cryptography cy = new Cryptography(chat.getId());
		while (true) {
			String str = in.nextLine();
			if (str.equals("$term")) break;
			int count = 0;
			Scanner inByte = new Scanner(str);
			byte[] msgCryp = new byte[str.length()];
			while (inByte.hasNextByte()) {
				msgCryp[count++] = inByte.nextByte();
			}
			msgCryp = Arrays.copyOfRange(msgCryp, 0, count);
			try {
				String msg = cy.decryptMessage(msgCryp);
				if (!msg.trim().isEmpty()) chat.addMsg(msg + "\n");
			} catch (CryptographyException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			inByte.close();
		}
		this.repChat.update(chat);
		this.gui.sendMessage(chat.getSb().substring(0));
		in.close();
	}

	private void controlPush(byte[] b) {
		Scanner in = new Scanner(new String(b));
		System.out.println(new String(b));
		in.nextLine();
		String id = in.nextLine();
		Chat chat = this.repChat.search(id);
		if(chat != null){
			chat.setName(in.nextLine());
			chat.setSent(in.nextLine().charAt(0) == 't');
			chat.setSeen(in.nextLine().charAt(0) == 't');
			HashSet<String> hs = new HashSet<String>();
			int numFile = in.nextInt();
			in.nextLine();
			for(int i=0 ; i<numFile ; i++){
				hs.add(in.nextLine());
			}
			chat.setFiles(hs);
			Cryptography cy = new Cryptography(chat.getId());
			while(true){
				String str = in.nextLine();
				if(str.equals("$term")) break;
				int count = 0;
				Scanner inByte = new Scanner(str);
				byte[] msgCryp = new byte[str.length()];
				while(inByte.hasNextByte()){
					msgCryp[count++] = inByte.nextByte();
				}
				msgCryp = Arrays.copyOfRange(msgCryp, 0, count);
				try {
					String msg = cy.decryptMessage(msgCryp);
					if(!msg.trim().isEmpty()) chat.addMsg(msg + "\n");
				} catch (CryptographyException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				inByte.close();
			}
			this.repChat.update(chat);
		} else {
			chat = new Chat(in.nextLine(), id);
			chat.setSent(in.nextLine().charAt(0) == 't');
			chat.setSeen(in.nextLine().charAt(0) == 't');
			int numFile = in.nextInt();
			in.nextLine();
			for(int i=0 ; i<numFile ; i++){
				chat.insertFile(in.nextLine());
			}
			Cryptography cy = new Cryptography(chat.getId());
			while(true){
				String str = in.nextLine();
				if(str.equals("$term")) break;
				int count = 0;
				Scanner inByte = new Scanner(str);
				byte[] msgCryp = new byte[str.length()];
				while(inByte.hasNextByte()){
					msgCryp[count++] = inByte.nextByte();
				}
				msgCryp = Arrays.copyOfRange(msgCryp, 0, count);
				try {
					String msg = cy.decryptMessage(msgCryp);
					if(!msg.trim().isEmpty()) chat.addMsg(msg + "\n");
				} catch (CryptographyException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
				inByte.close();
			}
			this.repChat.insert(chat);
			this.user.insertChat(chat.getId());
		}
		in.close();
	}

	private void controlRegisterChatGroup(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		Chat chat = new Chat(in.nextLine(), in.nextLine());
		while(true){
			String str = in.nextLine();
			if(str.equals("$term")) break;
			chat.insertUser(str);
		}
		this.repChat.insert(chat);
		this.user.insertChat(chat.getId());
		this.gui.registerChatGroupDone();
		in.close();
	}

	private void controlRegisterChatPersonal(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		Chat chat = new Chat(in.nextLine());
		chat.setName(in.nextLine());
		chat.insertUser(chat.getName());
		chat.insertUser(this.user.getNickname());
		this.repChat.insert(chat);
		this.user.insertChat(chat.getId());
		this.gui.registerChatPersonalDone();
		in.close();
	}

	private void controllistUserRep(byte[] b) {
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		DefaultListModel<String> dlm = new DefaultListModel<String>();
		while(true){
			String str = in.nextLine();
			if(str.equals("$term")) break;
			dlm.addElement(str);
		}
		this.gui.listUserRepOK(dlm);
		in.close();
	}

	public void controlLogInDone(byte [] b){
		Scanner in = new Scanner(new String(b));
		in.nextLine();
		this.user = new User(in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), in.nextLine(), 
				in.nextLine().charAt(0), new Date(in.nextInt(), in.nextInt(), in.nextInt()));
		while(true){
			String str = in.nextLine();
			if(str.equals("$term")) break;
			this.user.insertChat(str);
		}
		this.gui.logInOK(user);
		new Push(this, this.user);
		in.close();
	}
	
	public void exception01(){
		//Enviar mensagem pra a GUI de nick já existente
	}
}

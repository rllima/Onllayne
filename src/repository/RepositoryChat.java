package repository;

import basic.Chat;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

/**
 * Classe respectiva ao repositorio das conversas.
 * @author Lucas Alves Rufino
 */
public class RepositoryChat implements Iterable<Chat> {
	
	public class IteratorChat implements Iterator<Chat> {
		private Chat[] clone;
		private int count;
		
		/**
		 * Metodo construtor da classe Iterator de chats.
		 * @param clone
		 */
		public IteratorChat(Chat[] clone){
			this.clone = clone;
			this.count = 0;
		}
		
		/**
		 * Metodo que indica na iteração se ainda existem elementos não acessados.
		 * @return boolean - Se TRUE, existe um proximo elemento a ser acessado.
		 */
		public boolean hasNext() {
			if(this.count < this.clone.length && this.clone[this.count] != null){
				return true;
			} else {
				this.clone = null;
				return false;
			}
		}

		/**
		 * Metodo que envia o proximo elemento da iteração.
		 * @return Chat - proximo objeto conversa.
		 */
		public Chat next() {
			return this.clone[this.count++];
		}
	}
	
	//Atributos ligados a configuração
	private String basepath;
	private int size;
	
	/**
	 * Construtor padrão do repositorio de conversas
	 * @param basepath String - caminho do diretorio onde estão armazenados os arquivos.
	 */
	public RepositoryChat(){}
	
	public String getBasepath() {
		return basepath;
	}

	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}

	/**
	 * Metodo que retorna o numero de elementos no repositorio
	 * @return int - tamanho do repositorio.
	 */
	public int size(){
		return this.size;
	}
	
	/**
	 * Metodo que insere uma nova conversa no repositorio
	 * @param chat Chat - nova conversa a ser adicionado
	 * @return boolean - se TRUE, procedimento realizado com sucesso.
	 */
	public synchronized boolean insert(Chat chat){
		boolean done = false;
		try {
			String path = this.basepath + chat.getId() + ".chat";
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(chat);
			oos.close();
			fos.close();
			done = true;
			this.size++;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return done;
	}
	
	/**
	 * Metodo que procura por uma conversa no repositorio e a retorna
	 * @param id String - identificador do chat
	 * @return Chat - chat com respectivo id respectivo. Obs.: se NULL, implica em não encontrado
	 */
	public synchronized Chat search(String id){
		Chat chat = null;
		boolean find = false;
		id = id + ".chat"; 
		String[] ids = new File(this.basepath).list();
		for(int i=0, len=ids.length ; i<len && !find ; i++){
			if(ids[i].equals(id)){
				find = true;
				try {
					FileInputStream fis = new FileInputStream(this.basepath + id);
					ObjectInputStream ois = new ObjectInputStream(fis);
					chat = (Chat) ois.readObject();
					ois.close();
					fis.close();
				} catch (FileNotFoundException e) {
				} catch (IOException e) {
				} catch (ClassNotFoundException e) {
				}
			}
		}
		return chat;
	}
	
	/**
	 * Metodo que remove um chat existente no repositorio
	 * @param id String - identificador do chat
	 * @return boolean - se TRUE implica em conversa removido com sucesso.
	 */
	public synchronized boolean remove(String id){
		boolean done = false;
		if(this.search(id) != null){
			File file = new File(this.basepath + id + ".chat");
			done = file.delete();
			this.size--;
		}
		return done;
	}
	
	/**
	 * Metodo que atualiza um chat no repositorio
	 * @param chat Chat - novo chat a ser adicionado
	 * @return boolean - se TRUE, procedimento realizado com sucesso.
	 */
	public synchronized boolean update(Chat chat){
		boolean done = false;
		if(this.remove(chat.getId())){
			done = this.insert(chat);
		}
		return done;
	}
	
	/**
	 * Metodo que produz a iteração de todo o repositorio de conversas
	 * @return IteratorChat - iterator do repositorio de conversas.
	 */
	public synchronized Iterator<Chat> iterator() {
		String[] ids = new File(this.basepath).list();
		Chat[] clone = new Chat[ids.length];
		for(int i=0, len=ids.length ; i<len; i++){
			clone[i] = this.search(ids[i].substring(0, ids[i].indexOf('.')));
		}
		return new IteratorChat(clone);
	}
}

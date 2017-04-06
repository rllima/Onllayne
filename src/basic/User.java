package basic;

import java.io.Serializable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Classe que representa um usuario com seus dados pessoais e a lista de conversas que ele possui.
 * @author Lucas Alves Rufino e Ullayne Fernandes
 */
public class User implements Serializable {
	
	private static final long serialVersionUID = 7744881595109314796L;
	
	//Atributos ligados a cadastro
	private String name;
	private String lastname; 
	private String nickname;
	private String password;
	private String email; 
	private char gender; 
	private Date date;
	
	//Atributos ligados a coleções
	private ConcurrentHashMap<String, Boolean> chats;
	
	/**
	 * Metodo construtor da classe usuario - metodo de cadastro.
	 * @param name String - nome do usuario
	 * @param lastname String - sobrenome do usuario
	 * @param nickname String - apelido do usuario (identificador unico);
	 * @param password String - password do usuario
	 * @param email	String - email do usuario
	 * @param gender char - sexo do usuario
	 * @param date Date - data de nascimento do usuario
	 * @param ip String ultimo ip do usuario
	 */
	public User(String name, String lastname, String nickname, String password, String email, 
				char gender, Date date) {
		this.name = name;
		this.lastname = lastname;
		this.nickname = nickname;
		this.password = password;
		this.email = email;
		this.gender = gender;
		this.date = date;
		chats = new ConcurrentHashMap<String, Boolean>();
	}

	//Metodos Getters////////////////////
	public String getName() {
		return name;
	}

	public String getLastname() {
		return lastname;
	}

	public String getNickname() {
		return nickname;
	}

	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}

	public char getGender() {
		return gender;
	}

	public Date getDate() {
		return date;
	}

	public ConcurrentHashMap<String, Boolean> getChats() {
		return chats;
	}

	//Metodos Setters////////////////////
	public void setName(String name) {
		this.name = name;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setChats(ConcurrentHashMap<String, Boolean> chats) {
		this.chats = chats;
	}

	//Metodo ligados ao tratamento de coleções////////////////////
	
	/**
	 * Metodo que adiciona um novo chat ao usuario.
	 * @param id String - id respectivo ao chat.
	 */
	public synchronized void insertChat(String id){
		this.chats.put(id, new Boolean(true));
	}
	
	/**
	 * Metodo que busca um chat no usuario e retorna sua existencia. Se TRUE, o chat existe. 
	 * @param id String - id respectivo ao chat.
	 */
	public synchronized boolean searchChat(String id){
		return this.chats.containsKey(id);
	}
	
	/**
	 * Metodo que remove um chat ao usuario. Se TRUE, foi removido com sucesso.
	 * @param id String - id respectivo ao chat.
	 */
	public synchronized boolean removeChat(String id){
		return (this.chats.remove(id) != null);
	}
	
	/**
	 * Metodo que fornece o itarator para o conjunto de chats do usuario.
	 * @return Iterator<String> - conjunto de id
	 */
	public synchronized Iterator<String> iteratorChat(){
		return this.chats.keySet().iterator();
	}
}

package basic;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

/**
/* Classe que modela um grupo. Possui o nome referente a ele e o nome de todos os membros,
 * uma lista de booleanos que indica as mensagens que foram, ou não, lidas,  uma lista de 
 * booleanos que indica as mensagens que foram nao lidas.
 * @author Lucas Alves Rufino.
 */
public class Chat implements Serializable {
	
	private static final long serialVersionUID = 6248346146171587319L;
	
	//Atributos ligados a cadastro.
	private String name;
	private String id;
	private boolean seen;
	private boolean sent;
	private boolean have;
	private StringBuffer sb;
	
	//Atributos ligados ao conjunto de usuarios do chat
	private HashSet<String> users;
	
	//Atributos ligados ao controle de arquivo;
	private HashSet<String> files;
	
	/**
	 * Metodo de criação de uma conversa no modelo de grupo.
	 * @param name String - nome do grupo
	 * @param id int - codigo de identificação da conversa.
	 */
	public Chat(String name, String id){
		this.id = id;
		this.name = name;
		this.seen = true;
		this.sent = true;
		this.have = false;
		this.users = new HashSet<String>();
		this.files = new HashSet<String>();
		this.sb = new StringBuffer();
	}
	
	/**
	 * Metodo de criaçao de uma conversa no modelo pesoal.
	 * @param id int - codigo de identificação da conversa
	 */
	public Chat(String id){
		this.id = id;
		this.name = "";
		this.seen = true;
		this.sent = true;
		this.have = false;
		this.users = new HashSet<String>();
		this.files = new HashSet<String>();
		this.sb = new StringBuffer();
	}
	
	public int getSizeUsers(){
		return this.users.size();
	}
	
	public int getSizeFiles(){
		return this.files.size();
	}
	
	public void addMsg(String msg){
		this.sb.append(msg);
		if(!msg.equals("")) this.have = true;
	}
	
	//Metodos Getters////////////////////

	public boolean getHave() {
		return have;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public HashSet<String> getUsers() {
		return users;
	}
	
	public HashSet<String> getFiles() {
		return files;
	}
	
	public boolean getSeen() {
		return seen;
	}
	
	public boolean getSent() {
		return sent;
	}
	
	public StringBuffer getSb() {
		this.have = false;
		return sb;
	}


	//Metodos Setters////////////////////
	
	public void setHave(boolean have) {
		this.have = have;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsers(HashSet<String> users) {
		this.users = users;
	}
	
	public void setFiles(HashSet<String> files) {
		this.files = files;
	}
	
	public void setSeen(boolean seen) {
		this.seen = seen;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
	//Metodos ligados a manipulação do conjunto de usuarios

	/**
	 * Metodo que adiciona um novo usuario ao grupo
	 * @param nickname String - Apelido do usuario
	 */
	public void insertUser(String nickname){
		this.users.add(nickname);
	}
	
	/**
	 * Metodo que procura um usuario no conjunto de usuarios do grupo e retorna seus dados. 
	 * @param nickname String - Apelido do usuario
	 * @return Data - Dados ligados ao respectivo usuario, NULL se não encontrado.
	 */
	public boolean searchUser(String nickname){
		return this.users.contains(nickname);
	}
	
	/**
	 * Metodo que remove um usuario do conjunto de usuarios ligados ao grupo.
	 * @param nickname String - apelido do usuario
	 * @return boolean - true caso removido com sucesso.
	 */
	public boolean removeUser(String nickname){
		return this.users.remove(nickname);
	}
	
	/**
	 * Metodo que envia o iterator com os usuarios do chat.
	 * @return Iterator<String> - iterator dos usuarios
	 */
	public Iterator<String> iteratorUser(){
		return this.users.iterator();
	}
	
	//Metodos ligados ao tratamento de arquivos
	
	/**
	 * Metodo para adicionar um novo arquivo na conversa
	 * @param file String - novo arquivo a ser adicionado na conversa.
	 */
	public void insertFile(String file){
		this.files.add(file);
	}
	
	/**
	 * Metodo que procura um arquivo na conversa.
	 * @param filename String - nome do arquivo a ser procurado.
	 * @return File - Dados ligados ao respectivo usuario, NULL se não encontrado.
	 */
	public boolean searchFile(String file){
		return this.files.contains(file);
	}
	
	/**
	 * Metodo que remove um arquivo do chat relacionado a um usuario
	 * @param filename String - nome do arquivo
	 * @return boolean - se TRUE, implica que o arquivo foi removido corretamente.
	 */
	public boolean removeFile(String file){
		return this.files.remove(file);
	}
	
	/**
	 * Metodo que envia o iterator com os usuarios do chat.
	 * @return Iterator<String> - iterator dos arquivos ligados a um usuario.
	 */
	public Iterator<String> iteratorFile(){
		return this.files.iterator();
	}
}

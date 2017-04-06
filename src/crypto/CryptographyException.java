package crypto;

/**
 * Classe que define um erro de criptografia
 * @author Lucas Alves Rufino
 */
public class CryptographyException extends Exception {

	private static final long serialVersionUID = -3253957290840272144L;
	private String msg;		//Mensagem de erro relacionada.
	private Exception e;
	
	/**
	 * Metodo construtor para geração de exceção de criptografia
	 * @param msg String - mensagem de erro complementar
	 * @param e Exception - exceção ocorrida de fato.
	 */
	public CryptographyException(String msg, Exception e){
		super("Erro de criptografia de " + msg + "!");
		this.msg = msg;
		this.e = e;
	}
	
	//Metodo Getter////////////////////
	public String getMsg(){
		return this.msg;
	}
	
	public Exception getException(){
		return this.e;
	}

}

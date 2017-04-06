package control;

import java.util.Iterator;
import basic.User;

public class Push extends Thread {
	
	private Control control;
	private User user;
	
	/**
	 * Metodo construtor do push para atualização de conversas 
	 * @param control
	 * @param user
	 */
	public Push(Control control, User user) {
		this.control = control;
		this.user = user;
		this.start();
	}
	
	/**
	 * Metodo runnable para execultar as funções do push de usuario.
	 */
	public void run(){
		while(true){
			this.control.pushUser();
			Iterator<String> it = this.user.iteratorChat();
			while(it.hasNext()){
				String id = it.next();
				if(!id.equals("")) {
					control.push(id);
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
	}
}

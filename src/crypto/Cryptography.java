package crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Cryptography {
	private String IV = "ABU78YITVJ982UP1";
    private String keyCrypto = "AErtu78lMt";
    
    /**
     * Metodo contrutor que recebe os atributos iniciais de uma criptografia
     * @param chatID String - id do chat para criação de chave criptografica.
     */
    public Cryptography(String chatID) {
    	this.keyCrypto = this.keyCrypto + chatID;
    }
    
    /**
     * Metodo responsavel por cifrar uma cadeia de byte respectiva a um arquivo.
     * @param file byte[] - cadeia de byte respectiva a um fragmento de arquivo.
     * @return byte[] - cadeia de byte de fragmento criptografado.
     * @throws CryptographyException - exceção de criptografia.
     */
    public byte[] encryptFile (byte[] file) throws CryptographyException {
		try {
			Cipher encrypt = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
			SecretKeySpec key = new SecretKeySpec(this.keyCrypto.getBytes("UTF-8"), "AES");
			encrypt.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(this.IV.getBytes("UTF-8")));
			return encrypt.doFinal(file);
		} catch (Exception e) {
			throw new CryptographyException("arquivo", e);
		}
    }
    
    /**
     * Metodo responsavel por decifrar uma cadeia de byte respectiva a um arquivo.
     * @param file byte[] - cadeia de byte respectiva a um fragmento de arquivo cifrado.
     * @return byte[] - cadeia de byte de fragmento decriptografado.
     * @throws CryptographyException - exceção de criptografia.
     */
    public byte[] decryptFile (byte[] file) throws CryptographyException {
        try {
			Cipher decrypt = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
			SecretKeySpec key = new SecretKeySpec(this.keyCrypto.getBytes("UTF-8"), "AES");
	        decrypt.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(this.IV.getBytes("UTF-8")));
	        return decrypt.doFinal(file);
		} catch (Exception e) {
			throw new CryptographyException("arquivo", e);
		}
    }
    
    /**
     * Metodo responsavel por cifrar uma mensagem.
     * @param message String - texto a ser criptografado.
     * @return byte[] - cadeia de byte de texto criptografado.
     * @throws CryptographyException - exceção de criptografia.
     */
    public byte[] encryptMessage (String message) throws CryptographyException {
        try {
        	Cipher encrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(this.keyCrypto.getBytes("UTF-8"), "AES");
			encrypt.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(this.IV.getBytes("UTF-8")));
			return encrypt.doFinal(message.getBytes("UTF-8"));
		} catch (Exception e) {
			throw new CryptographyException("mensagem", e);
		}
    }
    
    /**
     * Metodo responsavel por decifrar uma mensagem.
     * @param message byte[] - cadeia de byte do texto a ser decriptografado.
     * @return String - texto decriptografado.
     * @throws CryptographyException - exceção de criptografia.
     */
    public String decryptMessage(byte[] message) throws CryptographyException{
        try {
        	Cipher decrypt = Cipher.getInstance("AES/CBC/PKCS5Padding", "SunJCE");
            SecretKeySpec key = new SecretKeySpec(this.keyCrypto.getBytes("UTF-8"), "AES");
			decrypt.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(this.IV.getBytes("UTF-8")));
			return new String(decrypt.doFinal(message),"UTF-8");
		} catch (Exception e) {
			throw new CryptographyException("mensagem", e);
		}
    }
}
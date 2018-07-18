package com.braintobytes.serialspeed.cryptomania;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;




public class Cryptography {
	
	private class AESOBJECT {

		private final int AES_KEY_SIZE = 128;
		private final String padTransform = "AES/ECB/PKCS5Padding";
		private SecretKey pass;
		private byte[] out;
		private byte[] in;
		private String path;
		private Serializable obj;

		private AESOBJECT(String pass, String path, Serializable obj) throws InvalidKeyException, BadPaddingException, IOException, ClassNotFoundException{

			this.path = path;
			this.obj = obj;
			setUp(pass);
			encrypt();

		}
		
		private AESOBJECT(String pass, String path) throws InvalidKeyException, BadPaddingException, IOException, ClassNotFoundException{

			this.path = path;
			setUp(pass);
			decrypt();

		}

		private void setUp(String pass){
			
			try {
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				KeySpec spec = new PBEKeySpec(pass.toCharArray(), Hasher.fromHex(Hasher.numericalToString(Hasher.convertToNumerical(salt))), 65536, AES_KEY_SIZE);			//generateRandomCharsInBytes(16 - pass.getBytes().length)
				SecretKey tmp = factory.generateSecret(spec);
				this.pass = new SecretKeySpec(tmp.getEncoded(), "AES");
			} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
				System.out.println("Something was wrong during key generation " + e);
			}
		}


		private void encrypt() throws InvalidKeyException, BadPaddingException, IOException{

			try {

				Cipher aesCipher = Cipher.getInstance(padTransform);
				aesCipher.init(Cipher.ENCRYPT_MODE, pass);
				SealedObject sealed = new SealedObject(obj, aesCipher);
				
				FileOutputStream fileOut = new FileOutputStream(path);
				BufferedOutputStream buf = new BufferedOutputStream(fileOut);
				CipherOutputStream ciphyOut = new CipherOutputStream(buf, aesCipher);
				ObjectOutputStream objOut = new ObjectOutputStream(ciphyOut);
				objOut.writeObject(sealed);
				objOut.close();
				
			}catch(NoSuchAlgorithmException 
					| NoSuchPaddingException | IllegalBlockSizeException e) {

				System.out.println("Something was wrong in encrypting the data!" + e);
				e.printStackTrace();

			}
		}

		private void decrypt() throws BadPaddingException, InvalidKeyException, ClassNotFoundException, IOException{

			try{

				Cipher aesCipher = Cipher.getInstance(padTransform);
				aesCipher.init(Cipher.DECRYPT_MODE, pass);
				CipherInputStream ciphyIn = new CipherInputStream(new BufferedInputStream(new FileInputStream(path)), aesCipher);
				ObjectInputStream objIn = new ObjectInputStream(ciphyIn);
				SealedObject sealed = (SealedObject) objIn.readObject();
				obj = (Serializable) sealed.getObject(aesCipher);
				objIn.close();
				
			}catch(NoSuchAlgorithmException 
					| NoSuchPaddingException | IllegalBlockSizeException e) {

				System.out.println("Something was wrong in encrypting the data! " + e);

			}
		}

		public Serializable getObjResult() {
			return obj;
		}
	}
	
	

	protected enum TYPE {
		AESOBJ;
	}


	private TYPE type;
	private Object result;
	private String salt;



	protected Cryptography(TYPE type, String salt) {
		this.type = type;
		this.salt = salt;
	}


	protected void encryptObject(String pass, String path, Serializable obj) throws InvalidKeyException, BadPaddingException, ClassNotFoundException, IOException{

		switch(type){
		case AESOBJ:
			AESOBJECT aes = new AESOBJECT(pass, path, obj);
			break;
		}
	}


	protected void decryptObject(String pass, String path) throws InvalidKeyException, BadPaddingException, ClassNotFoundException, IOException{

		switch(type){
		case AESOBJ:
			AESOBJECT aes = new AESOBJECT(pass, path);
			result = aes.getObjResult();
			break;
		}
	}
	

	public static String toHex(byte[] hash){
		return DatatypeConverter.printHexBinary(hash);
	}

	public Object getResult() {
		return result;
	}
	
	protected static String createHash(String str) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Hasher.createHash(str.toCharArray());
	}
	
	protected static boolean checkSumHash(String hash, String pass) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String[] p = hash.split(":");
		byte[] salt = Hasher.fromHex(p[0]);
		byte[] testHash = Hasher.fromHex(p[1]);
		
		return Hasher.validate(pass.toCharArray(), testHash, salt);
	}
	
	protected static String createHash(byte[] str) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Hasher.createHash(new String(str).toCharArray());
	}
	
	protected static boolean checkSumHash(byte[] hash, byte[] pass) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String[] p = new String(hash).split(":");
		byte[] salt = Hasher.fromHex(p[0]);
		byte[] testHash = Hasher.fromHex(p[1]);
		
		return Hasher.validate(new String(pass).toCharArray(), testHash, salt);
	}
	
	protected static String createHash(char[] str) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Hasher.createHash(str);
	}
	
	protected static boolean checkSumHash(char[] hash, char[] pass) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String[] p = new String(hash).split(":");
		byte[] salt = Hasher.fromHex(p[0]);
		byte[] testHash = Hasher.fromHex(p[1]);
		
		return Hasher.validate(pass, testHash, salt);
	}
}
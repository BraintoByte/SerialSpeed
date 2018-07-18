package com.braintobytes.serialspeed.cryptomania;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;

import com.braintobytes.serialspeed.cryptomania.Cryptography.TYPE;
import com.braintobytes.serialspeed.exceptions.NullSaltException;




public class CryptoUtil {
	
	
	public enum CRYPTACTION{
		ENCRYPT,
		DECRYPT;
	}
	
	
	
	/**
	 * <br>
	 * Encrypt a serializable object in 128 bit AES using a PBKDF2 with SHA1 key, uses standard 128 bit length key size 
	 * and serialize an object
	 * </br>
	 * 
	 * <br>
	 * Object must implement {@link java.io.Serializable} to work
	 * </br>
	 * 
	 * <br>
	 * We suggest to use a generated serial version ID
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on Advanced Encryption Standard (AES): https://en.wikipedia.org/wiki/Advanced_Encryption_Standard}
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on serialization: https://en.wikipedia.org/wiki/Serialization}
	 * </br>
	 * 
	 * <br>
	 * </br>
	 * 
	 * @param password
	 * @param path
	 * @param object to encrypt
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws NullSaltException 
	 */
	
	public static void doEncryptObject(byte[] password, String path, Serializable obj, byte[] salt) throws InvalidKeyException, BadPaddingException, ClassNotFoundException, IOException, NullSaltException{
		
		if(salt == null){
			throw new NullSaltException();
		}
		
		Cryptography crypt = new Cryptography(TYPE.AESOBJ, new String(salt));
		crypt.encryptObject(new String(password), path, obj);
	}
	
	
	/**
	 * <br>
	 * Decrypt a serializable object in 128 bit AES using a PBKDF2 with SHA1 key, uses standard 128 bit length key size 
	 * and deserialize an object through path
	 * </br>
	 * 
	 * <br>
	 * Object must implement {@link java.io.Serializable} to work
	 * </br>
	 * 
	 * <br>
	 * We suggest to use a generated serial version ID
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on Advanced Encryption Standard (AES): https://en.wikipedia.org/wiki/Advanced_Encryption_Standard}
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on serialization: https://en.wikipedia.org/wiki/Serialization}
	 * </br>
	 * 
	 * <br>
	 * </br>
	 * 
	 * @param password
	 * @param path
	 * @return a {@link com.braintobytes.serialspeed.cryptomania.Cryptography#Cryptography(TYPE) Cryptography} object
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws NullSaltException 
	 */
	
	public static Cryptography doDecryptObject(byte[] password, String path, byte[] salt) throws InvalidKeyException, BadPaddingException, ClassNotFoundException, IOException, NullSaltException{
		
		if(salt == null){
			throw new NullSaltException();
		}
		
		Cryptography crypt = new Cryptography(TYPE.AESOBJ, new String(salt));
		crypt.decryptObject(new String(password), path);
		return crypt;
	}
	
	/**
	 * <br>
	 * Hashes using PBKDF2 with SHA1 lenght: 64 *8
	 * </br>
	 * <br>
	 * {@literal Get info on hashing:} https://en.wikipedia.org/wiki/PBKDF2
	 * https://en.wikipedia.org/wiki/Hash_function
	 * </br>
	 * <br>
	 * </br>
	 * 
	 * @param String to hash
	 * @return Hash
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateHash(byte[] toHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Cryptography.createHash(toHash).getBytes();
	}
	
	/**
	 * <br>
	 * Checksums a hash using PBKDF2 with SHA1 lenght: 64 *8
	 * </br>
	 * 
	 * 
	 * <br>
	 * {@link com.braintobytes.serialspeed.cryptomania.CryptoUtil#generateHash(byte[]) hashIt}
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on hashing:} https://en.wikipedia.org/wiki/PBKDF2
	 * https://en.wikipedia.org/wiki/Hash_function
	 * </br>
	 * 
	 * <br>
	 * </br>
	 * 
	 * @param What to check
	 * @param Hash
	 * @return True if valid
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkHash(byte[] toCheck, byte[] hash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Cryptography.checkSumHash(hash, toCheck);
	}
	
	/**
	 * <br>
	 * Hashes using PBKDF2 with SHA1 lenght: 64 *8
	 * </br>
	 * <br>
	 * {@literal Get info on hashing:} https://en.wikipedia.org/wiki/PBKDF2
	 * https://en.wikipedia.org/wiki/Hash_function
	 * </br>
	 * 
	 * @param String to hash
	 * @return Hash
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	
	public static byte[] generateHash(String toHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Cryptography.createHash(toHash).getBytes();
	}
	
	/**
	 * <br>
	 * Checksums a hash using PBKDF2 with SHA1 lenght: 64 *8
	 * </br>
	 * 
	 * <br>
	 * {@link com.braintobytes.serialspeed.cryptomania.CryptoUtil#generateHash(String)  hashIt}
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on hashing:} https://en.wikipedia.org/wiki/PBKDF2
	 * https://en.wikipedia.org/wiki/Hash_function
	 * </br>
	 * 
	 * <br>
	 * </br>
	 * 
	 * @param What to check
	 * @param Hash
	 * @return True if valid
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkHash(String toCheck, String hash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Cryptography.checkSumHash(hash, toCheck);
	}
	
	
	/**
	 * <br>
	 * Hashes using PBKDF2 with SHA1 lenght: 64 *8
	 * </br>
	 * <br>
	 * {@literal Get info on hashing:} https://en.wikipedia.org/wiki/PBKDF2
	 * https://en.wikipedia.org/wiki/Hash_function
	 * </br>
	 * 
	 * @param String to hash
	 * @return Hash
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] generateHash(char[] toHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Cryptography.createHash(toHash).getBytes();
	}
	
	/**
	 * <br>
	 * Checksums a hash using PBKDF2 with SHA1 lenght: 64 *8
	 * </br>
	 * 
	 * <br>
	 * {@link com.braintobytes.serialspeed.cryptomania.CryptoUtil#generateHash(char[]) hashIt}
	 * </br>
	 * 
	 * <br>
	 * {@literal Get info on hashing:} https://en.wikipedia.org/wiki/PBKDF2
	 * https://en.wikipedia.org/wiki/Hash_function
	 * </br>
	 * 
	 * <br>
	 * </br>
	 * 
	 * @param What to check
	 * @param Hash
	 * @return True if valid
	 * @throws InvalidKeySpecException
	 * @throws NoSuchAlgorithmException
	 */
	public static boolean checkHash(char[] toCheck, char[] hash) throws InvalidKeySpecException, NoSuchAlgorithmException {
		return Cryptography.checkSumHash(hash, toCheck);
	}
}
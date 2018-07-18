package com.braintobytes.serialspeed.cryptomania;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hasher {
	
	protected static boolean validate(char[] pass, byte[] hash, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException{

		byte[] tempHash = pbkdf2(pass, salt, 1000, hash.length * 8);
		
		return timeDefFunction(hash, tempHash);

	}
	
	protected static String createHash(char[] pass) throws InvalidKeySpecException, NoSuchAlgorithmException{
		
		byte[] salt = getSalt().getBytes();
		
		byte[] hash = pbkdf2(pass, salt, 1000, 64 * 8);
		
		return toHexAlternative(salt) + ":" + toHexAlternative(hash); 
		
	}
	
	protected static String getSalt() throws NoSuchAlgorithmException {
		
		SecureRandom rand = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		rand.nextBytes(salt);
		
		return Arrays.toString(salt);
		
	}

	protected static byte[] pbkdf2(final char[] pass, final byte[] salt, final int iter, final int lenght) throws InvalidKeySpecException, NoSuchAlgorithmException{

		return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(pass, salt, iter, lenght)).getEncoded();

	}


	protected static boolean timeDefFunction(byte[] x, byte[] y){

		int contrast = x.length ^ y.length;

		for (int i = 0; i < x.length && i < y.length; i++) {
			contrast |= x[i] ^ y[i];	
		}

		return contrast == 0;
	}
	
	protected static int[] convertToNumerical(String strToConvert){
		
		int[] result = new int[strToConvert.length()];
		
		for (int i = 0; i < strToConvert.length(); i++) {
			result[i] = (int) strToConvert.charAt(i);
		}
		
		return result;
	}
	
	protected static String numericalToString(int[] numericalToConvert){
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < numericalToConvert.length; i++) {
			sb.append(numericalToConvert[i]);
		}
		
		return sb.toString();
	}
	
	protected static byte[] fromHex(String hexa){
		
		byte[] bin = new byte[hexa.length() / 2];
		
		for (int i = 0; i < bin.length; i++) {
			
			bin[i] = (byte) Integer.parseInt(hexa.substring(2*i,  2*i+2), 16);
			
		}
		
		return bin;
	}
	
	protected static String toHexAlternative(byte[] byteArr){
		
		BigInteger big = new BigInteger(1, byteArr);
		String hexa = big.toString(16);
		int padding = (byteArr.length*2) - hexa.length();
		
		if(padding > 0){
			
			return String.format("%0" + padding + "d", 0) + hexa;
			
		}else{
			
			return hexa;
			
		}	
	}
}
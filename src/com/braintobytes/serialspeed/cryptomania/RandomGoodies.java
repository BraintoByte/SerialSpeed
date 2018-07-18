package com.braintobytes.serialspeed.cryptomania;

import java.security.SecureRandom;

public class RandomGoodies {

	private char[] alphabetLower = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	private char[] alphabetUpper = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();
	private char[] vowelsLower = "aeiou".toCharArray();
	private char[] vowelsUpper = "aeiou".toUpperCase().toCharArray();
	private char[] consonantsLower = "bcdfghjklmnpqrstvwxyz".toCharArray();
	private char[] consonantsUpper = "bcdfghjklmnpqrstvwxyz".toUpperCase().toCharArray();
	private char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
	private char[] allSymbols = "!\"#$%&\'()*+,-.'/\\:;<=>?@[\\]^_`{|}~".toCharArray();

	
	public byte[] generateRandomCharsInBytes(int bytesAmount, boolean vowels, boolean consonants, boolean number, boolean symbols){

		StringBuilder tempSb = new StringBuilder();
		SecureRandom rand = new SecureRandom();


		while(tempSb.toString().getBytes().length != bytesAmount) {

			int symbolLetterNbr = rand.nextInt(3);

			switch(symbolLetterNbr){
			case 0:
				if(vowels && consonants){

					if(rand.nextBoolean()){
						tempSb.append(alphabetLower[rand.nextInt(alphabetLower.length)]);
					}else{
						tempSb.append(alphabetUpper[rand.nextInt(alphabetLower.length)]);
					}

				}else{

					if(vowels){
						if(rand.nextBoolean()){
							tempSb.append(vowelsLower[rand.nextInt(vowelsLower.length)]);
						}else{
							tempSb.append(vowelsUpper[rand.nextInt(vowelsUpper.length)]);
						}
					}else{
						if(rand.nextBoolean()){
							tempSb.append(consonantsLower[rand.nextInt(consonantsLower.length)]);
						}else{
							tempSb.append(consonantsUpper[rand.nextInt(consonantsUpper.length)]);
						}
					}
				}
				break;
			case 1:
				if(number){
					tempSb.append(numbers[rand.nextInt(numbers.length)]);
				}
				break;
			case 2:
				if(symbols){
					tempSb.append(allSymbols[rand.nextInt(allSymbols.length)]);
				}
				break;
			}
		}
		return tempSb.toString().getBytes();
	}


	public char[] getVowelsLower() {
		return vowelsLower;
	}

	public char[] getVowelsUpper() {
		return vowelsUpper;
	}

	public char[] getConsonantsLower() {
		return consonantsLower;
	}

	public char[] getConsonantsUpper() {
		return consonantsUpper;
	}

	public char[] getAllSymbols() {
		return allSymbols;
	}

	public char[] getAlphabetLower() {
		return alphabetLower;
	}
	
	public char[] getAlphabetUpper() {
		return alphabetUpper;
	}
	
	public char[] getNumbers() {
		return numbers;
	}
}
package com.braintobytes.serialspeed.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;

import com.braintobytes.serialspeed.cryptomania.CryptoUtil;
import com.braintobytes.serialspeed.cryptomania.Cryptography;
import com.braintobytes.serialspeed.cryptomania.RandomGoodies;
import com.braintobytes.serialspeed.exceptions.NullSaltException;
import com.braintobytes.serialspeed.serialmania.SerialUtil;
import com.braintobytes.serialspeed.testing.testObjects.ObjectTest1;
import com.braintobytes.serialspeed.testing.testObjects.ObjectTest2;

public class IntegralTesting {

	private ObjectTest1 testObject1;
	private ObjectTest2 testObject2;
	private int encryptedOutCount;


	private boolean boolTest;
	private int intTest;
	private long longTest;
	private double doubleTest;
	private String stringTest;
	private char charTest;
	private String[] arrayOfStringsTest;
	private int[] intArrayTest;
	private List<String> stringListTest;
	private JButton jButtonTest;
	private UUID sessionId;
	private String separator = System.getProperty("file.separator");
	private RandomGoodies rand;
	private int min, max;
	private SecureRandom random;
	
	
	
	static int OBJECTSTOENCRYPTAMOUNT = 10;



	@Before
	public void makeVariables(){
		boolTest = false;
		intTest = 1;
		longTest = 111111;
		doubleTest = 2.0111225f;
		stringTest = "stringTest";
		charTest = 'c';
		arrayOfStringsTest = new String[] { "str1", "str2", "str3", "str4" };
		intArrayTest = new int[] { 1, 2, 3, 4, 5, 6, 7 };
		stringListTest = new ArrayList<>();

		for (int i = 0; i < arrayOfStringsTest.length; i++) {
			stringListTest.add(arrayOfStringsTest[i]);
		}

		jButtonTest = new JButton("jButtontest");

		testObject2 = new ObjectTest2(boolTest, intTest, longTest, stringTest, charTest, arrayOfStringsTest, intArrayTest, stringListTest, jButtonTest);
		testObject1 = new ObjectTest1(boolTest, intTest, longTest, doubleTest, stringTest, charTest, arrayOfStringsTest, intArrayTest, stringListTest, jButtonTest, testObject2);
		encryptedOutCount = 0;
		sessionId = UUID.randomUUID();

		rand = new RandomGoodies();
		min = 1;
		max = 200;
		random = new SecureRandom();
	}

	
	@Test
	public void testSerialization(){
		
		String path = System.getProperty("user.dir") + separator + "TestInputSerialization";
		
		ObjectTest1 objTest = (ObjectTest1) SerialUtil.deserializeObject(path + separator + "testObject1.test");
		
		
		assertFalse(objTest == null);
		assertTrue(objTest instanceof ObjectTest1);
		assertTrue(objTest.getTestObj() instanceof ObjectTest2);

		assertTrue(objTest.getDoubleTest() == testObject1.getDoubleTest());
		assertTrue(objTest.getIntTest() == testObject1.getIntTest());
		assertTrue(objTest.getLongTest() == testObject1.getLongTest());
		assertTrue(objTest.getCharTest() == testObject1.getCharTest());
		assertTrue(objTest.getStringTest().equals(testObject1.getStringTest()));
		assertTrue(assertListEquals(objTest.getStringListTest(), testObject1.getStringListTest()));
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization";
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest1";
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest1" + separator + "SubSubfolder" + separator;
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest1"  + separator + "SubSubfolder" + separator + "SubSubSubfolder" + separator;
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest1"  + separator + "SubSubfolder" + separator + "SubSubSubfolder" + separator + "SubSubSubSubFolder" + separator;
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest2";
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest2" + separator + "SubSubfolder" + separator;
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest2" + separator + "SubSubfolder" +  separator + "SubSubSubfolder" + separator;
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
		path = System.getProperty("user.dir") + separator + "TestInputSerialization" + separator + "SubfolderTest2"  + separator + "SubSubfolder" + separator + "SubSubSubfolder" + separator + "SubSubSubSubFolder" + separator;
		SerialUtil.serializeObject(path, "object", "test", testObject1);
		
	}

	@Test
	public void testEncryption(){

		Cryptography decryptObject;
		byte[] salt = rand.generateRandomCharsInBytes(100, true, true, true, true);
		
		long totalTimeEncryption = 0;
		long totalTimeDecryption = 0;
		long total = 0;
		

		while(encryptedOutCount != OBJECTSTOENCRYPTAMOUNT){
			try {

				byte[] pass = generateRandomCharsInBytes(14).getBytes();
				
				long startEncryption = System.currentTimeMillis();
				
				CryptoUtil.doEncryptObject(pass, System.getProperty("user.dir") + separator + "TestOutput" + separator + "EncryptedOutput" + encryptedOutCount, testObject1, salt);
				System.out.println("EncryptedOutput" + encryptedOutCount + " created!");

				long finishEncryption = System.currentTimeMillis();
				totalTimeEncryption += finishEncryption - startEncryption;
				
				long startDecryption = System.currentTimeMillis();
				
				decryptObject = CryptoUtil.doDecryptObject(pass, System.getProperty("user.dir") + separator + "TestOutput" + separator + "EncryptedOutput" + encryptedOutCount, salt);
				
				long finishDecryption = System.currentTimeMillis();
				totalTimeDecryption += finishDecryption - startDecryption;
				
				assertFalse(decryptObject == null);

				ObjectTest1 objTest = (ObjectTest1) decryptObject.getResult();

				assertFalse(objTest == null);
				assertTrue(objTest instanceof ObjectTest1);
				assertTrue(objTest.getTestObj() instanceof ObjectTest2);

				assertTrue(objTest.getDoubleTest() == testObject1.getDoubleTest());
				assertTrue(objTest.getIntTest() == testObject1.getIntTest());
				assertTrue(objTest.getLongTest() == testObject1.getLongTest());
				assertTrue(objTest.getCharTest() == testObject1.getCharTest());
				assertTrue(objTest.getStringTest().equals(testObject1.getStringTest()));
				assertTrue(assertListEquals(objTest.getStringListTest(), testObject1.getStringListTest()));
				total += totalTimeEncryption + totalTimeDecryption;
				assertTrue(checkFileInFolder(System.getProperty("user.dir") + "" + separator + "TestOutput", "" + separator + "EncryptedOutput" + encryptedOutCount));
				assertTrue(moveFileInFolder(System.getProperty("user.dir") + "" + separator + "TestOutput" + separator + "EncryptedOutput" + encryptedOutCount, 
						System.getProperty("user.dir") + separator + "TestOutput" + separator + "Completed" + separator + "EncryptedOutput" + encryptedOutCount + " pass " + new String(pass) + " session id " + sessionId
						+ " TotEncry " + (totalTimeEncryption) + "ms TotDecr " + (totalTimeDecryption) + "ms total " + (total/1000) + "s"));
				

				
				totalTimeEncryption = 0;
				totalTimeDecryption = 0;
				
				encryptedOutCount++;

			} catch (InvalidKeyException | BadPaddingException | ClassNotFoundException | IOException | NullSaltException e) {
				e.printStackTrace();
			}
		}
	}




	@Test
	public void testHashing(){
		
		try {
			
			int inclusive = max - min + 1;
			int exclusive = max - min;
			
			System.out.println("\n");
			System.out.println("Testing hash");
			System.out.println("\n\n\n\n");
			
			byte[] password = rand.generateRandomCharsInBytes(100, true, true, true, true);
			byte[] hash = CryptoUtil.generateHash(password);
			System.out.println("Using password: " + new String(password));
			System.out.println("Hash: " + new String(hash));
			
			System.out.println("\n");
			
			assertTrue(CryptoUtil.checkHash(password, hash));
			assertFalse(CryptoUtil.checkHash(hash, hash));
			assertFalse(CryptoUtil.checkHash("1234RandomPassword".getBytes(), hash));


			int i = 0;
			

			while(i < 100){
				byte[] passwordFake;

				if(random.nextBoolean()){
					passwordFake = rand.generateRandomCharsInBytes((random.nextInt(inclusive) + min), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
				}else{
					passwordFake = rand.generateRandomCharsInBytes((random.nextInt(inclusive) + min), true, true, true, true);
				}
				assertFalse(CryptoUtil.checkHash(passwordFake, hash));
				System.out.println("Passowrd at iteration " + i + " with password " + new String(passwordFake) + " does not check");
				i++;
			}
			
			assertTrue(CryptoUtil.checkHash(password, hash));
			System.out.println(new String(password) + " does check again!");
			
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testHashingRandomPasswordStructure(){
		try {
			
			System.out.println("\n");
			System.out.println("Testing hash with random passw generation structure");
			System.out.println("\n");
			
			int inclusive = max - min + 1;
			int exclusive = max - min;
			
			byte[] password = rand.generateRandomCharsInBytes(random.nextInt(inclusive) + min, random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
			byte[] hash = CryptoUtil.generateHash(password);
			System.out.println("Using password: " + new String(password));
			System.out.println("Hash: " + new String(hash));
			
			System.out.println("\n");
			
			assertTrue(CryptoUtil.checkHash(password, hash));
			assertFalse(CryptoUtil.checkHash(hash, hash));
			assertFalse(CryptoUtil.checkHash("1234RandomPassword".getBytes(), hash));


			int i = 0;
			

			while(i < 100){
				byte[] passwordFake;

				if(random.nextBoolean()){
					passwordFake = rand.generateRandomCharsInBytes((random.nextInt(inclusive) + min), random.nextBoolean(), random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
				}else{
					passwordFake = rand.generateRandomCharsInBytes((random.nextInt(inclusive) + min), true, true, true, true);
				}
				assertFalse(CryptoUtil.checkHash(passwordFake, hash));
				System.out.println("Passowrd at iteration " + i + " with password " + new String(passwordFake) + " does not check");
				i++;
			}
			
			assertTrue(CryptoUtil.checkHash(password, hash));
			System.out.println(new String(password) + " does check again!");
			
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	private boolean checkFileInFolder(String pathToFolder, String fileName){
		File folder = new File(pathToFolder);
		File[] listOfFiles = folder.listFiles();

		for(File file : listOfFiles){
			if(file.getAbsoluteFile().toString().contains(fileName)){
				return true;
			}
		}

		return false;
	}

	private boolean moveFileInFolder(String fromPath, String toPath){
		File file = new File(fromPath);

		if(file.renameTo(new File(toPath))){
			file.delete();
			System.out.println("File moved to " + toPath);
			return true;
		}

		System.out.println("Could not move file " + fromPath + " to " + toPath);
		return false;
	}


	private boolean assertListEquals(List<String> encrypted, List<String> original){

		Iterator<String> itEncrypted = encrypted.iterator();
		Iterator<String> itOriginal = original.iterator();

		while(itEncrypted.hasNext() && itOriginal.hasNext()){

			if((itEncrypted.hasNext() && !itOriginal.hasNext()) || (!itEncrypted.hasNext() && itOriginal.hasNext())){
				return false;
			}

			if(!itEncrypted.next().equals(itOriginal.next())){
				return false;
			}
		}
		return true;
	}
	
	private String generateRandomCharsInBytes(int bytesAmount){
		RandomGoodies rand = new RandomGoodies();
		return new String(rand.generateRandomCharsInBytes(bytesAmount, true, true, true, false));
	}
}
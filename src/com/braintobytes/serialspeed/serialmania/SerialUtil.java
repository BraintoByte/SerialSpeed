package com.braintobytes.serialspeed.serialmania;

public class SerialUtil {
	
	private static String fileSystemSeparator = System.getProperty("file.separator");
	
	public static void serializeObject(String pathToFile, String fileName, String fileExtension, Object obj){
		ObjectSerialization serial = new ObjectSerialization();
		
		if(!fileExtension.contains(".")){
			fileExtension = "." + fileExtension;
		}
		
		serial.serialize(pathToFile + fileSystemSeparator + fileName + fileExtension, obj);
	}
	
	public static Object deserializeObject(String path){
		ObjectSerialization serial = new ObjectSerialization();
		return serial.deserialize(path);
	}
	
	
	protected static String getFileSystemSeparator() {
		return fileSystemSeparator;
	}
}
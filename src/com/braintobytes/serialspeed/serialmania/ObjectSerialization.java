package com.braintobytes.serialspeed.serialmania;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ObjectSerialization {
	
	private Object obj;
	private String systemSeparator = SerialUtil.getFileSystemSeparator();
	
	
	public ObjectSerialization(Object obj) {
		this.obj = obj;
	}
	
	public ObjectSerialization() {}
	
	protected void serialize(String path, String objectName, String extension){
		
		try(FileOutputStream streamOut = new FileOutputStream(path + systemSeparator + objectName + "." + extension)){
			
			ObjectOutputStream out = new ObjectOutputStream(streamOut);
			out.writeObject(this.obj);
			out.flush();
			out.close();
			streamOut.flush();
			streamOut.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void serialize(String path){
		
		try(FileOutputStream streamOut = new FileOutputStream(path)){
			
			ObjectOutputStream out = new ObjectOutputStream(streamOut);
			out.writeObject(this.obj);
			out.flush();
			out.close();
			streamOut.flush();
			streamOut.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void serialize(String path, Object obj){
		
		try(FileOutputStream streamOut = new FileOutputStream(new File(path))){
			
			ObjectOutputStream out = new ObjectOutputStream(streamOut);
			out.writeObject(obj);
			out.flush();
			out.close();
			streamOut.flush();
			streamOut.close();
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	protected Object deserialize(String path){
		
		try(FileInputStream streamIn = new FileInputStream(path)){
			
			ObjectInputStream in = new ObjectInputStream(streamIn);
			Object obj = null;
			
			obj = in.readObject();
			
			in.close();
			streamIn.close();
			
			return obj;
			
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	protected Object deserialize(String path, String objectName, String extension){
		
		
		return null;
		
//		path = path + systemSeparator + objectName + "." + extension;
//		return deserialize(path);
		
	}
	
	protected Object[] deserializeObjects(String path, String extension){
		return null;
	}
}
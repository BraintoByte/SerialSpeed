package com.braintobytes.serialspeed.testing.testObjects;

import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;

public class ObjectTest2 implements Serializable {
	
	private boolean boolTest;
	private int intTest;
	private long longTest;
	private String stringTest;
	private char charTest;
	private String[] arrayOfStringsTest;
	private int[] intArrayTest;
	private List<String> stringListTest;
	private JButton jButtonTest;
	
	
	public ObjectTest2(boolean boolTest, int intTest, long longTest, String stringTest, char charTest,
			String[] arrayOfStringsTest, int[] intArrayTest, List<String> stringListTest, JButton jButtonTest) {
		super();
		this.boolTest = boolTest;
		this.intTest = intTest;
		this.longTest = longTest;
		this.stringTest = stringTest;
		this.charTest = charTest;
		this.arrayOfStringsTest = arrayOfStringsTest;
		this.intArrayTest = intArrayTest;
		this.stringListTest = stringListTest;
		this.jButtonTest = jButtonTest;
	}


	public boolean isBoolTest() {
		return boolTest;
	}


	public int getIntTest() {
		return intTest;
	}


	public long getLongTest() {
		return longTest;
	}


	public String getStringTest() {
		return stringTest;
	}


	public char getCharTest() {
		return charTest;
	}


	public String[] getArrayOfStringsTest() {
		return arrayOfStringsTest;
	}


	public int[] getIntArrayTest() {
		return intArrayTest;
	}


	public List<String> getStringListTest() {
		return stringListTest;
	}


	public JButton getjButtonTest() {
		return jButtonTest;
	}
}
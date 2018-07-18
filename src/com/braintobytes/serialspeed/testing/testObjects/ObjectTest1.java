package com.braintobytes.serialspeed.testing.testObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

public class ObjectTest1 implements Serializable{

	private static final long serialVersionUID = 8437679925712311487L;
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
	private ObjectTest2 testObj;


	public ObjectTest1(boolean boolTest, int intTest, long longTest, double doubleTest, String stringTest,
			char charTest, String[] arrayOfStringsTest, int[] intArrayTest, List<String> stringListTest,
			JButton jButtonTest, ObjectTest2 testObj) {
		super();
		this.boolTest = boolTest;
		this.intTest = intTest;
		this.longTest = longTest;
		this.doubleTest = doubleTest;
		this.stringTest = stringTest;
		this.charTest = charTest;
		this.arrayOfStringsTest = arrayOfStringsTest;
		this.intArrayTest = intArrayTest;
		this.stringListTest = stringListTest;
		this.jButtonTest = jButtonTest;
		this.testObj = testObj;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
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



	public double getDoubleTest() {
		return doubleTest;
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



	public ObjectTest2 getTestObj() {
		return testObj;
	}
}
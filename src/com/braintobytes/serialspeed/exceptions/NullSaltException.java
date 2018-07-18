package com.braintobytes.serialspeed.exceptions;

public class NullSaltException extends Exception {
	
	public NullSaltException() {
		super("Salt is null!");
	}
}
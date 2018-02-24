package com.bbg.open.b2b4pos.utils;

public class StringTools {
	
	
	public static boolean isLetterDigit(String str) {
		  String regex = "^[a-z0-9A-Z]+$";
		  return str.matches(regex);
		 }

}

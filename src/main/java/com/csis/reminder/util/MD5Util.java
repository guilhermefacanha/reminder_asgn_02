package com.csis.reminder.util;

import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

public class MD5Util {
	public static String getMd5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] digest = md.digest();
			String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

			return myHash.toUpperCase();

		} catch (Exception e) {
			return "";
		}
	}
}

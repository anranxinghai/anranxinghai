package com.anranxinghai.musicplayer.util;

import java.util.Arrays;

/*
 * FileUtil.
 * 
 * JavaZOOM : jlgui@javazoom.net
 *            http://www.javazoom.net 
 *
 *-----------------------------------------------------------------------
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU Library General Public License as published
 *   by the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Library General Public License for more details.
 *
 *   You should have received a copy of the GNU Library General Public
 *   License along with this program; if not, write to the Free Software
 *   Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *----------------------------------------------------------------------
 */
public class FileUtil {
	public static String padString(String string, int length){
		return padString(string, ' ',  length);
	}
	public static String padString(String string, char padChar, int length){
		int sLen = 0, numPads = 0;
		if (string == null) {
			string = "";
			numPads = length - sLen;
		} else if ((sLen = string.length()) > length) {
			string  = string.substring(0,length);
		} else if (sLen < length) {
			numPads = length - sLen;
		}
		if (numPads == 0) {
			return string;
		}
		char[] chars = new char[numPads];
		Arrays.fill(chars, padChar);
		return string + new String(chars);
	}
	
	public static String rightPadString(String string, int length) {
		return (rightPadString(string, ' ', length));
	}
	
	public static String rightPadString(String string, char padChar, int length){
		int sLen = 0, numPads = 0;
		if (string == null) {
			string = "";
			numPads = length;
		} else if ((sLen = string.length()) > length) {
			string = string.substring(length);
		} else if (sLen < length) {
			numPads = length - sLen;
		}
		if (numPads == 0) {
			return string;
		}
		char[] chars = new char[numPads];
		Arrays.fill(chars, padChar);
		return new String(chars) + string;
	}
	
	
	
}

package com.swust.ipmsg.util;

import java.io.File;
import java.io.Serializable;

/**
 * 
 * @author george
 *该类用来保存文件路径和文件名，同时实现Comparable接口，根据type的值来进行排序，
 *type=1代表当前存的是目录信息
 *type=2代表当前存的是文件信息
 *根据type的值从小到大排例，这样文件夹均被排在前面，文件排在后面
 */

/**
 * Compares this object to the specified object to determine their relative
 * order.
 * 
 * @author Administrator
 * 
 */

// Comparable接口中只有一个方法，compareTo方法，是用来比较两个对象，使用泛型。
// 此类实现了Serializable接口，可以序列化，即可以将对象存储到硬盘上，
// 如果内存不足时，实现了Serializable接口的对象会存储到硬盘上，到时候在读取出来使用，这部分内存即为虚拟内存。
// 实现Comparable接口，使得此类可以进行比较。
public class FileName implements Comparable<FileName>, Serializable {

	// long型的静态常量，也叫，类变量（类成员成员量）
	private static final long serialVersionUID = 1L;

	public int type = 0;// 类型？ 这是此类的一个标志或者标记
	public long fileSize = -1;// 文件大小，初始值为-1
	public String fileName = "";// fileName文件名包括：路径和文件名，初始为空串。
	public boolean isDirectory = true;// 是否为直接路径

	// 三个构造方法
	public FileName() {
	}// 空的构造方法。

	// 名称和类型。
	public FileName(int type, String fileName) {
		this.type = type;
		this.fileName = fileName;
	}

	// 名称，类型，文件大小，是否直接路径?
	public FileName(int type, String fileName, long fileSize,
			boolean isDirectory) {
		this.type = type;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.isDirectory = isDirectory;
	}

	// 得到文件名。 对于此函数

	// 例如有字符串：fileName = "C:\WINDOWS\system32\drivers\pccsmcfd.sys"
	// 则fileName.lastIndexOf(File.separator);（separator 分隔符'/'）
	// 返回的值为最后一个'\'的位置 ，也即index = 28。
	// 再通过fileName.substring(index+1)
	// 即fileName.substring(29);（得到指定位置之后的字符串，即指定位置的后缀）得到pccsmcfd.sys
	public String getFileName() {

		// lastIndexOf()方法 :Searches in this string for the last index of the
		// specified string. The search for the string starts at the end and
		// moves towards the beginning of this string.
		// 在此字符串指定的字符串的最后一个索引搜索。此搜索从字符串的末尾想字符串的开头移动。
		// 找出参数串在此对象最后出现侧位置。
		// lastIndexOf 方法返回一个整数值，指出 String 对象内子字符串的开始位置。如果没有找到子字符串，则返回 -1。
		int index = fileName.lastIndexOf(File.separator);
		return fileName.substring(index + 1);
	}

	// 获得文件的全路径以及名称
	public String getFullPath() {
		return fileName;
	}

	// 重写的类
	@Override
	// 用来比较文件的位置？用来比较文件。
	public int compareTo(FileName fileN) {
		int result = -2;
		if (type < fileN.type)
			result = -1;
		if (type == fileN.type)
			result = 0;
		if (type > fileN.type)
			result = 1;
		return result;
	}

	// hashCode 哈希代码？哈希码
	// 不是递归方法，此hashCode()方法是，String的hashCode()方法。
	public int hashCode() {

		int result = 56;
		result = 56 * result + type;
		// Returns an integer hash code for this object. By contract, any two
		// objects for which equals(Object) returns true must return the same
		// hash code value. This means that subclasses of Object usually
		// override both methods or neither method.
		// 返回此对象的hashCode，1) 对象相等则hashCode一定相等； 　　2) hashCode相等对象未必相等。
		result = 56 * result + fileName.hashCode();
		return result;
	}

	public boolean equals(Object o) {
		if (!(o instanceof FileName))// 判断左边的对象是不是右边类的实例
			return false;
		FileName fileN = (FileName) o;// 如果是，强制转化。
		// 判断文件类型是否匹配，文件名是否相同。
		return (type == fileN.type) && (fileName.equals(fileN.fileName));
	}
}

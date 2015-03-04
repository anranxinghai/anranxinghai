package com.swust.ipmsg.test;

import com.swust.ipmsg.util.IPv4Util;
import com.swust.ipmsg.util.NetWork;

import junit.framework.TestCase;

public class LoopbackAddressTest extends TestCase{
	public void testLoopbackAddress(){
		String loopbackIp = "127.0.0.1";
		System.out.println(IPv4Util.ipToInt(loopbackIp));
	}
}

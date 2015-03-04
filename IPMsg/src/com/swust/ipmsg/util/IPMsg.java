package com.swust.ipmsg.util;

public class IPMsg {
	/*    @(#)Copyright (C) H.Shirouzu 1996-1998 ipmsg.h    Ver1.34 */

//	#ifndef IPMSG_H
//	public static final int IPMSG_H

	/* IP Messenger Communication Protocol version 1.0 define */
	/* macro */
	public static final int GET_MODE = 0;//(command)(command & 0x000000ff);
	public static final int GET_OPT = 0;//(command)    (command & 0xffffff00;)

	/* header */
	public static final int IPMSG_VERSION = 0x0001;//版本号。
	public static final int IPMSG_DEFAULT_PORT = 0x0979;//默认端口2425。

	/* command */
	public static final int IPMSG_NOOPERATION = 0x00000000;

	public static final int IPMSG_BR_ENTRY = 0x00000001;
	public static final int IPMSG_BR_EXIT = 0x00000002;
	public static final int IPMSG_ANSENTRY = 0x00000003;
	public static final int IPMSG_BR_ABSENCE = 0x00000004;

	public static final int IPMSG_BR_ISGETIST = 0x00000010;
	public static final int IPMSG_OKGETIST = 0x00000011;
	public static final int IPMSG_GETIST = 0x00000012;
	public static final int IPMSG_ANSIST = 0x00000013;
	public static final int IPMSG_FIE_MTIME = 0x00000014;
	public static final int IPMSG_FIE_CREATETIME = 0x00000016;
	public static final int IPMSG_BR_ISGETIST2 = 0x00000018;

	public static final int IPMSG_SENDMSG = 0x00000020;
	public static final int IPMSG_RECVMSG = 0x00000021;
	public static final int IPMSG_READMSG = 0x00000030;
	public static final int IPMSG_DEMSG = 0x00000031;

	/* option for all command */
	public static final int IPMSG_ABSENCEOPT = 0x00000100;
	public static final int IPMSG_SERVEROPT = 0x00000200;
	public static final int IPMSG_DIAUPOPT = 0x00010000;
	public static final int IPMSG_FIEATTACHOPT = 0x00200000;

	/* file types for fileattach command */
	public static final int IPMSG_FIE_REGUAR = 0x00000001;
	public static final int IPMSG_FIE_DIR = 0x00000002;
	public static final int IPMSG_ISTGET_TIMER = 0x0104;
	public static final int IPMSG_ISTGETRETRY_TIMER = 0x0105;

	public static String HS_TOOS = "HSTools";
	public static String IP_MSG = "IPMsg";
	public static String NO_NAME = "no_name";
	public static String UR_STR = "://";
	public static String MAITO_STR = "mailto:";
	//#endif/* IPMSG_H */


	

	/* command */

	public static final int IPMSG_NEW_BR_ENTRY = 0x00000401;
	public static final int IPMSG_NEW_BR_EXIT = 0x00000402;
	public static final int IPMSG_NEW_ANSENTRY = 0x00000403;
	public static final int IPMSG_NEW_BR_ABSENCE = 0x00000101;

	public static final int IPMSG_NEW_BR_ISGETIST = 0x00000410;
	public static final int IPMSG_NEW_OKGETIST = 0x00000411;
	public static final int IPMSG_NEW_GETIST = 0x00000412;
	public static final int IPMSG_NEW_ANSIST = 0x00000413;
	public static final int IPMSG_NEW_FIE_MTIME = 0x00000414;
	public static final int IPMSG_NEW_FIE_CREATETIME = 0x00000416;
	public static final int IPMSG_NEW_BR_ISGETIST2 = 0x00000418;

	public static final int IPMSG_NEW_SENDMSG = 0x00000120;
	public static final int IPMSG_NEW_RECVMSG = 0x00000421;
	public static final int IPMSG_NEW_READMSG = 0x00000430;
	public static final int IPMSG_NEW_DEMSG = 0x00000431;

	/* option for all command */
	public static final int IPMSG_NEW_ABSENCEOPT = 0x00000100;
	public static final int IPMSG_NEW_SERVEROPT = 0x00000200;
	public static final int IPMSG_NEW_DIAUPOPT = 0x00010000;
	public static final int IPMSG_NEW_FIEATTACHOPT = 0x00200000;

	/* file types for fileattach command */
	public static final int IPMSG_NEW_FIE_REGUAR = 0x00000401;
	public static final int IPMSG_NEW_FIE_DIR = 0x00000402;
	public static final int IPMSG_NEW_ISTGET_TIMER = 0x0104;
	public static final int IPMSG_NEW_ISTGETRETRY_TIMER = 0x0105;


}

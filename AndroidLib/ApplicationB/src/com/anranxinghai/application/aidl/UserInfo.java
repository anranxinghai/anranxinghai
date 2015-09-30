package com.anranxinghai.application.aidl;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.table.DatabaseTable;
import com.anranxinghai.applicationb.constant.*;
@DatabaseTable(tableName=Constants.TABLE_NAME)
public class UserInfo implements Parcelable{
	
	public static final String USER_ID = "_id";
	public static final String USER_NAME = "user_name";
	public static final String USER_PASSWORD = "user_password";
	
	//@DatabaseField(canBeNull=false,generatedId=true,columnName=USER_ID,useGetSet=true,unique=true)
	private int userId;
	//@DatabaseField(useGetSet=true,canBeNull=false,columnName=USER_NAME)
	private String userName;
	//@DatabaseField(useGetSet=true,canBeNull=false,columnName=USER_PASSWORD)
	private String userPassword;
	
	public UserInfo(){
		
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeInt(userId);
		out.writeString(userName);
		out.writeString(userPassword);
	}
	
	public static Parcelable.Creator<UserInfo> CREATOR = new Creator<UserInfo>() {

		@Override
		public UserInfo createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(in.readInt());
			userInfo.setUserName(in.readString());
			userInfo.setUserPassword(in.readString());
			return userInfo;	
		}

		@Override
		public UserInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new UserInfo[size];
		}
		
	};
	public UserInfo(Parcel in) {
		// TODO Auto-generated constructor stub
		userId = in.readInt();
		userName = in.readString();
		userPassword = in.readString();
	}
	
}

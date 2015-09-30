package com.anranxinghai.androidlib.bean;


import android.os.Parcel;
import android.os.Parcelable;

import com.anranxinghai.androidlib.databasecolumn.UserInfoColumn;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.tojc.ormlite.android.annotation.AdditionalAnnotation.DefaultContentUri;
@DatabaseTable(tableName=UserInfoColumn.TABLE_NAME)
@DefaultContentUri(authority=UserInfoColumn.AUTOHORITY , path=UserInfoColumn.TABLE_NAME)
public class UserInfo implements Parcelable{
	
	@DatabaseField(canBeNull=false,generatedId=true,columnName=UserInfoColumn._ID,useGetSet=true,unique=true)
	private int userId;
	@DatabaseField(useGetSet=true,canBeNull=false,columnName=UserInfoColumn.USER_NAME)
	private String userName;
	@DatabaseField(useGetSet=true,canBeNull=false,columnName=UserInfoColumn.USER_PASSWORD)
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
			//这里有两种写法
			/*UserInfo userInfo = new UserInfo();
			userInfo.setUserId(in.readInt());
			userInfo.setUserName(in.readString());
			userInfo.setUserPassword(in.readString());
			return userInfo;	*/
			return new UserInfo(in);
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

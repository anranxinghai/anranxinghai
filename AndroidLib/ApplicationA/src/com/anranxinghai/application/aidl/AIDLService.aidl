package com.anranxinghai.application.aidl;
import com.anranxinghai.application.aidl.UserInfo;

interface AIDLService {

    List<UserInfo> queryAllUserInfo();
    
}
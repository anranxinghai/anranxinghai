/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: F:\\workspace\\ApplicationA\\src\\com\\anranxinghai\\application\\aidl\\AIDLService.aidl
 */
package com.anranxinghai.application.aidl;
public interface AIDLService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.anranxinghai.application.aidl.AIDLService
{
private static final java.lang.String DESCRIPTOR = "com.anranxinghai.application.aidl.AIDLService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.anranxinghai.application.aidl.AIDLService interface,
 * generating a proxy if needed.
 */
public static com.anranxinghai.application.aidl.AIDLService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.anranxinghai.application.aidl.AIDLService))) {
return ((com.anranxinghai.application.aidl.AIDLService)iin);
}
return new com.anranxinghai.application.aidl.AIDLService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_queryAllUserInfo:
{
data.enforceInterface(DESCRIPTOR);
java.util.List<com.anranxinghai.application.aidl.UserInfo> _result = this.queryAllUserInfo();
reply.writeNoException();
reply.writeTypedList(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.anranxinghai.application.aidl.AIDLService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public java.util.List<com.anranxinghai.application.aidl.UserInfo> queryAllUserInfo() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.util.List<com.anranxinghai.application.aidl.UserInfo> _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_queryAllUserInfo, _data, _reply, 0);
_reply.readException();
_result = _reply.createTypedArrayList(com.anranxinghai.application.aidl.UserInfo.CREATOR);
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_queryAllUserInfo = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public java.util.List<com.anranxinghai.application.aidl.UserInfo> queryAllUserInfo() throws android.os.RemoteException;
}

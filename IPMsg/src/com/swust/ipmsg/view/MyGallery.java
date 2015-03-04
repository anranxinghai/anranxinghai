package com.swust.ipmsg.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Gallery;

public class MyGallery extends Gallery{//Gallery用来显示缩略图

	public MyGallery(Context context,AttributeSet attrs) {
	
		super(context,attrs);
		
	}
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
		
		return super.onFling(e1, e2, velocityX/3, velocityY);
		
		}
}
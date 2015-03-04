package com.swust.ipmsg.view;

import com.swust.ipmsg.R;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomDialog extends AlertDialog {

	AlertDialog.Builder builder;
	protected CustomDialog(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		/*builder = new AlertDialog.Builder(context);
		
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.custom_dialog, null);
		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText("每一天、珍惜");
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.sign);
		builder = new AlertDialog.Builder(context);
		builder.setView(layout);
		this = builder.create();
		*/
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		super.cancel();
	}
	
	

}

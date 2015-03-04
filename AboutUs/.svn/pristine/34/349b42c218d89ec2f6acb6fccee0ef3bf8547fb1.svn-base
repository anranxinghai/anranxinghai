package com.channelsoft.android.aboutus.ui;

import com.channelsoft.android.aboutus.R;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义Toast，使用方法与原生Toast相同 显示时长使用Toast.LENGTH_LONG和Toast.LENGTH_SHORT
 * 
 * @author qiruichao
 * 
 */
public class QNToast
{

    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG  = Toast.LENGTH_LONG;

    public static Toast makeText(Context context, CharSequence text, int duration)
    {
        Toast result = new Toast(context);

        View toastRoot = LayoutInflater.from(context).inflate(R.layout.qn_toast, null);

        result.setView(toastRoot);

        TextView tv = (TextView) toastRoot.findViewById(R.id.qn_toast_textview);
        tv.setText(text);

        return result;
    }

    public static Toast makeText(Context context, int resId, int duration) throws Resources.NotFoundException
    {
        return makeText(context, context.getResources().getText(resId), duration);
    }
}

package com.example.utils;

import javax.security.auth.PrivateCredentialPermission;

import com.amap.api.mapcore.util.c;
import com.example.zhihuishequ.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtils 
{
//	public static Toast makeText(Context context,CharSequence text,int duration)
//	{
//		if(mToast==null)
//			mToast=new Toast(context);
//		LinearLayout mLayout=new LinearLayout(context);
//		LinearLayout outLayout=new LinearLayout(context);
//		LayoutInflater mInflater=LayoutInflater.from(context);
////		mLayout=(LinearLayout) mInflater.inflate(R.layout.mytoast,null);
//		outLayout.addView(mLayout);
//		outLayout.setBackgroundResource(R.drawable.android_layout_bg);
//		outLayout.setGravity(Gravity.CENTER);
//		mLayout.setBackgroundResource(R.drawable.mytoast_corner_bg);
//		mToast.setView(mLayout);
//		mText=(TextView)mLayout.findViewById(R.id.my_toast_text);
//		mText.setText(text);
//		return mToast;
//		
//	}
	public static void showShortToast(Context context,String message)
	{
		LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view =inflater.inflate(R.layout.mytoast, null);
		TextView text=(TextView)view.findViewById(R.id.my_toast_text);
		Toast toast=new Toast(context);
		text.setText(message);
		toast.setView(view);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.show();
		
	}
	public static final int LENGTH_SHORT=2000;
	public static final int LENGTH_LONG=3500;
	
	private final Handler mHandler=new Handler();
	private static Toast mToast;
	private int mDuration=LENGTH_SHORT;
	private int mGravity=Gravity.CENTER;
	private int mX,mY;
	private float mHorizontalMargin;
	private float mVerticalMargin;
	private View mView;
	private View mNextView;
	private static TextView mText;
	
	private WindowManager mWM;
	private final WindowManager.LayoutParams mParams=new WindowManager.LayoutParams();
	
	public ToastUtils(Context context)
	{
		init(context);
	}
	private void init(Context context)
	{
//		final WindowManager.LayoutParams params=mParams;
//		params.height=WindowManager.LayoutParams.WRAP_CONTENT;
//		params.width=WindowManager.LayoutParams.WRAP_CONTENT;
//		params.flags=WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
//		params.format=PixelFormat.TRANSLUCENT;
//		params.windowAnimations=android.R.style.Animation_Toast;
//		params.type=WindowManager.LayoutParams.TYPE_TOAST;
//		
//		mWM=(WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
	}
	
}

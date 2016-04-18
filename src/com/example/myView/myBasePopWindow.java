package com.example.myView;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

public abstract class myBasePopWindow extends PopupWindow
{
	protected View mContentView;
	public  myBasePopWindow()
	{
		super();
	}
	public myBasePopWindow(Context context,AttributeSet attrs,int defStyle)
	{
		super(context,attrs,defStyle);
	}
	public myBasePopWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public myBasePopWindow(Context context) {
		super(context);
	}

	public myBasePopWindow(int width, int height) {
		super(width, height);
	}
	public myBasePopWindow(View contentView, int width, int height,
			boolean focusable) {
		super(contentView, width, height, focusable);
	}

	public myBasePopWindow(View contentView) {
		super(contentView);
	}
	public myBasePopWindow(View contentView,int width,int height)
	{
		super(contentView,width,height,true);
		mContentView=contentView;
		setFocusable(true);
		setOutsideTouchable(true);
//		setAnimationStyle(null);
		setBackgroundDrawable(new ColorDrawable());
		setTouchable(true);
		initViews();
		initEvents();
		init();
	}
	public abstract void initViews();

	public abstract void initEvents();

	public abstract void init();

	public View findViewById(int id) {
		return mContentView.findViewById(id);
	}
}

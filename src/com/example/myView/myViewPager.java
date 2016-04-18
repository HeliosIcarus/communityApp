package com.example.myView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class myViewPager extends ViewPager
{
	public static final String TAG="myViewPager";
	
	public myViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public myViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		super.addView(child);
	}
	
	@Override
	protected void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		// TODO Auto-generated method stub
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);
	}
}

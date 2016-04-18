package com.example.zhihuishequ;



import com.example.zhihuishequ.R;

import dalvik.system.DexFile;
import android.os.Bundle;
import android.R.color;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.Menu;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	public static final String TAG = MainActivity.class.getSimpleName();

	private RadioGroup mTabButtonGroup;
	private TabHost mTabHost;

	public static final String TAB_MAIN = "MAIN_ACTIVITY";
	public static final String TAB_SEARCH = "SEARCH_ACTIVITY";
	public static final String TAB_MALL = "MALL_ACTIVITY";
	public static final String TAB_SERVICE = "SERVICE_ACTIVITY";
	public static final String TAB_PERSONAL = "PERSONAL_ACTIVITY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_main);
		findViewById();
		initView();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	private void initView() {
		// TODO Auto-generated method stub
		mTabHost = getTabHost();
		
		Intent i_main = new Intent(this, IndexActivity.class);
		Intent i_search = new Intent(this, SearchActivity.class);
		Intent i_mall = new Intent(this, MallActivity.class);
		Intent i_service=new Intent(this,ServiceActivity.class);
		Intent i_personal=new Intent(this,LoginActivity.class);

//	代码中出现的问题，参1设置页卡，参2设置上边指示器名字，参3设置启动的Intent
//	我的错误： addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_SEARCH)
//		导致通过RadioGroup 点击Button找不到 页。
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MAIN).setIndicator(TAB_MAIN)
				.setContent(i_main));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_SEARCH).setIndicator(TAB_SEARCH)
				.setContent(i_search));	
		mTabHost.addTab(mTabHost.newTabSpec(TAB_MALL).setIndicator(TAB_MALL)
				.setContent(i_mall));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_SERVICE).setIndicator(TAB_SERVICE)
				.setContent(i_service));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_PERSONAL).setIndicator(TAB_PERSONAL)
				.setContent(i_personal));
		mTabHost.setCurrentTabByTag(TAB_MAIN);
//		DexFile
		
//	渐变色
//		GradientDrawable gd=new GradientDrawable(Orientation.TOP_BOTTOM, new int[]{Color.RED,Color.BLACK});
//		mTabButtonGroup.setBackground(gd);
		mTabButtonGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch(checkedId)
				{
				case R.id.home_tab_main:
					mTabHost.setCurrentTabByTag(TAB_MAIN);
					System.out.println("current Tag "+TAB_MAIN);
					break;
				case R.id.home_tab_search:
					mTabHost.setCurrentTabByTag(TAB_SEARCH);
					System.out.println("current Tag "+TAB_SEARCH);
					break;
				case R.id.home_tab_mall:
					mTabHost.setCurrentTabByTag(TAB_MALL);
					System.out.println("current Tag "+TAB_MALL);
					break;
				case R.id.home_tab_service:
					mTabHost.setCurrentTabByTag(TAB_SERVICE);
					System.out.println("current Tag "+TAB_SERVICE);
					break;
				case R.id.home_tab_personal:
					mTabHost.setCurrentTabByTag(TAB_PERSONAL);
					System.out.println("current Tag "+TAB_PERSONAL);
					break;
				}
			}
		});
	}

	private void findViewById() {
		// TODO Auto-generated method stub
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

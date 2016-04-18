package com.example.zhihuishequ;

import com.example.zhihuishequ.R;
import com.example.utils.ToastUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends Activity implements OnClickListener
{
	Button top_btn_1;
	Button top_btn_2;
	Button top_btn_3;
	Button top_btn_4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		findViewById();
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		top_btn_1.setOnClickListener(this);
		top_btn_2.setOnClickListener(this);
		top_btn_3.setOnClickListener(this);
		top_btn_4.setOnClickListener(this);
	}
	private void findViewById() {
		// TODO Auto-generated method stub
		top_btn_1=(Button)findViewById(R.id.index_top_btn1);
		top_btn_2=(Button)findViewById(R.id.index_top_btn2);
		top_btn_3=(Button)findViewById(R.id.index_top_btn3);
		top_btn_4=(Button)findViewById(R.id.index_top_btn4);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(IndexActivity.this,InformationDetails.class);
		intent.putExtra("name", v.getId());
		switch(v.getId())
		{
		case R.id.index_top_btn1:
//			toastShow("请稍等");			如果失败，则更改回来。
			ToastUtils.showShortToast(IndexActivity.this, "6666");
//	服务器怎么知道我点击的是哪个图片？   需要上传一个东西让服务器知道。
//	初步构想是这样的。1	setTag
//				  2  就直接上传点击btn的名字？
			startActivity(intent);
			this.getParent().overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out);
			break;
		case R.id.index_top_btn2:
			toastShow("请稍等 2");
			startActivity(intent);
			this.getParent().overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out);
			break;
		}
	}
	public void toastShow(String text)
	{
		Toast mtoast=new Toast(IndexActivity.this);
		LayoutInflater inflater=IndexActivity.this.getLayoutInflater();
		View myToast=inflater.inflate(R.layout.mytoast, null);
		ProgressBar mProgressBar=(ProgressBar)myToast.findViewById(R.id.my_toast_progress);
		TextView mTextView=(TextView)myToast.findViewById(R.id.my_toast_text);
		mTextView.setText(text);
		mtoast.setView(myToast);
		mtoast.setDuration(Toast.LENGTH_SHORT);
		mtoast.setGravity(Gravity.CENTER, 0, 0);
		mtoast.show();
	
	}
	
}

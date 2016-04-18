package com.example.zhihuishequ;

import java.util.List;

import com.example.zhihuishequ.R;
import com.example.myView.myRefreshableView;
import com.example.myView.myRefreshableView.PullToRefreshListener;
import com.example.utils.ToastUtils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class SearchActivity extends Activity
{
//		让右边LienarLayout显示的笨办法，是让5个都显示 ，通过切换其Visibility来设置可见。
	
//		还需要完善下拉菜单的功能。   将头和List绑定在一起，头在初始化的时候要Margin  -XXX
	
	ListView left_list;
	ListView right_list;
	ArrayAdapter<String> leftAdapter;
	ArrayAdapter<String> rightAdapter;
	myRefreshableView refreshView;
	String[] left_test={"测试选项1","测试选项2","测试选项3","测试选项4","测试选项5","测试选项6"};
	String[] right_test={"测试选项11","测试选项12ssssssssssssssssssssssssssssssssssssssss","测试选项3","测试选项14","测试选项15","测试选项16"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		findViewById();
		initListView();
//		ActivityManager am=(ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
//		WindowManager wm=(WindowManager)getSystemService(Context.WINDOW_SERVICE);
	}
	private void initListView() {
		// TODO Auto-generated method stub
		left_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				toastShow();
//				ToastUtils.makeText(SearchActivity.this, "9999", Toast.LENGTH_SHORT).show();
				
			}
		});
	}
	private void findViewById() {
		// TODO Auto-generated method stub
		left_list=(ListView)findViewById(R.id.search_left_list);
		right_list=(ListView)findViewById(R.id.search_right_list);
		leftAdapter=new ArrayAdapter<String>(this, R.layout.search_leftlist_item, left_test);
		rightAdapter=new ArrayAdapter<String>(this, R.layout.search_leftlist_item, right_test);
		left_list.setAdapter(leftAdapter);
		right_list.setAdapter(rightAdapter);
//		right_list.setClickable(true);
		refreshView=(myRefreshableView)findViewById(R.id.search_right_layout);
		refreshView.setonRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshView.finishRefreshing();
			}
		}, 0);
		
	}
	public void toastShow()
	{
		Toast mtoast=new Toast(SearchActivity.this);
		LayoutInflater inflater=SearchActivity.this.getLayoutInflater();
		View myToast=inflater.inflate(R.layout.mytoast, null);
		ProgressBar mProgressBar=(ProgressBar)myToast.findViewById(R.id.my_toast_progress);
		TextView mTextView=(TextView)myToast.findViewById(R.id.my_toast_text);
		mTextView.setText("Text Toast");
		mtoast.setView(myToast);
		mtoast.setDuration(Toast.LENGTH_SHORT);
		mtoast.setGravity(Gravity.CENTER, 0, 0);
		mtoast.show();
	}
}

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
//		���ұ�LienarLayout��ʾ�ı��취������5������ʾ ��ͨ���л���Visibility�����ÿɼ���
	
//		����Ҫ���������˵��Ĺ��ܡ�   ��ͷ��List����һ��ͷ�ڳ�ʼ����ʱ��ҪMargin  -XXX
	
	ListView left_list;
	ListView right_list;
	ArrayAdapter<String> leftAdapter;
	ArrayAdapter<String> rightAdapter;
	myRefreshableView refreshView;
	String[] left_test={"����ѡ��1","����ѡ��2","����ѡ��3","����ѡ��4","����ѡ��5","����ѡ��6"};
	String[] right_test={"����ѡ��11","����ѡ��12ssssssssssssssssssssssssssssssssssssssss","����ѡ��3","����ѡ��14","����ѡ��15","����ѡ��16"};
	
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

package com.example.myView;

import java.security.PublicKey;



import com.example.zhihuishequ.R;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.RotateAnimation;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class myRefreshableView extends LinearLayout implements OnTouchListener
{
	
	/**
	 * 下拉状态
	 * 分别是：
	 * 下拉
	 * 释放立即刷新
	 * 正在刷新
	 * 刷新完成或未刷新状态
	 */
	public static final int STATUS_PULL_TO_REFRESH=0;
	public static final int STATUS_RELEASE_TO_REFRESH=1;
	public static final int STATUS_REFRESHING=2;
	public static final int STATUS_REFRESH_FINISHED=3;
	public static final int SCROLL_SPEED=-20;
	public static final long ONE_MINUTE=60*1000;
	public static final long ONE_HOUR = 60 * ONE_MINUTE;
	public static final long ONE_DAY = 24 * ONE_HOUR;
	public static final long ONE_MONTH = 30 * ONE_DAY;
	public static final long ONE_YEAR = 12 * ONE_MONTH;
	private static final String UPDATED_AT = "updated_at";
	/**
	 * 下拉回调接口
	 */
	private PullToRefreshListener mListener;
	
	/**
	 * 下拉时间存放在SP中。
	 */
	private SharedPreferences preferences;
	
	private View header;
	
	private ListView listView;
	
	private ProgressBar progressBar;
	
	private ImageView arrow;
	
	private TextView description;
	
	private TextView updateAt;
	
	private MarginLayoutParams headerLayoutParams;
	
	private long lastUpdateTime;
	
	private int mId=-1;
	
	private int hideHeaderHeight;
	
	/**
	 * 当前状态
	 */
	private int currentStatus=STATUS_REFRESH_FINISHED;
	private int lastStatus=currentStatus;
	private float yDown;
	private int touchSlop;
	private boolean loadOnce;
	private boolean ableToPull;
	
	
	
	public myRefreshableView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		preferences=PreferenceManager.getDefaultSharedPreferences(context);
		header=LayoutInflater.from(context).inflate(R.layout.pull_to_refresh, null,true);
		progressBar=(ProgressBar)header.findViewById(R.id.pull_to_refresh_head_progress_bar);
		arrow=(ImageView)header.findViewById(R.id.pull_to_refresh_head_arrow);
		description=(TextView)header.findViewById(R.id.pull_to_refresh_head_description);
		updateAt=(TextView)header.findViewById(R.id.pull_to_refresh_head_updated_at);
		touchSlop=ViewConfiguration.get(context).getScaledTouchSlop();
		refreshUpdatedAtValue();
		setOrientation(VERTICAL);
		addView(header,0);
	}

	/**
	 * 进行一些关键性的初始化操作，比如：将下拉头向上偏移进行隐藏，给ListView注册touch事件。
	 * 在初始化  和  尺寸被改变的时候会被系统回调。
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		System.out.println("in layout");
		if(changed && !loadOnce)
		{
//			初始的时候将整个头全部藏进去。
			System.out.println("in if");
			hideHeaderHeight=-header.getHeight();
			headerLayoutParams=(MarginLayoutParams)header.getLayoutParams();
			headerLayoutParams.topMargin=hideHeaderHeight;
//			System.out.println("on layout if"+headerLayoutParams.topMargin);
			System.out.println("top margin="+headerLayoutParams.topMargin);
			listView=(ListView)getChildAt(1);
			listView.setOnTouchListener(this);
			loadOnce=true;
//			header.setLayoutParams(headerLayoutParams);
		}
//		这里的代码有瑕疵，如果去掉这句，会在第一次初始化的时候无法隐藏标题头  
//			不知道为什么
		super.onLayout(changed, l, t, r, b);
		
	}
	
	/**
	 * 更新刷新时间。
	 */
	private void refreshUpdatedAtValue() {
		// TODO Auto-generated method stub
		lastUpdateTime = preferences.getLong(UPDATED_AT + mId, -1);
		long currentTime = System.currentTimeMillis();
		long timePassed = currentTime - lastUpdateTime;
		long timeIntoFormat;
		String updateAtValue;
		if (lastUpdateTime == -1) {
			updateAtValue = getResources().getString(R.string.not_updated_yet);
		} else if (timePassed < 0) {
			updateAtValue = getResources().getString(R.string.time_error);
		} else if (timePassed < ONE_MINUTE) {
			updateAtValue = getResources().getString(R.string.updated_just_now);
		} else if (timePassed < ONE_HOUR) {
			timeIntoFormat = timePassed / ONE_MINUTE;
			String value = timeIntoFormat + "分钟";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_DAY) {
			timeIntoFormat = timePassed / ONE_HOUR;
			String value = timeIntoFormat + "小时";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_MONTH) {
			timeIntoFormat = timePassed / ONE_DAY;
			String value = timeIntoFormat + "天";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else if (timePassed < ONE_YEAR) {
			timeIntoFormat = timePassed / ONE_MONTH;
			String value = timeIntoFormat + "个月";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		} else {
			timeIntoFormat = timePassed / ONE_YEAR;
			String value = timeIntoFormat + "年";
			updateAtValue = String.format(getResources().getString(R.string.updated_at), value);
		}
		updateAt.setText(updateAtValue);
	}

	/**
	 * 判断ListView被触摸的逻辑
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		setIsAbleToPull(event);
		if(ableToPull)
		{
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				yDown=event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				float yMove=event.getRawY();
				int distance=(int)(yMove-yDown);
				if(distance<=0&&headerLayoutParams.topMargin<=hideHeaderHeight)
					return false;
				if(distance<touchSlop)
					return false;
				if(currentStatus!=STATUS_REFRESHING)
				{
					if(headerLayoutParams.topMargin>0)
						currentStatus=STATUS_RELEASE_TO_REFRESH;
					else {
						currentStatus=STATUS_PULL_TO_REFRESH;
					}
					headerLayoutParams.topMargin=(distance/2)+hideHeaderHeight;
					System.out.println("current top margin="+headerLayoutParams.topMargin);
					header.setLayoutParams(headerLayoutParams);
				}
				break;
			case MotionEvent.ACTION_UP:
			default:
				if(currentStatus==STATUS_RELEASE_TO_REFRESH)
				{
					new RefreshingTask().execute();
//					这里应该调用显示ProgressBar
//					并且查询网络访问并更新本身的ListView的值。
				}
				else if(currentStatus==STATUS_PULL_TO_REFRESH)
				{
					new HideHeaderTask().execute();
				}break;
			}
			// 时刻记得更新下拉头中的信息
			if (currentStatus == STATUS_PULL_TO_REFRESH
					|| currentStatus == STATUS_RELEASE_TO_REFRESH) {
				updateHeaderView();
				// 当前正处于下拉或释放状态，要让ListView失去焦点，否则被点击的那一项会一直处于选中状态
				listView.setPressed(false);
				listView.setFocusable(false);
				listView.setFocusableInTouchMode(false);
				lastStatus = currentStatus;
				// 当前正处于下拉或释放状态，通过返回true屏蔽掉ListView的滚动事件
				return true;
			}
		}
		return false;
	}
	public void setonRefreshListener(PullToRefreshListener listener,int id)
	{
		mListener=listener;
		mId=id;
	}
	public void finishRefreshing() {
		currentStatus = STATUS_REFRESH_FINISHED;
		preferences.edit().putLong(UPDATED_AT + mId, System.currentTimeMillis()).commit();
		new HideHeaderTask().execute();
	}
	private void updateHeaderView() {
		// TODO Auto-generated method stub
		if (lastStatus != currentStatus) {
			if (currentStatus == STATUS_PULL_TO_REFRESH) {
				description.setText(getResources().getString(R.string.pull_to_refresh));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
				description.setText(getResources().getString(R.string.release_to_refresh));
				arrow.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
				rotateArrow();
			} else if (currentStatus == STATUS_REFRESHING) {
				description.setText(getResources().getString(R.string.refreshing));
				progressBar.setVisibility(View.VISIBLE);
				arrow.clearAnimation();
				arrow.setVisibility(View.GONE);
			}
			refreshUpdatedAtValue();
		}
	}

	private void rotateArrow() {
		// TODO Auto-generated method stub
		float pivotX = arrow.getWidth() / 2f;
		float pivotY = arrow.getHeight() / 2f;
		float fromDegrees = 0f;
		float toDegrees = 0f;
		if (currentStatus == STATUS_PULL_TO_REFRESH) {
			fromDegrees = 180f;
			toDegrees = 360f;
		} else if (currentStatus == STATUS_RELEASE_TO_REFRESH) {
			fromDegrees = 0f;
			toDegrees = 180f;
		}
		RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, pivotX, pivotY);
		animation.setDuration(100);
		animation.setFillAfter(true);
		arrow.startAnimation(animation);
	}

	/**
	 * 判断当前是否可以下拉
	 * @param event	由onTouch传递。
	 */
	private void setIsAbleToPull(MotionEvent event) {
		// TODO Auto-generated method stub
		View firstChild=listView.getChildAt(0);
		if(firstChild!=null)
		{
			int firstVisiblePos=listView.getFirstVisiblePosition();
			if (firstVisiblePos == 0 && firstChild.getTop() == 0) {
				if (!ableToPull) {
					yDown = event.getRawY();
				}
		// 如果首个元素的上边缘，距离父布局值为0，就说明ListView滚动到了最顶部，此时应该允许下拉刷新
//		判断父布局有利于拓展		
				ableToPull = true;
			} else {
				if (headerLayoutParams.topMargin != hideHeaderHeight) {
					headerLayoutParams.topMargin = hideHeaderHeight;
					header.setLayoutParams(headerLayoutParams);
				}
				ableToPull = false;
			}
		} else {
			// 如果ListView中没有元素，也应该允许下拉刷新
			ableToPull = true;
		
		}
	}
	public interface PullToRefreshListener
	{
		void onRefresh();
	}
	/**
	 * 更新UI用AsyncTask
	 * @author 翔宇
	 *
	 */
	class RefreshingTask extends AsyncTask<Void, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Void... params) {
			// TODO Auto-generated method stub
			int topMargin=headerLayoutParams.topMargin;
			while(true)
			{
				topMargin=topMargin+SCROLL_SPEED;
				if(topMargin<=0)
				{
					topMargin=0;
					break;
				}
				publishProgress(topMargin);
				sleep(10);
			}
			currentStatus = STATUS_REFRESHING;
			publishProgress(0);
			if (mListener != null) {
				mListener.onRefresh();
			}
			return null;
		}
		@Override
		protected void onProgressUpdate(Integer... topMargin) {
			System.out.println("on HideHeadView onProgressUpdate");
			updateHeaderView();
			headerLayoutParams.topMargin = topMargin[0];
			header.setLayoutParams(headerLayoutParams);
		}
		
	}
	class HideHeaderTask extends AsyncTask<Void, Integer, Integer>
	{

		@Override
		protected Integer doInBackground(Void... params) {
			int topMargin = headerLayoutParams.topMargin;
			while (true) {
//	普通上升和 刷新 上升的代码都在这里。
				topMargin = topMargin + SCROLL_SPEED;
				if (topMargin <= hideHeaderHeight) {
					topMargin = hideHeaderHeight;
					break;
				}
//	每次调用一次 publisProgress 系统会调用一次onProgressUpdate  并传递参数给 OPU
				publishProgress(topMargin);
				sleep(1000);
			}
			return topMargin;
		}
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			headerLayoutParams.topMargin=values[0];
			header.setLayoutParams(headerLayoutParams);
		}
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			headerLayoutParams.topMargin = result;
			header.setLayoutParams(headerLayoutParams);
			currentStatus = STATUS_REFRESH_FINISHED;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
	}
	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

package com.example.myView;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.zhihuishequ.R;
/**
 * ��Button��ָʾͼ��󶨳�Ϊһ��relativeLayout
 * Button�Ŀ������Ļ��ΪButton����background
 * �����ImageViewҲ����ʵ�֣�����Ҫ����clickable���� ֮����ܵ����
 * @author ����
 *
 */
public class mySwitcherView extends RelativeLayout implements OnTouchListener
{
	public static final int SNAP_VELOCITY=200;
	private int switcherViewWidth;
	private int currentItemIndex;
	private int itemsCount;
	private int[] borders;
	private int leftEdge=0;
	private int rightEdge=0;
	private float xDown;
	private float xMove;
	private float xUp;
	private LinearLayout itemsLayout;
	private LinearLayout dotsLayout;
	private View firstItem;
	/**
	 * ����ͨ���ı��һ����ֵ�����ı������ֵ��
	 */
	private MarginLayoutParams firstItemParams;
	/**
	 * ����������ָ�����ٶȣ�������ĳ�ٶȲŻ�����
	 */
	private VelocityTracker mVelocityTracker;
	
	public void scrollToNext()
	{
		new ScrollTask().execute(-20);
	}
	public void scrollToPrevious()
	{
		new ScrollTask().execute(20);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
//	�ڸı�ߴ�����¼��ص�ʱ��changed�ͱ�Ϊtrue
		if(changed)
		{
			initialItems();
			initialDots();
		}
			
	}private void initialDots() {
		// TODO Auto-generated method stub
		Log.i("Init", "init dots method");
		dotsLayout=(LinearLayout)getChildAt(1);
		refreshDotsLayout();
	}
	private void initialItems() {
		// TODO Auto-generated method stub
		Log.i("Init", "init items method");
		switcherViewWidth=getWidth();
		itemsLayout=(LinearLayout)getChildAt(0);
		itemsCount=itemsLayout.getChildCount();
		borders=new int[itemsCount];

		for(int i=0;i<itemsCount;i++)
		{
			borders[i]=-i*switcherViewWidth;
			View item=itemsLayout.getChildAt(i);
			MarginLayoutParams params=(MarginLayoutParams)item.getLayoutParams();
			params.width=switcherViewWidth;
			item.setLayoutParams(params);
			item.setOnTouchListener(this);
		}
		leftEdge=borders[itemsCount-1];
		firstItem=itemsLayout.getChildAt(0);
		firstItemParams=(MarginLayoutParams)firstItem.getLayoutParams();
	}
	/**
	 * ���˼���ǣ�����dotLayoutλ�����½�
	 * ������һ��LinearLayout
	 * ����ÿһ��dot�ָҲ��LinearLayout
	 * ��dot�ָ֮�����䱾������RelativeLayout��
	 */
	private void refreshDotsLayout()
	{
		Log.i("refresh", "refresh dots layout");
		dotsLayout.removeAllViews();
		for(int i=0;i<itemsCount;i++)
		{
			LinearLayout.LayoutParams linearParams=new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
			linearParams.weight=1;
			RelativeLayout relativeLayout=new RelativeLayout(getContext());
			ImageView image=new ImageView(getContext());
			if(i==currentItemIndex)
			{
				image.setBackgroundResource(R.drawable.index_dot_selected);
			}
			else {
				image.setBackgroundResource(R.drawable.index_dot_unselected);
			}
			RelativeLayout.LayoutParams relativeParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			relativeParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			relativeLayout.addView(image, relativeParams);
			dotsLayout.addView(relativeLayout, linearParams);
		}
	}

	public mySwitcherView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		Log.i("onTouch", "on Touch Event ");
		createVelocityTracker(event);
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			xDown=event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getRawX();
			int distanceX = (int) (xMove - xDown) - (currentItemIndex * switcherViewWidth);
			firstItemParams.leftMargin = distanceX;
			if (beAbleToScroll()) {
				firstItem.setLayoutParams(firstItemParams);
			}
			break;
		case MotionEvent.ACTION_UP:
			xUp = event.getRawX();
			if (beAbleToScroll()) {
				if (wantScrollToPrevious()) {
					if (shouldScrollToPrevious()) {
						currentItemIndex--;
						scrollToPrevious();
						refreshDotsLayout();
					} else {
						scrollToNext();
					}
				} else if (wantScrollToNext()) {
					if (shouldScrollToNext()) {
						currentItemIndex++;
						scrollToNext();
						refreshDotsLayout();
					} else {
						scrollToPrevious();
					}
				}
			}
			mVelocityTracker.recycle();
			mVelocityTracker = null;
			break;
		}
		return false;
	}

	private boolean wantScrollToPrevious() {
		return xUp - xDown > 0;
	}
	private boolean wantScrollToNext() {
		return xUp - xDown < 0;
	}
	private boolean shouldScrollToNext() {
		return xDown - xUp > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getXVelocity();
		return Math.abs(velocity);
	}
	private boolean shouldScrollToPrevious() {
		return xUp - xDown > switcherViewWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
	}
	private boolean beAbleToScroll() {
		// TODO Auto-generated method stub
		return firstItemParams.leftMargin<rightEdge&&firstItemParams.leftMargin>leftEdge;
	}
	private void createVelocityTracker(MotionEvent event)
	{
		if(mVelocityTracker==null)
			mVelocityTracker=VelocityTracker.obtain();
		mVelocityTracker.addMovement(event);
	}
	/**
	 * ������Task  
	 * Ҫ�жϹ�����ʲô�߽����
	 * ����ʲôʱ����Թ���	
	 * @author ����
	 *
	 */
	class ScrollTask extends AsyncTask<Integer, Integer, Integer>
	{

//	���������ָ���Ļ����ٶ� 20
		@Override
		protected Integer doInBackground(Integer... speed) {
			// TODO Auto-generated method stub
			int leftMargin=firstItemParams.leftMargin;
			while(true)
			{
				leftMargin=leftMargin+speed[0];
				if(isCrossBorder(leftMargin,speed[0]))
				{
					leftMargin=findClosetBorder(leftMargin);
					break;
				}
				publishProgress(leftMargin);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return leftMargin;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			firstItemParams.leftMargin = values[0];
			firstItem.setLayoutParams(firstItemParams);
		}
		@Override
		protected void onPostExecute(Integer leftMargin) {
			firstItemParams.leftMargin = leftMargin;
			firstItem.setLayoutParams(firstItemParams);
		}
		
	}
	/**
	 * �ж��Ƿ�������߽�
	 * @param leftMargin
	 * @param integer
	 * @return
	 */
	public boolean isCrossBorder(int leftMargin, Integer speed) {
		// TODO Auto-generated method stub
		for (int border : borders) {
			if (speed > 0) {
				if (leftMargin >= border && leftMargin - speed < border) {
					return true;
				}
			} else {
				if (leftMargin <= border && leftMargin - speed > border) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * �ҵ��뵱ǰ���������һ��Borderֵ��
	 * @param leftMargin
	 * @return
	 */
	public int findClosetBorder(int leftMargin) {
		// TODO Auto-generated method stub
		int absLeftMargin = Math.abs(leftMargin);
		int closestBorder = borders[0];
		int closestMargin = Math.abs(Math.abs(closestBorder) - absLeftMargin);
		for (int border : borders) {
			int margin = Math.abs(Math.abs(border) - absLeftMargin);
			if (margin < closestMargin) {
				closestBorder = border;
				closestMargin = margin;
			}
		}
		return closestBorder;
	}

}

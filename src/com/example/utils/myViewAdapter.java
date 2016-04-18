package com.example.utils;

import com.example.zhihuishequ.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class myViewAdapter extends BaseAdapter
{
	private Integer[] mImageids;
	private String[] mTitleValues;
	private String[] mCountentValues;
	private Context mcontext;
	LayoutInflater layoutInflater;
	public myViewAdapter(Context context,Integer[] mImageIds,String[] mTitleValues,String[] mContentValues) 
	{
		// TODO Auto-generated constructor stub
		this.mcontext=context;
		this.mImageids=mImageIds;
		this.mTitleValues=mTitleValues;
		this.mCountentValues=mContentValues;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitleValues.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		ViewHolder holder;
		layoutInflater=LayoutInflater.from(mcontext);
		if(convertView==null)
		{
			view=layoutInflater.inflate(R.layout.activity_mall_item, null);
			holder=new ViewHolder();
			holder.image=(ImageView)view.findViewById(R.id.mall_list_image);
			holder.content=(TextView)view.findViewById(R.id.map_content);
			holder.title=(TextView)view.findViewById(R.id.map_title);
			view.setTag(holder);
			
		}
		else {
			view=convertView;
			holder=(ViewHolder)view.getTag();
		}
		holder.image.setImageResource(mImageids[position]);
		holder.title.setText(mTitleValues[position]);
		holder.content.setText(mCountentValues[position]);
		return view;
	}
	public static class ViewHolder
	{
		ImageView image;
		TextView title;
		TextView content;
	}
}

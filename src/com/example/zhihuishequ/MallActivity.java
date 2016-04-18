package com.example.zhihuishequ;



import com.example.zhihuishequ.R;
import com.example.utils.myViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * 这里遇到最大的一个问题   ListView里面数据啥也没有
 * 经过检查  是因为 getCount不能返回 0  Adapter中没有  其子类，BaseAdapter中判断空 就是 getCount=0  
 * @author 翔宇
 *
 */
public class MallActivity extends Activity
{
	private ListView mapList;
	private LayoutInflater layoutInflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mall);
		System.out.println("in MAP ACTIVITY");
		findViewById();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
	}

	private void findViewById() {
		// TODO Auto-generated method stub
		mapList=(ListView)findViewById(R.id.map_list);
//产生空白的问题还是出现在Adaper中。
		mapList.setAdapter(new myViewAdapter(this,mImageIds,mTitleValues,mContentValues));
		mapList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println(mTitleValues[position]+"  current position="+position);
				
//	点击MapItem之后  跳转到 NavigateActivity中。
				Intent i_navigate=new Intent(MallActivity.this,NavigateActivity.class);
				i_navigate.putExtra("navigate_location", mTitleValues[position]);
				startActivity(i_navigate);
			}
		});
	}
	

//	private class MapAdapter extends BaseAdapter
//	{
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return mTitleValues.length;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			View view;
//			ViewHolder holder;
//			
////	相当于获得LayoutInflater 对象。
//			layoutInflater=LayoutInflater.from(MallActivity.this);
//			
//			//组装数据
//			if(convertView==null){
//				view=layoutInflater.inflate(R.layout.activity_mall_item, null);
//				holder=new ViewHolder();
//				holder.image=(ImageView) view.findViewById(R.id.mall_list_image);
//				holder.title=(TextView) view.findViewById(R.id.map_title);
//				holder.content=(TextView) view.findViewById(R.id.map_content);
//				//使用tag存储数据
//				view.setTag(holder);
//			}else{
//				view=convertView;
//				holder=(ViewHolder) view.getTag();
//			}
//			holder.image.setImageResource(mImageIds[position]);
//			holder.title.setText(mTitleValues[position]);
//			holder.content.setText(mContentValues[position]);
//			return view;
//		}
//		
//	}
	private Integer[] mImageIds = {R.drawable.carrefour,R.drawable.carrefour,R.drawable.carrefour,R.drawable.carrefour,
			R.drawable.carrefour,R.drawable.carrefour,R.drawable.carrefour,R.drawable.carrefour
			 };
	private String[] mTitleValues = {"家电", "图书", "衣服", "笔记本", "数码",
			"家具", "手机", "护肤" };
				
	private String[] mContentValues={"家电/生活电器/厨房电器", "电子书/图书/小说","男装/女装/童装", "笔记本/笔记本配件/产品外设", "摄影摄像/数码配件", 
			"家具/灯具/生活用品", "手机通讯/运营商/手机配件", "面部护理/口腔护理/..."};
//	public static class ViewHolder {
//		ImageView image;
//		TextView title;
//		TextView content;
//	}
}

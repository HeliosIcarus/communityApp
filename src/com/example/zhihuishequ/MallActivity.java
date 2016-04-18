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
 * ������������һ������   ListView��������ɶҲû��
 * �������  ����Ϊ getCount���ܷ��� 0  Adapter��û��  �����࣬BaseAdapter���жϿ� ���� getCount=0  
 * @author ����
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
//�����հ׵����⻹�ǳ�����Adaper�С�
		mapList.setAdapter(new myViewAdapter(this,mImageIds,mTitleValues,mContentValues));
		mapList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				System.out.println(mTitleValues[position]+"  current position="+position);
				
//	���MapItem֮��  ��ת�� NavigateActivity�С�
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
////	�൱�ڻ��LayoutInflater ����
//			layoutInflater=LayoutInflater.from(MallActivity.this);
//			
//			//��װ����
//			if(convertView==null){
//				view=layoutInflater.inflate(R.layout.activity_mall_item, null);
//				holder=new ViewHolder();
//				holder.image=(ImageView) view.findViewById(R.id.mall_list_image);
//				holder.title=(TextView) view.findViewById(R.id.map_title);
//				holder.content=(TextView) view.findViewById(R.id.map_content);
//				//ʹ��tag�洢����
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
	private String[] mTitleValues = {"�ҵ�", "ͼ��", "�·�", "�ʼǱ�", "����",
			"�Ҿ�", "�ֻ�", "����" };
				
	private String[] mContentValues={"�ҵ�/�������/��������", "������/ͼ��/С˵","��װ/Ůװ/ͯװ", "�ʼǱ�/�ʼǱ����/��Ʒ����", "��Ӱ����/�������", 
			"�Ҿ�/�ƾ�/������Ʒ", "�ֻ�ͨѶ/��Ӫ��/�ֻ����", "�沿����/��ǻ����/..."};
//	public static class ViewHolder {
//		ImageView image;
//		TextView title;
//		TextView content;
//	}
}

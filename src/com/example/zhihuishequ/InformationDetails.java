package com.example.zhihuishequ;

import com.example.zhihuishequ.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationDetails extends Activity
{
	Bundle bundle;
	int tag;
	TextView informationDetails;
	ImageView informationImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_information);
		findViewById();
	}
	private void findViewById() {
		// TODO Auto-generated method stub
		informationDetails=(TextView)findViewById(R.id.information_details_text);
		informationImage=(ImageView)findViewById(R.id.information_details_image);
		bundle=getIntent().getExtras();
		tag=bundle.getInt("name");
		setText();
	}
	private void setText() {
		// TODO Auto-generated method stub
		if(tag==R.id.index_top_btn2)
		{
			informationDetails.setText("Ŀǰ����֤����ʾ�����г���¶���˸�ȾH7N9�����з�����Σ�����أ�Я�������ļ��ݼ�����й�������������˸�ȾH7N9�����в����Ĵ�Ⱦ��Դ���ճ������У�Ҫע���������1���ճ�������Ӧ��������ֱ�ӽӴ������ࡢ��������㣬�����ǲ��������ݣ������Ӵ����뾡���÷���ˮϴ�֡���ͯӦ����ֱ�ӽӴ����ݺ�Ұ�ݡ�������ֲ��������ݡ��󣬲�Ҫ���д���Ӧ�����йز��š� ��2����Ҫ�������������ɱ�����Ӵ�����ʳ�ò��������ݡ����⣬�������޼���֤�����ʡ�����������Ʒ�� ��3�����ݡ�����ͼ�����һ��Ҫ������͸�� ��4��ע����ʳ��������ʳƷ�ӹ���ʳ�ù����У�һ��Ҫ��������ֿ������⽻����Ⱦ���������ݡ�����İ��塢���ߺ������Ȳ���������ʳ���ڼӹ�������������͵����Ҫ����ϴ�֡� ��5�����������ʽ��Ԥ�������ǳ���Ҫ��ƽʱӦ��ǿ��������������Ϣ������������ۣ������̣���ϴ�֣�ע���������������������ʱ��ס�ڱǡ� 6�����з��ȼ�������֢״��Ӧ���Ͽ��֣����������м�Ҫ����ҽ������ǰ����������λ�������Ӵ�ʷ��Ӧ��ҽ��ָ�����������ƺ���ҩ�� ");
		}
			
			
	}
}

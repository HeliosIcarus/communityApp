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
			informationDetails.setText("目前已有证据提示活禽市场暴露是人感染H7N9禽流感发病的危险因素，携带病毒的家禽及其排泄物、分泌物可能是人感染H7N9禽流感病毒的传染来源。日常生活中，要注意以下事项（1）日常生活中应尽量避免直接接触活禽类、鸟类或其粪便，尤其是病（死）禽；若曾接触，须尽快用肥皂及水洗手。儿童应避免直接接触家禽和野禽。如果发现病（死）禽、畜，不要自行处理，应报告有关部门。 （2）不要购买活禽自行宰杀，不接触、不食用病（死）禽、畜肉，不购买无检疫证明的鲜、活、冻禽畜及其产品。 （3）生禽、畜肉和鸡蛋等一定要烧熟煮透。 （4）注意饮食卫生，在食品加工、食用过程中，一定要做到生熟分开，避免交叉污染，处理生禽、畜肉的案板、刀具和容器等不能用于熟食；在加工处理生禽畜肉和蛋类后要彻底洗手。 （5）健康的生活方式对预防本病非常重要。平时应加强体育锻炼，多休息，避免过度劳累；不吸烟，勤洗手，注意个人卫生，打喷嚏或咳嗽时掩住口鼻。 6）若有发热及呼吸道症状，应带上口罩，尽快就诊，并切记要告诉医生发病前有无外出旅游或与禽类接触史。应在医生指导下正规治疗和用药。 ");
		}
			
			
	}
}

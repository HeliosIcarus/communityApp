package com.example.zhihuishequ;

import com.example.zhihuishequ.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceActivity extends Activity implements OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_service);
		Button testButton=(Button)findViewById(R.id.service_btn_test_1);
		testButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.service_btn_test_1:
			Intent repairIntent=new Intent(ServiceActivity.this,RepairActivity.class);
			startActivity(repairIntent);
			this.getParent().overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out);
		}
	}

	private void startActivityByIntent() {
		// TODO Auto-generated method stub
	}
}

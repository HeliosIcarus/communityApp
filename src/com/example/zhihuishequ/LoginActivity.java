package com.example.zhihuishequ;

import java.net.HttpURLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.example.zhihuishequ.R;
public class LoginActivity extends Activity implements OnClickListener
{
	String accountName;
	String accountPassword;
	EditText name;
	EditText password;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		findViewById();
	}

	private void findViewById() {
		// TODO Auto-generated method stub
		name=(EditText)findViewById(R.id.loginaccount);
		password=(EditText)findViewById(R.id.loginpassword);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login:
			postAccountThread();
			break;

		default:
			break;
		}
	}

	private void postAccountThread() {
		// TODO Auto-generated method stub
		Thread postThread=new Thread()
		{
			public void run() 
			{
					
			};
		};
		postThread.start();
	}
}

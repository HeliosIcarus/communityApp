package com.example.zhihuishequ;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;




import com.example.zhihuishequ.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class RepairActivity extends Activity implements OnClickListener
{
	Button takePhoto;
	ImageView photo;
	SimpleDateFormat time;
	String path;
	File file;
	String photoPath;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_repair);
		findViewById();
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		time = new SimpleDateFormat("yyyyMMddhhmmss");
		path=getExternalCacheDir()+"/image";
		file = new File(path);
		if(file.exists()){
			
		}else{			
			file.mkdirs();
		}
	}
	private void findViewById() {
		// TODO Auto-generated method stub
		takePhoto=(Button)findViewById(R.id.repair_take_photo);
		takePhoto.setOnClickListener(this);
		photo=(ImageView)findViewById(R.id.repair_photo);
		photo.setBackgroundResource(R.drawable.image_default_bg);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.repair_take_photo:
			Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, 1);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK)
		{
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");
			photo.setImageBitmap(bitmap);
//		存储到本地			得到的图片是经过压缩的
//			http://blog.csdn.net/zd_1471278687/article/details/41074431  
//			这里有不经过压缩的。
			photoPath=path+"/image"+time.format(new Date())+".jpg";
			FileOutputStream out = null;
			try {
				
				out=new FileOutputStream(photoPath);
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				Toast.makeText(RepairActivity.this, "SD卡不存在", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			finally {
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}

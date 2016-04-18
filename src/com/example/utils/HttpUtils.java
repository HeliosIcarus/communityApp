package com.example.utils;

import android.provider.MediaStore;

//	截取一村一品部分代码
public class HttpUtils 
{
	//	用于将数据提交  接受 判断正误等。
	/**
	 * 1	照片的拍摄，在启动Activity的时候调用  Intent =MediaStore.ACTION_IMAGE_CAPTURE
	 * 2	启动Intent用ActivityForResult的启动方式。
	 * 3	在重写的回调方法  onActivityResult中 从intent中取得bundle  从bundle中取得data
	 * 4	将取得的data强转为BitMap
	 * 5	photoName = sdCard+"/myImage/" + time.format(new java.util.Date()) 存储在本地
	 * 6	5的路径可以改为packagename下。 并在Image上显示。
	 * 
	 * 7	首先判断  之前步骤执行并且确定存储了图片
	 * 8	拿到这个图片  调用HTTP Post方法  将url和文件传入。构建entity对象
	 * 9	entity加入httppost
	 * 10	构建HttpResponse 对象，用这个对象接受服务器返回值  response = httpClient.execute(post);
	 * 11	根据response对象的返回code  确认是否返回成功。
	 */
}

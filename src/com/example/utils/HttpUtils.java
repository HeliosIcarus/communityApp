package com.example.utils;

import android.provider.MediaStore;

//	��ȡһ��һƷ���ִ���
public class HttpUtils 
{
	//	���ڽ������ύ  ���� �ж�����ȡ�
	/**
	 * 1	��Ƭ�����㣬������Activity��ʱ�����  Intent =MediaStore.ACTION_IMAGE_CAPTURE
	 * 2	����Intent��ActivityForResult��������ʽ��
	 * 3	����д�Ļص�����  onActivityResult�� ��intent��ȡ��bundle  ��bundle��ȡ��data
	 * 4	��ȡ�õ�dataǿתΪBitMap
	 * 5	photoName = sdCard+"/myImage/" + time.format(new java.util.Date()) �洢�ڱ���
	 * 6	5��·�����Ը�Ϊpackagename�¡� ����Image����ʾ��
	 * 
	 * 7	�����ж�  ֮ǰ����ִ�в���ȷ���洢��ͼƬ
	 * 8	�õ����ͼƬ  ����HTTP Post����  ��url���ļ����롣����entity����
	 * 9	entity����httppost
	 * 10	����HttpResponse ���������������ܷ���������ֵ  response = httpClient.execute(post);
	 * 11	����response����ķ���code  ȷ���Ƿ񷵻سɹ���
	 */
}

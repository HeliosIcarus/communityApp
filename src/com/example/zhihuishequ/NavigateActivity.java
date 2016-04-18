package com.example.zhihuishequ;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.MapsInitializer;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.PoiOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;
import com.example.zhihuishequ.R;


/**
 * ��ʹ�øߵµ�ͼ��ʱ�򣬽�����JAR������LibsĿ¼�£�
 * ���ʹ�õ�ͼ�� ExceptioninInit...��
 * ����libs�ļ������½�armeabi�ļ��У�����ߵ´�������so�ļ��� 
 * 
 * ����ʹ������Ȩ�޲�����MapView����ʾ��
 * 
 * ��ǰ��С���⣬Ϊʲô�ڵ������֮�� �ҵĵ�ǰλ�ûᷢ�� Щ��䶯��
 * @author ����
 *
 */
public class NavigateActivity extends Activity implements LocationSource,AMapLocationListener,
OnMarkerClickListener, InfoWindowAdapter, OnItemSelectedListener,
OnPoiSearchListener, OnMapClickListener, OnInfoWindowClickListener,OnClickListener
{
	private MapView mapView;
	private AMap aMap;	
	private TextView locationText;
	private OnLocationChangedListener mListener;
	private AMapLocationClient mlocationClient;
	private AMapLocationClientOption mLocationOption;
	private LatLng currentLatLng;		// ���嵱ǰλ��Latlng
	private Location currentLocation;			//���嵱ǰλ��
	
	private ProgressDialog progDialog = null;
	private String searchItem;
	private String deepType = "";// poi��������
	private PoiResult poiResult;
	private int currentPage = 0;
	private PoiSearch.Query query;
	private LatLonPoint currentLatLonPoint;
	private Marker locationMarker;
	private PoiSearch poiSearch;
	private PoiOverlay poiOverlay;// poiͼ��
	private List<PoiItem> poiItems;// poi����
	private MarkerOptions markerOption;	//	�Զ������ͼ��
	private Marker marker;
	
	private Button locatBtn;// �������
	private Button nextBtn;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigate);
	    /*
         * �������ߵ�ͼ�洢Ŀ¼�����������ߵ�ͼ���ʼ����ͼ����;
         * ʹ�ù����п���������, ���������������ߵ�ͼ�洢��·����
         * ����Ҫ�����ߵ�ͼ���غ�ʹ�õ�ͼҳ�涼����·������
         * */
	    //Demo��Ϊ�������������ʹ�����ص����ߵ�ͼ��ʹ��Ĭ��λ�ô洢���������Զ�������
      //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		locationText=(TextView)findViewById(R.id.location);
		mapView.onCreate(savedInstanceState);// �˷���������д
		locatBtn=(Button)findViewById(R.id.locatbtn);
		nextBtn=(Button)findViewById(R.id.nextbtn);
		init();
	}

	/**
	 * ��ʼ��AMap����
	 */
	private void init() {
		if (aMap == null) 
		{
			aMap = mapView.getMap();
		}
		setUpMap();
		locationText.setText(getIntent().getExtras().getString("navigate_location"));
		locatBtn.setOnClickListener(this);
		nextBtn.setOnClickListener(this);
		
	}

	/**
	 * ������ϵͳ�Դ��Ļ�ȡ��ǰ����λ�ã��ٽ��������λ�ô��ݸ��ߵµ�ͼ��
	 */
	private void setUpMap() {
		System.out.println("setum Map");
		// TODO Auto-generated method stub
		LocationManager locationManager=(LocationManager) getSystemService
				(Context.LOCATION_SERVICE);
		currentLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		aMap.setOnMarkerClickListener(this);// ��ӵ��marker�����¼�
		aMap.setInfoWindowAdapter(this);// �����ʾinfowindow�����¼�
		aMap.setLocationSource(this);
		aMap.getUiSettings().setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		
//	��ΪRotate���Զ�����Camera    ��ͨ����Ҫ�Լ�������	
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		try {
			currentLatLng=new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
			
			
//			��ͷ��覴á�   ��һ�β���ʵ�Ŵ�ͼ��	�������
//				����ٳ������������  ���Խ�PASSIVE_PROVIDER��Ϊ NETWORK_PROVIDER
				CameraUpdate cameraUpdate=CameraUpdateFactory.newCameraPosition(new CameraPosition(currentLatLng, 16, 0, 0));

				//	�趨Camera֮��Ҫ�þ�ͷ����ȥ
						
				aMap.moveCamera(cameraUpdate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * ����������д
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}


	@Override
	public void activate(OnLocationChangedListener listener) {
		// TODO Auto-generated method stub
		mListener = listener;
		if (mlocationClient == null) {
			mlocationClient = new AMapLocationClient(this);
			mLocationOption = new AMapLocationClientOption();
			//���ö�λ����
			mlocationClient.setLocationListener(this);
			//����Ϊ�߾��ȶ�λģʽ
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			//���ö�λ����
			mlocationClient.setLocationOption(mLocationOption);
			mlocationClient.startLocation();
		}
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (mlocationClient != null) {
			mlocationClient.stopLocation();
			mlocationClient.onDestroy();
		}
		mlocationClient = null;
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null
					&& amapLocation.getErrorCode() == 0) {
				mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����
//				CameraUpdate cameraUpdate=CameraUpdateFactory.newCameraPosition(new CameraPosition(currentLatLng, 12, 0, 0));
				currentLatLng=new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
				currentLocation=amapLocation;
			} else {
				String errText = "��λʧ��," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}

	/**
	 * �����һҳ��ť  ��ʾ��ͼ��һҳ����ŵ�
	 */
	private void nextSearch() {
		// TODO Auto-generated method stub
		if (query != null && poiSearch != null && poiResult != null) {
			if (poiResult.getPageCount() - 1 > currentPage) {
				currentPage++;

				query.setPageNum(currentPage);// ���ò��һҳ
				poiSearch.searchPOIAsyn();
			}
		}
	}
	/**
	 * �����ʼ������ť  ��������ܱ��ŵ�
	 */
	private void doSearchQuery() {
		// TODO Auto-generated method stub
		showProgressDialog();// ��ʾ���ȿ�
//	���ԭ�е�ͼ����¼���
		
		aMap.setOnMapClickListener(null);
		currentPage = 0;
		query = new PoiSearch.Query(locationText.getText().toString(), deepType, "������");// ��һ��������ʾ�����ַ������ڶ���������ʾpoi�������ͣ�������������ʾpoi�������򣨿��ַ�������ȫ����
		query.setPageSize(10);// ����ÿҳ��෵�ض�����poiitem
		query.setPageNum(currentPage);// ���ò��һҳ

		currentLatLonPoint=new LatLonPoint(currentLatLng.latitude, currentLatLng.longitude);
		if (currentLatLonPoint != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(currentLatLonPoint, 20000, true));//
			// ������������Ϊ��lp��ΪԲ�ģ�����Χ2000�׷�Χ
			poiSearch.searchPOIAsyn();// �첽����
		}
	}
	
	private void showProgressDialog() {
		// TODO Auto-generated method stub
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("����������");
		progDialog.show();
	}
	
	private void dissmissProgressDialog() {
		if (progDialog != null) {
			progDialog.dismiss();
		}
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPoiItemSearched(PoiItem arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		// TODO Auto-generated method stub
		dissmissProgressDialog();// ���ضԻ���

		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {// ����poi�Ľ��
				if (result.getQuery().equals(query)) {// �Ƿ���ͬһ��
					poiResult = result;
					poiItems = poiResult.getPois();// ȡ�õ�һҳ��poiitem���ݣ�ҳ��������0��ʼ
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// ����������poiitem����ʱ���᷵�غ��������ؼ��ֵĳ�����Ϣ
					if (poiItems != null && poiItems.size() > 0) {
//						aMap.clear();// ����֮ǰ��ͼ��
//	������ӵ�ǰλ����ʾ
						poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();

						nextBtn.setClickable(true);// ������һҳ�ɵ�
					} else {
						Toast.makeText(this, "���������", Toast.LENGTH_LONG).show();
					}
				}
			} else {
				Toast.makeText(this, "���������", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "���������", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * �ڵ�ͼ����ʾ�ҵ�ǰ��λ�á�
	 */
	private void addMyLocation() {
		// TODO Auto-generated method stub
		marker.setTitle("�ҵ�λ��");
		
		markerOption = new MarkerOptions();
		markerOption.position(currentLatLng);
		markerOption.title("������");
 
		markerOption.draggable(true);
		markerOption.icon(
		// BitmapDescriptorFactory
		// .fromResource(R.drawable.location_marker)
				BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.location_marker)));
		// ��Marker����Ϊ������ʾ������˫ָ������Ч��
		markerOption.setFlat(true);
		aMap.addMarker(markerOption);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ���marker֮�󵯳���infowindow
	 * ������layouterInflater ��Ƹ�Ϊ���ӵĽ���
	 */
	@Override
	public View getInfoWindow(final Marker marker) {
		// TODO Auto-generated method stub
		System.out.println("in get info window method");
		View view = getLayoutInflater().inflate(R.layout.marker_detail,null);
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(marker.getTitle());

		TextView snippet = (TextView) view.findViewById(R.id.snippet);
		snippet.setText(marker.getSnippet());
		ImageButton button = (ImageButton) view
				.findViewById(R.id.start_amap_app);
		// ����ߵµ�ͼapp
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
//				startAMapNavi(marker);
				System.out.println("marker has been click "+marker.getId());
			}
		});
		return view;
//		return null;
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		marker.showInfoWindow();
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.locatbtn:
			CameraUpdate cameraUpdate=CameraUpdateFactory.newCameraPosition(new CameraPosition(currentLatLng, 16, 0, 0));
			aMap.moveCamera(cameraUpdate);
			doSearchQuery();
			break;
		case R.id.nextbtn:
			nextSearch();
			break;
		}
	}

}

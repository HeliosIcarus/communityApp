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
 * 在使用高德地图的时候，将三个JAR包放在Libs目录下，
 * 如果使用地图报 ExceptioninInit...错
 * 则在libs文件夹下新建armeabi文件夹，放入高德带的两个so文件。 
 * 
 * 加入使用网络权限才能在MapView中显示。
 * 
 * 当前有小问题，为什么在点击搜索之后 我的当前位置会发生 些许变动。
 * @author 翔宇
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
	private LatLng currentLatLng;		// 定义当前位置Latlng
	private Location currentLocation;			//定义当前位置
	
	private ProgressDialog progDialog = null;
	private String searchItem;
	private String deepType = "";// poi搜索类型
	private PoiResult poiResult;
	private int currentPage = 0;
	private PoiSearch.Query query;
	private LatLonPoint currentLatLonPoint;
	private Marker locationMarker;
	private PoiSearch poiSearch;
	private PoiOverlay poiOverlay;// poi图层
	private List<PoiItem> poiItems;// poi数据
	private MarkerOptions markerOption;	//	自定义添加图标
	private Marker marker;
	
	private Button locatBtn;// 点击搜索
	private Button nextBtn;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigate);
	    /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置;
         * 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         * */
	    //Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
      //  MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);
		mapView = (MapView) findViewById(R.id.map);
		locationText=(TextView)findViewById(R.id.location);
		mapView.onCreate(savedInstanceState);// 此方法必须重写
		locatBtn=(Button)findViewById(R.id.locatbtn);
		nextBtn=(Button)findViewById(R.id.nextbtn);
		init();
	}

	/**
	 * 初始化AMap对象
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
	 * 首先用系统自带的获取当前地理位置，再将这个地理位置传递给高德地图。
	 */
	private void setUpMap() {
		System.out.println("setum Map");
		// TODO Auto-generated method stub
		LocationManager locationManager=(LocationManager) getSystemService
				(Context.LOCATION_SERVICE);
		currentLocation=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		aMap.setOnMarkerClickListener(this);// 添加点击marker监听事件
		aMap.setInfoWindowAdapter(this);// 添加显示infowindow监听事件
		aMap.setLocationSource(this);
		aMap.getUiSettings().setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		
//	改为Rotate会自动调整Camera    普通的需要自己调整。	
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		try {
			currentLatLng=new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
			
			
//			镜头有瑕疵。   第一次不现实放大图。	功能完成
//				如果再出出现这个问题  尝试将PASSIVE_PROVIDER改为 NETWORK_PROVIDER
				CameraUpdate cameraUpdate=CameraUpdateFactory.newCameraPosition(new CameraPosition(currentLatLng, 16, 0, 0));

				//	设定Camera之后要让镜头动过去
						
				aMap.moveCamera(cameraUpdate);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
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
			//设置定位监听
			mlocationClient.setLocationListener(this);
			//设置为高精度定位模式
			mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			//设置定位参数
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
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
//				CameraUpdate cameraUpdate=CameraUpdateFactory.newCameraPosition(new CameraPosition(currentLatLng, 12, 0, 0));
				currentLatLng=new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
				currentLocation=amapLocation;
			} else {
				String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
				Log.e("AmapErr",errText);
			}
		}
	}

	/**
	 * 点击下一页按钮  显示地图下一页相关门店
	 */
	private void nextSearch() {
		// TODO Auto-generated method stub
		if (query != null && poiSearch != null && poiResult != null) {
			if (poiResult.getPageCount() - 1 > currentPage) {
				currentPage++;

				query.setPageNum(currentPage);// 设置查后一页
				poiSearch.searchPOIAsyn();
			}
		}
	}
	/**
	 * 点击开始搜索按钮  搜索相关周边门店
	 */
	private void doSearchQuery() {
		// TODO Auto-generated method stub
		showProgressDialog();// 显示进度框
//	清除原有地图点击事件。
		
		aMap.setOnMapClickListener(null);
		currentPage = 0;
		query = new PoiSearch.Query(locationText.getText().toString(), deepType, "北京市");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(10);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页

		currentLatLonPoint=new LatLonPoint(currentLatLng.latitude, currentLatLng.longitude);
		if (currentLatLonPoint != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(currentLatLonPoint, 20000, true));//
			// 设置搜索区域为以lp点为圆心，其周围2000米范围
			poiSearch.searchPOIAsyn();// 异步搜索
		}
	}
	
	private void showProgressDialog() {
		// TODO Auto-generated method stub
		if (progDialog == null)
			progDialog = new ProgressDialog(this);
		progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progDialog.setIndeterminate(false);
		progDialog.setCancelable(false);
		progDialog.setMessage("正在搜索中");
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
		dissmissProgressDialog();// 隐藏对话框

		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					List<SuggestionCity> suggestionCities = poiResult
							.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
					if (poiItems != null && poiItems.size() > 0) {
//						aMap.clear();// 清理之前的图标
//	这里添加当前位置显示
						poiOverlay = new PoiOverlay(aMap, poiItems);
						poiOverlay.removeFromMap();
						poiOverlay.addToMap();

						nextBtn.setClickable(true);// 设置下一页可点
					} else {
						Toast.makeText(this, "无搜索结果", Toast.LENGTH_LONG).show();
					}
				}
			} else {
				Toast.makeText(this, "无搜索结果", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "无搜索结果", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 在地图上显示我当前的位置。
	 */
	private void addMyLocation() {
		// TODO Auto-generated method stub
		marker.setTitle("我的位置");
		
		markerOption = new MarkerOptions();
		markerOption.position(currentLatLng);
		markerOption.title("西安市");
 
		markerOption.draggable(true);
		markerOption.icon(
		// BitmapDescriptorFactory
		// .fromResource(R.drawable.location_marker)
				BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.location_marker)));
		// 将Marker设置为贴地显示，可以双指下拉看效果
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
	 * 点击marker之后弹出的infowindow
	 * 可以用layouterInflater 设计更为复杂的界面
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
		// 调起高德地图app
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

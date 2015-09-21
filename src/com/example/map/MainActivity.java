package com.example.map;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private GoogleMap googleMap;
	private Marker mMarker = null;
	private LatLng position = null;
	private double myLatitude = 0;
	private double myLongitude = 0;
	private Button mbutton = null;
	private EditText number = null;
	private int number1 = 0;
	private LatLng latlng = null;
			


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 latlng = new LatLng(35.710065, 139.8107);
		mbutton = (Button) findViewById(R.id.button1);
		mbutton.setOnClickListener(MainActivity.this);

		SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map);
		googleMap = supportMapFragment.getMap();
		if (googleMap != null) {
			googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("現在位置"));
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
			googleMap.addMarker(new MarkerOptions().position(latlng).title("現在位置"));

			googleMap.setMyLocationEnabled(true);
			UiSettings settings = googleMap.getUiSettings();
			settings.setMyLocationButtonEnabled(true);
			googleMap.addCircle(
					new CircleOptions().center(new LatLng(latlng.latitude, latlng.longitude))
							.radius(500).strokeColor(Color.GREEN).fillColor(Color.BLUE));

			
			mMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("現在地"));

			/*googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
				public void onMyLocationChange(Location location) {
					googleMap.addCircle(
							new CircleOptions().center(new LatLng(location.getLatitude(), location.getLongitude()))
									.radius(500).strokeColor(Color.RED).fillColor(Color.BLUE));

					myLatitude = location.getLatitude();
					// 経度を取得
					myLongitude = location.getLongitude();
					position = new LatLng(myLatitude, myLongitude);
					
					
				}

			});*/

			googleMap.setOnMapClickListener(new OnMapClickListener() {
				@Override
				public void onMapClick(LatLng point) {
					// TODO Auto-generated method stub
					// タッチ地点と目的地との最短距離の計算
					float[] results = new float[1];
					/*
					 * double myLatitude = location.getLatitude(); //経度を取得
					 * double myLongitude = location.getLongitude(); LatLng
					 * position = new LatLng(myLatitude,myLongitude);
					 */
					Location.distanceBetween(point.latitude, point.longitude, latlng.latitude, latlng.longitude,
							results);
/*					Location.distanceBetween(point.latitude, point.longitude, position.latitude, position.longitude,
							results);
*/					
					Toast.makeText(getApplicationContext(), "ここからの距離：" + results[0] + "m", Toast.LENGTH_LONG).show();
					mMarker.setPosition(point);

					// 測地線
					PolylineOptions geodesics = new PolylineOptions().add(point, latlng) // 2地点設定
							.geodesic(true) // 測地線
							.color(Color.RED).width(3);
					googleMap.addPolyline(geodesics);
				}
			});

		}
	}

	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == mbutton) {
			 number = (EditText)findViewById(R.id.editText1);
			number1 = Integer.parseInt(number.getText().toString());
			googleMap.addCircle(new CircleOptions().center(new LatLng(latlng.latitude, latlng.longitude))
					.radius(number1).strokeColor(Color.RED));
			
		}
	}

}

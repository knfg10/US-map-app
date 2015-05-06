package brighton.uni.usmappedapp;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin.Response;
import com.google.api.client.http.HttpResponse;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentActivity;


public class Map extends FragmentActivity  {

	/**
	 * @param args
	 */

	private GoogleMap Map;	//Creating Google Map
	private Button btnClosePopup;
	public String texttest = "Hello";

	ArrayList<String> Types = new ArrayList<String>();  // Array list for types of poi
	ArrayList<String> Cities = new ArrayList<String>();
	ArrayList<String> Cities2 = new ArrayList<String>();

	ArrayList<GooglePlaces> venuesList; 	// Array list for creating map markers
	ArrayList<GooglePlaces> venuesList2;


	// Camera Location on map
	private static final LatLng CENTER_USA = 
			new LatLng(39.50, -98.20);
	// Example Location for map
	private static final LatLng GOLDEN_GATE_BRIDGE = 
			new LatLng(37.828891,-122.485884);
	// Second Example Location
	private static final LatLng APPLE = 
			new LatLng(37.3325004578, -122.03099823);



	// <------ Places In Cities = Locations -------->
	//	private static final LatLng ALBURQUERQUE = 
	//			new LatLng(35.1107, -106.6100);
	private static final LatLng ATLANTA = 
			new LatLng(33.7550,-84.3900);
	//	private static final LatLng AUSTIN = 
	//			new LatLng(30.2500, -97.7500);
	//	private static final LatLng BALTIMORE = 
	//			new LatLng(39.2833, -76.6167);
	private static final LatLng BOSTON = 
			new LatLng(42.3601, -71.0589);
	//	private static final LatLng CHARLOTTE = 
	//			new LatLng(35.2269, -80.8433);
	private static final LatLng CHICAGO = 
			new LatLng(41.8369,-87.6847);
	private static final LatLng DALLAS = 
			new LatLng(32.7767, -96.7970);
	private static final LatLng HOUSTON = 
			new LatLng(29.7604, -95.3698);
	private static final LatLng KANSAS_CITY = 
			new LatLng(39.0997, -94.5783);
	private static final LatLng LAS_VEGAS = 
			new LatLng(36.1215, -115.1739);
	private static final LatLng LOS_ANGELES = 
			new LatLng(34.0500,-118.2500);
	private static final LatLng MIAMI = 
			new LatLng(25.7753, -80.2089);
	private static final LatLng NEW_ORLEANS = 
			new LatLng(29.9500, -90.0667);
	private static final LatLng NEW_YORK = 
			new LatLng(40.7463956, -73.9852992);
	private static final LatLng ORLANDO = 
			new LatLng(28.4158, -81.2989);
	//	private static final LatLng PHOENIX = 
	//			new LatLng(33.4500, -112.0667);
	private static final LatLng SEATTLE = 
			new LatLng(47.6097,-122.3331);
	private static final LatLng SAN_DIEGO = 
			new LatLng(32.7150, -117.1625);
	private static final LatLng SAN_FRANCISCO = 
			new LatLng(37.7833, -122.4167);
	//	private static final LatLng TAMPA = 
	//			new LatLng(27.9681, -82.4764);
	private static final LatLng WASHINGTON = 
			new LatLng(38.9047, -77.0164);


	// The Google API Key
	final String GOOGLE_KEY = "ADD_KEY";

	final String FOURSQUARE_CLIENTID = "ADD_CLIENTID";
	final String FOURSQUARE_CLIENTSECRET = "ADD_CLIENTSECRET";
	//	final String GOOGLE_KEY = "";
	//	final String GOOGLE_KEY = "";
	//	final String GOOGLE_KEY = "";

	// Example Location, Center of New York
	final String NYlatitude = "40.7463956";
	final String NYlongtitude = "-73.9852992";


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout); 	// Use pre-made Layout xml file

		
		// Creates and loads map
		Map = ((SupportMapFragment)getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap(); 	

		// Positions the view of the map to the center of america
		CameraPosition startUSPosition = new CameraPosition.Builder()
		.target(CENTER_USA) // Sets the center of the map to USA
		.zoom(3)                   // Sets the zoom
		.bearing(0) // Sets the orientation of the camera to east
		.tilt(0)    // Sets the tilt of the camera to degrees
		.build();    // Creates a CameraPosition from the builder
		Map.animateCamera(CameraUpdateFactory.newCameraPosition(
				startUSPosition));

		// Enabling the use of Location
		Map.setMyLocationEnabled(true);

		Map.getUiSettings().setCompassEnabled(true);
		//Map.getUiSettings().setScrollGesturesEnabled(true);
		Map.getUiSettings().setAllGesturesEnabled(true);
		Map.getUiSettings().setZoomControlsEnabled(true);

		//Tests If Map Loads
		if (Map == null) {
			Toast.makeText(this, "Google Maps not available", 
					Toast.LENGTH_LONG).show();
		}
	}	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main2, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		Intent weatherPage = new Intent(this, Weather.class); 	// This Creates an intent to load 
		// the weather page from menu item

		// This implements all the accessable menus
		switch (item.getItemId()) {


		// Map View Types Option Menu
		case R.id.action_normal:
			Map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.action_terrain:
			Map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			break;
		case R.id.action_hybrid:
			Map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.action_satellite:
			Map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;
		case R.id.action_3d_on:
			Map.setBuildingsEnabled(true);
			break;
		case R.id.action_3d_off:
			Map.setBuildingsEnabled(true);
			break;



			// Option Menu for Finding POIs 
		case R.id.menu_atm:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiAtm = "atm"; 	// String defining POI type
			Types.add(poiAtm); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation11 = Map.getMyLocation();
			double atmlat = myLocation11.getLatitude(); 	// Creates double with current Lat value 
			double atmlon = myLocation11.getLongitude(); 	// Creates double with current Lng value
			String atmlat1 = String.valueOf(atmlat); 	// Creates String from Lat double
			String atmlon1 = String.valueOf(atmlon); 	// Creates String from Lng double
			LatLng myLatLng11 = new LatLng(myLocation11.getLatitude(),
					myLocation11.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition11 = new CameraPosition.Builder()
			.target(myLatLng11).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition11));

			Cities.add(atmlat1);
			Cities2.add(atmlon1);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_airport:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiAirport = "airport"; 	// String defining POI type
			Types.add(poiAirport); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation0 = Map.getMyLocation();
			double alat1 = myLocation0.getLatitude(); 	// Creates double with current Lat value 
			double alon1 = myLocation0.getLongitude(); 	// Creates double with current Lng value
			String lat = String.valueOf(alat1); 	// Creates String from Lat double
			String lon = String.valueOf(alon1); 	// Creates String from Lng double
			LatLng myLatLng3 = new LatLng(myLocation0.getLatitude(),
					myLocation0.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition3 = new CameraPosition.Builder()
			.target(myLatLng3).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition3));

			Cities.add(lat);
			Cities2.add(lon);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_bank:

			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiBank = "bank"; 	// String defining POI type
			Types.add(poiBank); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation1 = Map.getMyLocation();
			double blat = myLocation1.getLatitude(); 	// Creates double with current Lat value 
			double blon = myLocation1.getLongitude(); 	// Creates double with current Lng value
			String lat1 = String.valueOf(blat); 	// Creates String from Lat double
			String lon1 = String.valueOf(blon); 	// Creates String from Lng double
			LatLng myLatLng4 = new LatLng(myLocation1.getLatitude(),
					myLocation1.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition4 = new CameraPosition.Builder()
			.target(myLatLng4).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition4));

			Cities.add(lat1);
			Cities2.add(lon1);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_bar:

			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiBar = "bar"; 	// String defining POI type
			Types.add(poiBar); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation2 = Map.getMyLocation();
			double blat1 = myLocation2.getLatitude(); 	// Creates double with current Lat value 
			double blon1 = myLocation2.getLongitude(); 	// Creates double with current Lng value
			String lat2 = String.valueOf(blat1); 	// Creates String from Lat double
			String lon2 = String.valueOf(blon1); 	// Creates String from Lng double
			LatLng myLatLng5 = new LatLng(myLocation2.getLatitude(),
					myLocation2.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition5 = new CameraPosition.Builder()
			.target(myLatLng5).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition5));

			Cities.add(lat2);
			Cities2.add(lon2);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_bus_station:

			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiBusStation = "movietheater"; 	// String defining POI type
			Types.add(poiBusStation); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation3 = Map.getMyLocation();
			double blat2 = myLocation3.getLatitude(); 	// Creates double with current Lat value 
			double blon2 = myLocation3.getLongitude(); 	// Creates double with current Lng value
			String lat3 = String.valueOf(blat2); 	// Creates String from Lat double
			String lon3 = String.valueOf(blon2); 	// Creates String from Lng double
			LatLng myLatLng6 = new LatLng(myLocation3.getLatitude(),
					myLocation3.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition6 = new CameraPosition.Builder()
			.target(myLatLng6).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition6));

			Cities.add(lat3);
			Cities2.add(lon3);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_cafe:

			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiCafe = "cafe"; 	// String defining POI type
			Types.add(poiCafe); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation4 = Map.getMyLocation();
			double clat = myLocation4.getLatitude(); 	// Creates double with current Lat value 
			double clon = myLocation4.getLongitude(); 	// Creates double with current Lng value
			String lat4 = String.valueOf(clat); 	// Creates String from Lat double
			String lon4 = String.valueOf(clon); 	// Creates String from Lng double
			LatLng myLatLng7 = new LatLng(myLocation4.getLatitude(),
					myLocation4.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition7 = new CameraPosition.Builder()
			.target(myLatLng7).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition7));

			Cities.add(lat4);
			Cities2.add(lon4);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_hospital:

			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiHospital = "Hospital"; 	// String defining POI type
			Types.add(poiHospital); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation5 = Map.getMyLocation();
			double hlat = myLocation5.getLatitude(); 	// Creates double with current Lat value 
			double hlon = myLocation5.getLongitude(); 	// Creates double with current Lng value
			String lat5 = String.valueOf(hlat); 	// Creates String from Lat double
			String lon5 = String.valueOf(hlon); 	// Creates String from Lng double
			LatLng myLatLng8 = new LatLng(myLocation5.getLatitude(),
					myLocation5.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition8 = new CameraPosition.Builder()
			.target(myLatLng8).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition8));

			Cities.add(lat5);
			Cities2.add(lon5);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_hostel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiHostel = "hostel"; 	// String defining POI type
			Types.add(poiHostel); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation10 = Map.getMyLocation();
			double hlat1 = myLocation10.getLatitude(); 	// Creates double with current Lat value 
			double hlon1 = myLocation10.getLongitude(); 	// Creates double with current Lng value
			String lat10 = String.valueOf(hlat1); 	// Creates String from Lat double
			String lon10 = String.valueOf(hlon1); 	// Creates String from Lng double
			LatLng myLatLng13 = new LatLng(myLocation10.getLatitude(),
					myLocation10.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition13 = new CameraPosition.Builder()
			.target(myLatLng13).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition13));

			Cities.add(lat10);
			Cities2.add(lon10);

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_hotel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiHotel = "hotel"; 	// String defining POI type
			Types.add(poiHotel); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation12 = Map.getMyLocation();
			double hlat2 = myLocation12.getLatitude(); 	// Creates double with current Lat value 
			double hlon2 = myLocation12.getLongitude(); 	// Creates double with current Lng value
			String lat11 = String.valueOf(hlat2); 	// Creates String from Lat double
			String lon11 = String.valueOf(hlon2); 	// Creates String from Lng double
			LatLng myLatLng14 = new LatLng(myLocation12.getLatitude(),
					myLocation12.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition14 = new CameraPosition.Builder()
			.target(myLatLng14).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition14));

			Cities.add(lat11);
			Cities2.add(lon11);

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_movie_theater:

			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiMovieTheater = "movie_theater"; 	// String defining POI type
			Types.add(poiMovieTheater); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation6 = Map.getMyLocation();
			double mlat = myLocation6.getLatitude(); 	// Creates double with current Lat value 
			double mlon = myLocation6.getLongitude(); 	// Creates double with current Lng value
			String lat6 = String.valueOf(mlat); 	// Creates String from Lat double
			String lon6 = String.valueOf(mlon); 	// Creates String from Lng double
			LatLng myLatLng9 = new LatLng(myLocation6.getLatitude(),
					myLocation6.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition9 = new CameraPosition.Builder()
			.target(myLatLng9).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition9));

			Cities.add(lat6);
			Cities2.add(lon6);

			// Executes the API Code 
			new googleplaces().execute();
			new foursquareplaces().execute();
			break;
		case R.id.menu_museum:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiMuseum = "museum"; 	// String defining POI type
			Types.add(poiMuseum); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation7 = Map.getMyLocation();
			double mlat0 = myLocation7.getLatitude(); 	// Creates double with current Lat value 
			double mlon0 = myLocation7.getLongitude(); 	// Creates double with current Lng value
			String lat7 = String.valueOf(mlat0); 	// Creates String from Lat double
			String lon7 = String.valueOf(mlon0); 	// Creates String from Lng double
			LatLng myLatLng10 = new LatLng(myLocation7.getLatitude(),
					myLocation7.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition10 = new CameraPosition.Builder()
			.target(myLatLng10).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition10));

			Cities.add(lat7);
			Cities2.add(lon7);

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_restaurant:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiRestaurant = "restaurant"; 	// String defining POI type
			Types.add(poiRestaurant); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation8 = Map.getMyLocation();
			double rlat = myLocation8.getLatitude(); 	// Creates double with current Lat value 
			double rlon = myLocation8.getLongitude(); 	// Creates double with current Lng value
			String lat8 = String.valueOf(rlat); 	// Creates String from Lat double
			String lon8 = String.valueOf(rlon); 	// Creates String from Lng double
			LatLng myLatLng111 = new LatLng(myLocation8.getLatitude(),
					myLocation8.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition111 = new CameraPosition.Builder()
			.target(myLatLng111).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition111));

			Cities.add(lat8);
			Cities2.add(lon8);

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_subway_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String poiSubwayStation = "subway_station"; 	// String defining POI type
			Types.add(poiSubwayStation); 	// Adding the made string of POI to the 'Types' arraylist

			// Retrieving Current Location
			Location myLocation9 = Map.getMyLocation();
			double slat = myLocation9.getLatitude(); 	// Creates double with current Lat value 
			double slon = myLocation9.getLongitude(); 	// Creates double with current Lng value
			String lat9 = String.valueOf(slat); 	// Creates String from Lat double
			String lon9 = String.valueOf(slon); 	// Creates String from Lng double
			LatLng myLatLng12 = new LatLng(myLocation9.getLatitude(),
					myLocation9.getLongitude());

			// Sets the camera position near current location an POIs 
			CameraPosition myPosition12 = new CameraPosition.Builder()
			.target(myLatLng12).zoom(14).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(myPosition12));

			Cities.add(lat9);
			Cities2.add(lon9);

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_clear_map:
			Map.clear();
			break;



			// Atlanta Places
		case R.id.menu_atatm:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT = "atm"; 	// String defining POI type
			String ATlat = "33.7550";
			String ATlng = "-84.3900";
			Types.add(AT); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat);
			Cities2.add(ATlng);

			CameraPosition atlanta = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atairport:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT0 = "airport"; 	// String defining POI type
			String ATlat0 = "33.7550";
			String ATlng0 = "-84.3900";
			Types.add(AT0); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat0);
			Cities2.add(ATlng0);

			CameraPosition atlanta0 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta0));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atbank:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT1 = "bank"; 	// String defining POI type
			String ATlat1 = "33.7550";
			String ATlng1 = "-84.3900";
			Types.add(AT1); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat1);
			Cities2.add(ATlng1);

			CameraPosition atlanta1 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta1));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atbar:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT2 = "bar"; 	// String defining POI type
			String ATlat2 = "33.7550";
			String ATlng2 = "-84.3900";
			Types.add(AT2); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat2);
			Cities2.add(ATlng2);

			CameraPosition atlanta2 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta2));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atbus_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT3 = "bus_station"; 	// String defining POI type
			String ATlat3 = "33.7550";
			String ATlng3 = "-84.3900";
			Types.add(AT3); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat3);
			Cities2.add(ATlng3);

			CameraPosition atlanta3 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta3));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atcafe:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT4 = "cafe"; 	// String defining POI type
			String ATlat4 = "33.7550";
			String ATlng4 = "-84.3900";
			Types.add(AT4); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat4);
			Cities2.add(ATlng4);

			CameraPosition atlanta4 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta4));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_athospital:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT5 = "hospital"; 	// String defining POI type
			String ATlat5 = "33.7550";
			String ATlng5 = "-84.3900";
			Types.add(AT5); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat5);
			Cities2.add(ATlng5);

			CameraPosition atlanta5 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta5));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;

		case R.id.menu_athostel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT44 = "hostel"; 	// String defining POI type
			String ATlat44 = "33.7550";
			String ATlng44 = "-84.3900";
			Types.add(AT44); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat44);
			Cities2.add(ATlng44);

			CameraPosition atlanta44 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta44));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_athotel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT55 = "hotel"; 	// String defining POI type
			String ATlat55 = "33.7550";
			String ATlng55 = "-84.3900";
			Types.add(AT55); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat55);
			Cities2.add(ATlng55);

			CameraPosition atlanta55 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta55));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atmovie_theater:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT6 = "movie_theater"; 	// String defining POI type
			String ATlat6 = "33.7550";
			String ATlng6 = "-84.3900";
			Types.add(AT6); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat6);
			Cities2.add(ATlng6);

			CameraPosition atlanta6 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta6));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atmuseum:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT7 = "museum"; 	// String defining POI type
			String ATlat7 = "33.7550";
			String ATlng7 = "-84.3900";
			Types.add(AT7); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat7);
			Cities2.add(ATlng7);

			CameraPosition atlanta7 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta7));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atfood:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT8 = "food"; 	// String defining POI type
			String ATlat8 = "33.7550";
			String ATlng8 = "-84.3900";
			Types.add(AT8); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat8);
			Cities2.add(ATlng8);

			CameraPosition atlanta8 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta8));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_atsubway_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String AT9 = "subway_station"; 	// String defining POI type
			String ATlat9 = "33.7550";
			String ATlng9 = "-84.3900";
			Types.add(AT9); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(ATlat9);
			Cities2.add(ATlng9);

			CameraPosition atlanta9 = new CameraPosition.Builder()
			.target(ATLANTA).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(atlanta9));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
			//		case R.id.menu_boston:
			//			CameraPosition boston = new CameraPosition.Builder()
			//			.target(BOSTON).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(boston));			
			//			break;
			//		case R.id.menu_chicago:
			//			CameraPosition chicago = new CameraPosition.Builder()
			//			.target(CHICAGO).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(chicago));			
			//			break;
			//		case R.id.menu_dallas:
			//			CameraPosition dallas = new CameraPosition.Builder()
			//			.target(DALLAS).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(dallas));			
			//			break;
			//		case R.id.menu_houston:
			//			CameraPosition houston = new CameraPosition.Builder()
			//			.target(HOUSTON).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(houston));			
			//			break;
			//		case R.id.menu_kansascity:
			//			CameraPosition kansascity = new CameraPosition.Builder()
			//			.target(KANSAS_CITY).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(kansascity));			
			//			break;


			//  Las Vegas Places
		case R.id.menu_lasatm:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS = "atm"; 	// String defining POI type
			String LASlat = "36.1215";
			String LASlng = "-115.1739";
			Types.add(LAS); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat);
			Cities2.add(LASlng);

			CameraPosition lasvegas = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasairport:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS0 = "airport"; 	// String defining POI type
			String LASlat0 = "36.1215";
			String LASlng0 = "-115.1739";
			Types.add(LAS0); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat0);
			Cities2.add(LASlng0);

			CameraPosition lasvegas0 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas0));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasbank:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS1 = "bank"; 	// String defining POI type
			String LASlat1 = "36.1215";
			String LASlng1 = "-115.1739";
			Types.add(LAS1); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat1);
			Cities2.add(LASlng1);

			CameraPosition lasvegas1 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas1));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasbar:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS2 = "bar"; 	// String defining POI type
			String LASlat2 = "36.1215";
			String LASlng2 = "-115.1739";
			Types.add(LAS2); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat2);
			Cities2.add(LASlng2);

			CameraPosition lasvegas2 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas2));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasbus_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS3 = "bus_station"; 	// String defining POI type
			String LASlat3 = "36.1215";
			String LASlng3 = "-115.1739";
			Types.add(LAS3); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat3);
			Cities2.add(LASlng3);

			CameraPosition lasvegas3 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas3));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lascafe:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS4 = "cafe"; 	// String defining POI type
			String LASlat4 = "36.1215";
			String LASlng4 = "-115.1739";
			Types.add(LAS4); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat4);
			Cities2.add(LASlng4);

			CameraPosition lasvegas4 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas4));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lashospital:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS5 = "hospital"; 	// String defining POI type
			String LASlat5 = "36.1215";
			String LASlng5 = "-115.1739";
			Types.add(LAS5); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat5);
			Cities2.add(LASlng5);

			CameraPosition lasvegas5 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas5));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;

		case R.id.menu_lashostel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS44 = "hostel"; 	// String defining POI type
			String LASlat44 = "36.1215";
			String LASlng44 = "-115.1739";
			Types.add(LAS44); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat44);
			Cities2.add(LASlng44);

			CameraPosition lasvegas44 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas44));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lashotel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS55 = "hotel"; 	// String defining POI type
			String LASlat55 = "36.1215";
			String LASlng55 = "-115.1739";
			Types.add(LAS55); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat55);
			Cities2.add(LASlng55);

			CameraPosition lasvegas55 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas55));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasmovie_theater:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS6 = "movie_theater"; 	// String defining POI type
			String LASlat6 = "36.1215";
			String LASlng6 = "-115.1739";
			Types.add(LAS6); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat6);
			Cities2.add(LASlng6);

			CameraPosition lasvegas6 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas6));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasmuseum:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS7 = "museum"; 	// String defining POI type
			String LASlat7 = "36.1215";
			String LASlng7 = "-115.1739";
			Types.add(LAS7); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat7);
			Cities2.add(LASlng7);

			CameraPosition lasvegas7 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas7));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lasfood:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS8 = "food"; 	// String defining POI type
			String LASlat8 = "36.1215";
			String LASlng8 = "-115.1739";
			Types.add(LAS8); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat8);
			Cities2.add(LASlng8);

			CameraPosition lasvegas8 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas8));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lassubway_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LAS9 = "subway_station"; 	// String defining POI type
			String LASlat9 = "36.1215";
			String LASlng9 = "-115.1739";
			Types.add(LAS9); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LASlat9);
			Cities2.add(LASlng9);

			CameraPosition lasvegas9 = new CameraPosition.Builder()
			.target(LAS_VEGAS).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(lasvegas9));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;





			// Los Angeles Places
		case R.id.menu_losatm:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS = "atm"; 	// String defining POI type
			String LOSlat = "34.0500";
			String LOSlng = "-118.2500";
			Types.add(LOS); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat);
			Cities2.add(LOSlng);

			CameraPosition losangeles = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losangeles));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losairport:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS0 = "airport"; 	// String defining POI type
			String LOSlat0 = "34.0500";
			String LOSlng0 = "-118.2500";
			Types.add(LOS0); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat0);
			Cities2.add(LOSlng0);

			CameraPosition losangeles0 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losangeles0));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losbank:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS1 = "bank"; 	// String defining POI type
			String LOSlat1 = "34.0500";
			String LOSlng1 = "-118.2500";
			Types.add(LOS1); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat1);
			Cities2.add(LOSlng1);

			CameraPosition losanggeles1 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles1));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losbar:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS2 = "bar"; 	// String defining POI type
			String LOSlat2 = "34.0500";
			String LOSlng2 = "-118.2500";
			Types.add(LOS2); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat2);
			Cities2.add(LOSlng2);

			CameraPosition losanggeles2 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles2));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losbus_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS3 = "bus_station"; 	// String defining POI type
			String LOSlat3 = "34.0500";
			String LOSlng3 = "-118.2500";
			Types.add(LOS3); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat3);
			Cities2.add(LOSlng3);

			CameraPosition losanggeles3 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles3));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_loscafe:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS4 = "cafe"; 	// String defining POI type
			String LOSlat4 = "34.0500";
			String LOSlng4 = "-118.2500";
			Types.add(LOS4); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat4);
			Cities2.add(LOSlng4);

			CameraPosition losanggeles4 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles4));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_loshospital:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS5 = "hospital"; 	// String defining POI type
			String LOSlat5 = "34.0500";
			String LOSlng5 = "-118.2500";
			Types.add(LOS5); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat5);
			Cities2.add(LOSlng5);

			CameraPosition losanggeles5 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles5));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;

		case R.id.menu_loshostel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS44 = "hostel"; 	// String defining POI type
			String LOSlat44 = "34.0500";
			String LOSlng44 = "-118.2500";
			Types.add(LOS44); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat44);
			Cities2.add(LOSlng44);

			CameraPosition losanggeles44 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles44));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_loshotel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS55 = "hotel"; 	// String defining POI type
			String LOSlat55 = "34.0500";
			String LOSlng55 = "-118.2500";
			Types.add(LOS55); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat55);
			Cities2.add(LOSlng55);

			CameraPosition losanggeles55 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles55));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losmovie_theater:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS6 = "movie_theater"; 	// String defining POI type
			String LOSlat6 = "34.0500";
			String LOSlng6 = "-118.2500";
			Types.add(LOS6); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat6);
			Cities2.add(LOSlng6);

			CameraPosition losanggeles6 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles6));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losmuseum:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS7 = "museum"; 	// String defining POI type
			String LOSlat7 = "34.0500";
			String LOSlng7 = "-118.2500";
			Types.add(LOS7); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat7);
			Cities2.add(LOSlng7);

			CameraPosition losanggeles7 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles7));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_losfood:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS8 = "food"; 	// String defining POI type
			String LOSlat8 = "34.0500";
			String LOSlng8 = "-118.2500";
			Types.add(LOS8); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat8);
			Cities2.add(LOSlng8);

			CameraPosition losanggeles8 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles8));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_lossubway_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String LOS9 = "subway_station"; 	// String defining POI type
			String LOSlat9 = "34.0500";
			String LOSlng9 = "-118.2500";
			Types.add(LOS9); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(LOSlat9);
			Cities2.add(LOSlng9);

			CameraPosition losanggeles9 = new CameraPosition.Builder()
			.target(LOS_ANGELES).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(losanggeles9));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;



			


			// Los Miami Places
		case R.id.menu_miatm:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI = "atm"; 	// String defining POI type
			String MIlat = "25.7753";
			String MIlng = "-80.2089";
			Types.add(MI); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat);
			Cities2.add(MIlng);

			CameraPosition miami = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_miairport:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI0 = "airport"; 	// String defining POI type
			String MIlat0 = "25.7753";
			String MIlng0 = "-80.2089";
			Types.add(MI0); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat0);
			Cities2.add(MIlng0);

			CameraPosition miami0 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami0));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mibank:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI1 = "bank"; 	// String defining POI type
			String MIlat1 = "25.7753";
			String MIlng1 = "-80.2089";
			Types.add(MI1); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat1);
			Cities2.add(MIlng1);

			CameraPosition miami1 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami1));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mibar:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI2 = "bar"; 	// String defining POI type
			String MIlat2 = "25.7753";
			String MIlng2 = "-80.2089";
			Types.add(MI2); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat2);
			Cities2.add(MIlng2);

			CameraPosition miami2 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami2));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mibus_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI3 = "bus_station"; 	// String defining POI type
			String MIlat3 = "25.7753";
			String MIlng3 = "-80.2089";
			Types.add(MI3); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat3);
			Cities2.add(MIlng3);

			CameraPosition miami3 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami3));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_micafe:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI4 = "cafe"; 	// String defining POI type
			String MIlat4 = "25.7753";
			String MIlng4 = "-80.2089";
			Types.add(MI4); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat4);
			Cities2.add(MIlng4);

			CameraPosition miami4 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami4));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mihospital:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI5 = "hospital"; 	// String defining POI type
			String MIlat5 = "25.7753";
			String MIlng5 = "-80.2089";
			Types.add(MI5); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat5);
			Cities2.add(MIlng5);

			CameraPosition miami5 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami5));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;

		case R.id.menu_mihostel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI44 = "hostel"; 	// String defining POI type
			String MIlat44 = "25.7753";
			String MIlng44 = "-80.2089";
			Types.add(MI44); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat44);
			Cities2.add(MIlng44);

			CameraPosition miami44 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami44));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mihotel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI55 = "hotel"; 	// String defining POI type
			String MIlat55 = "25.7753";
			String MIlng55 = "-80.2089";
			Types.add(MI55); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat55);
			Cities2.add(MIlng55);

			CameraPosition miami55 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami55));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mimovie_theater:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI6 = "movie_theater"; 	// String defining POI type
			String MIlat6 = "25.7753";
			String MIlng6 = "-80.2089";
			Types.add(MI6); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat6);
			Cities2.add(MIlng6);

			CameraPosition miami6 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami6));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mimuseum:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI7 = "museum"; 	// String defining POI type
			String MIlat7 = "25.7753";
			String MIlng7 = "-80.2089";
			Types.add(MI7); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat7);
			Cities2.add(MIlng7);

			CameraPosition miami7 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami7));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_mifood:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI8 = "food"; 	// String defining POI type
			String MIlat8 = "25.7753";
			String MIlng8 = "-80.2089";
			Types.add(MI8); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat8);
			Cities2.add(MIlng8);

			CameraPosition miami8 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami8));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_misubway_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String MI9 = "subway_station"; 	// String defining POI type
			String MIlat9 = "25.7753";
			String MIlng9 = "-80.2089";
			Types.add(MI9); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(MIlat9);
			Cities2.add(MIlng9);

			CameraPosition miami9 = new CameraPosition.Builder()
			.target(MIAMI).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(miami9));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;





			//		case R.id.menu_neworleans:
			//			CameraPosition neworleans = new CameraPosition.Builder()
			//			.target(NEW_ORLEANS).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(neworleans));			
			//			break;
			
			
			
			
			
			
			// Los Angeles Places
		case R.id.menu_nyatm:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY = "atm"; 	// String defining POI type
			String NYlat = "40.7463956";
			String NYlng = "-73.9852992";
			Types.add(NY); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat);
			Cities2.add(NYlng);

			CameraPosition newyork = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nyairport:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY0 = "airport"; 	// String defining POI type
			String NYlat0 = "40.7463956";
			String NYlng0 = "-73.9852992";
			Types.add(NY0); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat0);
			Cities2.add(NYlng0);

			CameraPosition newyork0 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork0));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nybank:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY1 = "bank"; 	// String defining POI type
			String NYlat1 = "40.7463956";
			String NYlng1 = "-73.9852992";
			Types.add(NY1); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat1);
			Cities2.add(NYlng1);

			CameraPosition newyork1 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork1));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nybar:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY2 = "bar"; 	// String defining POI type
			String NYlat2 = "40.7463956";
			String NYlng2 = "-73.9852992";
			Types.add(NY2); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat2);
			Cities2.add(NYlng2);

			CameraPosition newyork2 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork2));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nybus_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY3 = "bus_station"; 	// String defining POI type
			String NYlat3 = "40.7463956";
			String NYlng3 = "-73.9852992";
			Types.add(NY3); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat3);
			Cities2.add(NYlng3);

			CameraPosition newyork3 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork3));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nycafe:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY4 = "cafe"; 	// String defining POI type
			String NYlat4 = "40.7463956";
			String NYlng4 = "-73.9852992";
			Types.add(NY4); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat4);
			Cities2.add(NYlng4);

			CameraPosition newyork4 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork4));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nyhospital:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY5 = "hospital"; 	// String defining POI type
			String NYlat5 = "40.7463956";
			String NYlng5 = "-73.9852992";
			Types.add(NY5); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat5);
			Cities2.add(NYlng5);

			CameraPosition newyork5 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork5));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nyhostel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY44 = "hostel"; 	// String defining POI type
			String NYlat44 = "40.7463956";
			String NYlng44 = "-73.9852992";
			Types.add(NY44); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat44);
			Cities2.add(NYlng44);

			CameraPosition newyork44 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork44));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nyhotel:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY55 = "hotel"; 	// String defining POI type
			String NYlat55 = "40.7463956";
			String NYlng55 = "-73.9852992";
			Types.add(NY55); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat55);
			Cities2.add(NYlng55);

			CameraPosition newyork55 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork55));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nymovie_theater:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY6 = "movie_theater"; 	// String defining POI type
			String NYlat6 = "40.7463956";
			String NYlng6 = "-73.9852992";
			Types.add(NY6); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat6);
			Cities2.add(NYlng6);

			CameraPosition newyork6 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork6));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nymuseum:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY7 = "museum"; 	// String defining POI type
			String NYlat7 = "40.7463956";
			String NYlng7 = "-73.9852992";
			Types.add(NY7); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat7);
			Cities2.add(NYlng7);

			CameraPosition newyork7 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork7));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nyfood:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY8 = "food"; 	// String defining POI type
			String NYlat8 = "40.7463956";
			String NYlng8 = "-73.9852992";
			Types.add(NY8); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat8);
			Cities2.add(NYlng8);

			CameraPosition newyork8 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork8));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
		case R.id.menu_nysubway_station:
			Map.clear(); 	// Clears map from any already made markers
			Types.clear();	 // Clears/ resets array list for type of POI searched 
			Cities.clear();
			Cities2.clear();
			String NY9 = "subway_station"; 	// String defining POI type
			String NYlat9 = "40.7463956";
			String NYlng9 = "-73.9852992";
			Types.add(NY9); 	// Adding the made string of POI to the 'Types' arraylist
			Cities.add(NYlat9);
			Cities2.add(NYlng9);

			CameraPosition newyork9 = new CameraPosition.Builder()
			.target(NEW_YORK).zoom(10).bearing(5).tilt(15).build();
			Map.animateCamera(
					CameraUpdateFactory.newCameraPosition(newyork9));

			new googleplaces().execute(); 
			new foursquareplaces().execute();
			break;
			
			
			
			
			//		case R.id.menu_orlando:
			//			CameraPosition orlando = new CameraPosition.Builder()
			//			.target(ORLANDO).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(orlando));			
			//			break;
			//		case R.id.menu_seattle:
			//			CameraPosition seattle = new CameraPosition.Builder()
			//			.target(SEATTLE).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(seattle));			
			//			break;
			//		case R.id.menu_sandiego:
			//			CameraPosition sandiego = new CameraPosition.Builder()
			//			.target(SAN_DIEGO).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(sandiego));			
			//			break;
			//		case R.id.menu_sanfrancisco:
			//			CameraPosition sanfrancisco = new CameraPosition.Builder()
			//			.target(SAN_FRANCISCO).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(sanfrancisco));			
			//			break;
			//		case R.id.menu_washington:
			//			CameraPosition washington = new CameraPosition.Builder()
			//			.target(WASHINGTON).zoom(14).bearing(5).tilt(15).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(washington));			
			//			break;




			// Weather Menu
		case R.id.menu_showweather2: 	// New activity with weather API
			startActivity(weatherPage);
			break;




			// Map Tools Option Menu
		case R.id.menu_showtraffic:
			Map.setTrafficEnabled(true);
			break;




			// Map Help Option Menu
		case R.id.menu_about:
			AboutPopupWindow();
			break;
		case R.id.menu_howto:
			HowToPopupWindow();
			break;






			//		case R.id.menu_zoomin:
			//			Map.animateCamera(CameraUpdateFactory.zoomIn());
			//			break;
			//		case R.id.menu_zoomout:
			//			Map.animateCamera(CameraUpdateFactory.zoomOut());
			//			break;


			// Example 'Show Current Location'
			//		case R.id.menu_showcurrentlocation:
			//			Location myLocation = Map.getMyLocation();
			//			LatLng myLatLng = new LatLng(myLocation.getLatitude(),
			//					myLocation.getLongitude());
			//
			//			CameraPosition myPosition = new CameraPosition.Builder()
			//			.target(myLatLng).zoom(17).bearing(90).tilt(30).build();
			//			Map.animateCamera(
			//					CameraUpdateFactory.newCameraPosition(myPosition));
			//
			//			double Testlat2 = myLocation.getLatitude();
			//			double Testlon2 = myLocation.getLongitude();
			//			String Testlat3 = String.valueOf(Testlat2) + "," + String.valueOf(Testlon2);
			//			Toast.makeText(Map.this,Testlat3, Toast.LENGTH_LONG).show();
			//			break;


			// Example 'Go To Pre-defined Location
			//		case R.id.menu_gotolocation:
			//			CameraPosition cameraPosition = new CameraPosition.Builder()
			//			.target(GOLDEN_GATE_BRIDGE) // Sets the center of the map to
			//			// Golden Gate Bridge
			//			.zoom(17)                   // Sets the zoom
			//			.bearing(90) // Sets the orientation of the camera to east
			//			.tilt(30)    // Sets the tilt of the camera to 30 degrees
			//			.build();    // Creates a CameraPosition from the builder
			//			Map.animateCamera(CameraUpdateFactory.newCameraPosition(
			//					cameraPosition));
			//			break;
			//			// Example 'Adding a Marker to the map'
			//		case R.id.menu_addmarker:
			//
			//			// ---using the default marker---
			//			//			Map.addMarker(new MarkerOptions() 
			//			//			.position(GOLDEN_GATE_BRIDGE)
			//			//			.title("Golden Gate Bridge") .snippet("San Francisco")
			//			//			.icon(BitmapDescriptorFactory
			//			//					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
			//
			//			// Creates new marker, then places on map, with a defined position, title, snippet(description) & icon
			//			Map.addMarker(new MarkerOptions()
			//			.position(GOLDEN_GATE_BRIDGE)
			//			.title("Golden Gate Bridge")
			//			.snippet("San Francisco")
			//			.icon(BitmapDescriptorFactory
			//					.fromResource(R.drawable.ic_launcher)));
			//			break;
			//			// Example 'Create a Line between locations'
			//		case R.id.menu_lineconnecttwopoints:
			//			// Add a marker at Apple location
			//			Map.addMarker(new MarkerOptions()
			//			.position(APPLE)
			//			.title("Apple")
			//			.snippet("Cupertino")
			//			.icon(BitmapDescriptorFactory.defaultMarker(
			//					BitmapDescriptorFactory.HUE_AZURE)));
			//
			//			// Draw a line connecting Apple and Golden Gate Bridge positions
			//			Map.addPolyline(new PolylineOptions()
			//			.add(GOLDEN_GATE_BRIDGE, APPLE).width(5).color(Color.RED));
			//			break;


		}
		return true;
	}

	private PopupWindow pwindo; 	// New instant of a popup window
	private PopupWindow pwindo2;

	private void AboutPopupWindow() { 
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) Map.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		//
			View layout = inflater.inflate(R.layout.popup_layout,
					(ViewGroup) findViewById(R.id.popup_element));		//
			String pp = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this);	//Retrieves a information set from google
			pwindo = new PopupWindow(layout, 400, 700, true);	// creates and sets the valuse of the popup window
			pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);	// sets the location
			pwindo.setOutsideTouchable(true);
			pwindo.setTouchable(true);

			// create button to be able to close the popup
			btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
			btnClosePopup.setOnClickListener(close_bnt_CL);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click Lisstener to close popup
	private OnClickListener close_bnt_CL = new OnClickListener() {
		public void onClick(View v) {
			pwindo.dismiss();
		}
	};
	
	private void HowToPopupWindow() { 
		try {
			// We need to get the instance of the LayoutInflater
			LayoutInflater inflater = (LayoutInflater) Map.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View layout = inflater.inflate(R.layout.popup_layout2,
					(ViewGroup) findViewById(R.id.popup_element));
			pwindo2 = new PopupWindow(layout, 500, 870, true);
			pwindo2.showAtLocation(layout, Gravity.CENTER, 0, 0);
			pwindo2.setOutsideTouchable(true);
			pwindo2.setTouchable(true);

			//
			btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
			btnClosePopup.setOnClickListener(close_bnt_CL_2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private OnClickListener close_bnt_CL_2 = new OnClickListener() {
		public void onClick(View v) {
			pwindo2.dismiss();
		}
	};



	// <-------- Google Places API -------->
	private class googleplaces extends AsyncTask<View, Void, String> {

		String temp; 	// Create temp String

		Location myLoc2 = Map.getMyLocation();  	// Creates a location variable to retrieve Lat and Lng
		double Testlat2 = myLoc2.getLatitude(); 	// Creates double with current Lat value 
		double Testlon2 = myLoc2.getLongitude(); 	// Creates double with current Lng value
		String Testlat3 = String.valueOf(Testlat2); 	// Creates String from Lat double
		String Testlon3 = String.valueOf(Testlon2); 	// Creates String from Lng double

		@Override
		protected String doInBackground(View... urls) {

			String T2 = Types.get(0); 	// Creates String with value of Types arraylist
			String C = Cities.get(0);
			String C2 = Cities2.get(0);
			System.out.println("Types Array: "+Types); 	// Prints the values in the arraylist in the console 
			System.out.println("Types 2: "+T2); 	// // Prints the values in the String in the console

			// Make Call to the Google API url
			temp = makeCall("https://maps.googleapis.com/maps/api/place/search/" +
					"json?location="+C+","+C2+
					"&radius=1500&types="+ T2 +"&sensor=true&key=" + GOOGLE_KEY);

			//print the call in the console
			//System.out.println("https://maps.googleapis.com/maps/api/place/search/json?location=" + Testlat3 + "," + Testlon3 + "&radius=100&sensor=true&key=" + GOOGLE_KEY);
			System.out.println("First Call: "+temp);
			return "";
		}

		@Override
		protected void onPreExecute() {
			// we can start a progress bar here
			//			progressBar = (ProgressBar)findViewById(R.id.progressbar);
			//	        progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(String result) {
			if (temp == null) {
				// we have an error to the call
				// we can also stop the progress bar
			} else {
				// all things went right
				// parse Google places search result
				venuesList = (ArrayList<GooglePlaces>) parseGoogleParse(temp);

				// for each value in the arraylist create...
				for (int i = 0; i < venuesList.size(); i++) {
					// make a list of the venus that are loaded in the list.
					// show the name, the category and the city

					// retrieving stored resutls within arraylist
					double lat = venuesList.get(i).getLatitude();
					double lon = venuesList.get(i).getLongitude();

					LatLng LocPoss2 = new LatLng(lat,lon);
					String PPtitle = venuesList.get(i).getName();
					String PPsnippet = venuesList.get(i).getVicinity();
					String NOsnippet = venuesList.get(i).getOpenNow();

					// creating no marker from the results
					Map.addMarker(new MarkerOptions()
					.position(LocPoss2)
					.title(PPtitle)
					.snippet(PPsnippet)
					.snippet("Open? "+NOsnippet)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.googleicon)));

				}
			}
		}


		public String makeCall(String url) {

			// string buffers the url
			StringBuffer buffer_string = new StringBuffer(url);
			String replyString = "";

			// instanciate an HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// instanciate an HttpGet
			HttpGet httpget = new HttpGet(buffer_string.toString());

			try {
				// get the responce of the httpclient execution of the url
				org.apache.http.HttpResponse response = httpclient.execute(httpget);
				InputStream is = response.getEntity().getContent();

				// buffer input stream the result
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer baf = new ByteArrayBuffer(20);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				// the result as a string is ready for parsing
				replyString = new String(baf.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(replyString);

			// trim the whitespaces
			return replyString.trim();
		}


		private ArrayList<GooglePlaces> parseGoogleParse(final String response) {

			ArrayList<GooglePlaces> temp = new ArrayList<GooglePlaces>();
			try {

				// make an jsonObject in order to parse the response
				JSONObject jsonObject = new JSONObject(response);

				// make an jsonObject in order to parse the response
				if (jsonObject.has("results")) {

					JSONArray jsonArray = jsonObject.getJSONArray("results");

					// for each result from the response API file, store the information into an arraylist
					for (int i = 0; i < jsonArray.length(); i++) {
						GooglePlaces poi = new GooglePlaces();
						if (jsonArray.getJSONObject(i).has("name")) {


							poi.setName(jsonArray.getJSONObject(i).optString("name"));
							poi.setRating(jsonArray.getJSONObject(i).optString("rating", " "));
							poi.setVicinity(jsonArray.getJSONObject(i).optString("vicinity"));

							poi.setLatitude(((JSONArray)jsonObject.get("results")).getJSONObject(i)
									.getJSONObject("geometry").getJSONObject("location")
									.getDouble("lat"));
							poi.setLongitude(((JSONArray)jsonObject.get("results")).getJSONObject(i)
									.getJSONObject("geometry").getJSONObject("location")
									.getDouble("lng"));


							if (jsonArray.getJSONObject(i).has("opening_hours")) {
								if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").has("open_now")) {
									if (jsonArray.getJSONObject(i).getJSONObject("opening_hours").getString("open_now").equals("true")) {
										poi.setOpenNow("YES");
									} else {
										poi.setOpenNow("NO");
									}
								}
							} else {
								poi.setOpenNow("Not Known");
							}
							if (jsonArray.getJSONObject(i).has("types")) {
								JSONArray typesArray = jsonArray.getJSONObject(i).getJSONArray("types");

								for (int j = 0; j < typesArray.length(); j++) {
									poi.setCategory(typesArray.getString(j) + ", " + poi.getCategory());
								}
							}
						}
						temp.add(poi);
						// make an jsonObject in order to parse the response
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<GooglePlaces>();
			}
			return temp;
		}
	}


	// <-------- FourSquare API -------->
	private class foursquareplaces extends AsyncTask<View, Void, String> {

		String temp2; 	// Create temp String
		//String temp2;

		Location myLoc2 = Map.getMyLocation();  	// Creates a location variable to retrieve Lat and Lng
		double Testlat2 = myLoc2.getLatitude(); 	// Creates double with current Lat value 
		double Testlon2 = myLoc2.getLongitude(); 	// Creates double with current Lng value
		String Testlat3 = String.valueOf(Testlat2); 	// Creates String from Lat double
		String Testlon3 = String.valueOf(Testlon2); 	// Creates String from Lng double

		@Override
		protected String doInBackground(View... urls) {

			String T2 = Types.get(0); 	// Creates String with value of Types arraylist
			String C = Cities.get(0);
			String C2 = Cities2.get(0);
			System.out.println("Types Array: "+Types); 	// Prints the values in the arraylist in the console 
			System.out.println("Types 2: "+T2); 	// // Prints the values in the String in the console

			// Make Call to the Google API url
			temp2 =  makeCall("https://api.foursquare.com/v2/venues/search?"+
					"client_id="+FOURSQUARE_CLIENTID+"&client_secret="+FOURSQUARE_CLIENTSECRET+"&ll="+
					C+","+C2+"&query="+ T2 +"&v=20140806");


			//print the call in the console
			//System.out.println("https://maps.googleapis.com/maps/api/place/search/json?location=" + Testlat3 + "," + Testlon3 + "&radius=100&sensor=true&key=" + GOOGLE_KEY);
			System.out.println("First Call: "+temp2);
			//System.out.println("Second Call: "+temp2);
			return "";
		}

		@Override
		protected void onPreExecute() {
			// we can start a progress bar here
			//			progressBar = (ProgressBar)findViewById(R.id.progressbar);
			//	        progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(String result) {
			if (temp2 == null) {
				// we have an error to the call
				// we can also stop the progress bar
			} else {
				// all things went right

				// parse Google places search result
				venuesList2 = (ArrayList<GooglePlaces>) parseGoogleParse(temp2);

				//List<String> listTitle2 = new ArrayList<String>();

				for (int i = 0; i < venuesList2.size(); i++) {
					// make a list of the venus that are loaded in the list.
					// show the name, the category and the city
					//listTitle2.add(i, venuesList2.get(i).getName() + "\nOpen Now: " + venuesList.get(i).getOpenNow() + "\n(" + venuesList.get(i).getCategory() + ")" + "\n Lat: " + venuesList.get(i).getLatitude() + "\nLon: " + venuesList.get(i).getLongitude());

					double lat2 = venuesList2.get(i).getLatitudeFS();
					double lon2 = venuesList2.get(i).getLongitudeFS();

					LatLng LocPoss3 = new LatLng(lat2,lon2);
					String FStitle = venuesList2.get(i).getNameFS();
					String FSsnippet = venuesList2.get(i).getCity();
					String FSsnippet2 = venuesList2.get(i).getCategoryFS();;


					Map.addMarker(new MarkerOptions()
					.position(LocPoss3)
					.title(FStitle)
					.snippet("Category? "+FSsnippet2)
					.icon(BitmapDescriptorFactory
							.fromResource(R.drawable.fsicon2)));

				}
			}
		}


		public String makeCall(String url) {

			// string buffers the url
			StringBuffer buffer_string = new StringBuffer(url);
			String replyString = "";

			// instanciate an HttpClient
			HttpClient httpclient = new DefaultHttpClient();
			// instanciate an HttpGet
			HttpGet httpget = new HttpGet(buffer_string.toString());

			try {
				// get the responce of the httpclient execution of the url
				org.apache.http.HttpResponse response = httpclient.execute(httpget);
				InputStream is = response.getEntity().getContent();

				// buffer input stream the result
				BufferedInputStream bis = new BufferedInputStream(is);
				ByteArrayBuffer baf = new ByteArrayBuffer(20);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				// the result as a string is ready for parsing
				replyString = new String(baf.toByteArray());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(replyString);

			// trim the whitespaces
			return replyString.trim();
		}


		private ArrayList<GooglePlaces> parseGoogleParse(final String response) {

			ArrayList<GooglePlaces> temp2 = new ArrayList<GooglePlaces>();
			//ArrayList<GooglePlaces> temp2 = new ArrayList<GooglePlaces>();
			try {

				// make an jsonObject in order to parse the response
				JSONObject jsonObject2 = new JSONObject(response);
				//				JSONObject jsonObject2 = new JSONObject(response);

				// make an jsonObject in order to parse the response
				if (jsonObject2.has("response")) {
					if (jsonObject2.getJSONObject("response").has("venues")) {

						JSONArray jsonArray2 = jsonObject2.getJSONObject("response").getJSONArray("venues");

						for (int i = 0; i < jsonArray2.length(); i++) {
							GooglePlaces poi2 = new GooglePlaces();
							if (jsonArray2.getJSONObject(i).has("name")) {
								poi2.setNameFS(jsonArray2.getJSONObject(i).getString("name"));

								if (jsonArray2.getJSONObject(i).has("location")) {
									if (jsonArray2.getJSONObject(i).getJSONObject("location").has("address")) {
										if (jsonArray2.getJSONObject(i).getJSONObject("location").has("city")) {
											poi2.setCity(jsonArray2.getJSONObject(i).getJSONObject("location").getString("city"));
										}

										if (jsonArray2.getJSONObject(i).has("location")) {
											if (jsonArray2.getJSONObject(i).getJSONObject("location").has("address")) {
												if (jsonArray2.getJSONObject(i).getJSONObject("location").has("lat")) {
													poi2.setLatitudeFS(jsonArray2.getJSONObject(i).getJSONObject("location").getDouble("lat"));
												}

												if (jsonArray2.getJSONObject(i).has("location")) {
													if (jsonArray2.getJSONObject(i).getJSONObject("location").has("address")) {
														if (jsonArray2.getJSONObject(i).getJSONObject("location").has("lng")) {
															poi2.setLongitudeFS(jsonArray2.getJSONObject(i).getJSONObject("location").getDouble("lng"));
														}

														if (jsonArray2.getJSONObject(i).has("categories")) {
															if (jsonArray2.getJSONObject(i).getJSONArray("categories").length() > 0) {
																if (jsonArray2.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
																	poi2.setCategoryFS(jsonArray2.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("name"));
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
							temp2.add(poi2);
							// make an jsonObject in order to parse the response
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<GooglePlaces>();
			}
			return temp2;
		}
	}
}

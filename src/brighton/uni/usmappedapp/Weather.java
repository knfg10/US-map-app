package brighton.uni.usmappedapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import uni.brighton.gpstracker.MyLocationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;



public class Weather extends Activity {

	TextView WeatherResponse;
	TextView WeatherConnected;
	ImageView WeathIcon;
	GoogleMap Map2;
	LocationManager mlocManager;
	LocationListener mlocListener;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_layout);

		// create button to return user to map
		Button bntBack = (Button) findViewById(R.id.btn_back);
		bntBack.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				Intent intent = new Intent(v.getContext(), Map.class);
				startActivityForResult(intent, 0);
			}
		});


		mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		android.location.Location lastLoc = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		//		android.location.Location lastLoc2 = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// find current location
		double Testlat = lastLoc.getLatitude();
		double Testlon = lastLoc.getLongitude();

		String TestLat = String.valueOf(Testlat);
		String TestLon = String.valueOf(Testlon);

		if (TestLat.length() > 5)
			TestLat = TestLat.substring(0, 5);
		if (TestLon.length() > 5)
			TestLon = TestLon.substring(0, 5);


		// get reference to the views
		WeatherResponse = (TextView) findViewById(R.id.WeatherResponse);
		WeatherConnected = (TextView) findViewById(R.id.WeatherConnected);
		WeathIcon = (ImageView) findViewById(R.id.weatherIcon);

		// check if you are connected or not
		if(isConnected()){
			WeatherConnected.setBackgroundColor(0xFF00CC00);
			WeatherConnected.setText("You are Connected");
			//        		Toast.makeText(Weather.this,"You are Connected ", Toast.LENGTH_LONG).show();
		}
		else{
			WeatherConnected.setText("You are NOT Connected ");
			//            Toast.makeText(Weather.this,"You are NOT Connected ", Toast.LENGTH_LONG).show();
		}

		// call AsynTask to perform network operation on separate thread
		new HttpAsyncTask().execute("http://api.weatherunlocked.com/api/current/"+TestLat+","+TestLon+"?app_id=29709a90&app_key=1815396e32dcfdaa3d4435b4e1765d14");
	}


	public static String GET(String url){
		InputStream inputStream = null;
		String result = "";
		try {

			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();

			// convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public boolean isConnected(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Weather.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) 
			return true;
		else
			return false;   
	}
	
	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			//Toast.makeText(getBaseContext(), "Weather Received!", Toast.LENGTH_LONG).show();
			JSONObject json;
			try {
				// Brings in JSON response from API call
				json = new JSONObject(result); // convert String to JSONObject

				String CuWeather = "";	// String that holds the results from the weather api 
				String WeathIc = "";	// String to identify which weather icon to present

				// retrieve the code for the icon to be presented then change it into a integer 
				WeathIc += json.getString("wx_code");
				int WeathCode = Integer.parseInt(WeathIc);    

				// Pulls the stored information from the selected tab in the JSON then stores it into a string
				CuWeather += "Latitude = "+json.getString("lat");
				CuWeather += "\nLongitude: "+json.getString("lon");
				CuWeather += "\n--------\n"; // New Line
				CuWeather += "Weather: "+json.getString("wx_desc");
				CuWeather += "\nIcon Code: "+json.getString("wx_code");
				CuWeather += "\nIcon: "+json.getString("wx_icon");
				CuWeather += "\n--------\n";
				CuWeather += "Temp C: "+json.getString("temp_c")+" °C";
				CuWeather += "\nTemp F: "+json.getString("temp_f")+" °F";
				CuWeather += "\n--------\n";
				CuWeather += "Feels Like C: "+json.getString("feelslike_c")+" °C";
				CuWeather += "\nFeels Like F: "+json.getString("feelslike_f")+" °F";


				System.out.println("T1 "+WeathIc); // test outcome

				// Defining which code will go with which icon 
				if (WeathCode == 0)
				{
					WeathIcon.setImageResource(R.drawable.sunny);	//  Draw icon if if statement is correct
				}
				else if (WeathCode == 1)
				{
					WeathIcon.setImageResource(R.drawable.partlycloudyday);
				}
				else if (WeathCode == 2)
				{
					WeathIcon.setImageResource(R.drawable.cloudy);
				}
				else if (WeathCode == 3)
				{
					WeathIcon.setImageResource(R.drawable.overcast);
				}
				else if (WeathCode == 10)
				{
					WeathIcon.setImageResource(R.drawable.mist);
				}
				else if (WeathCode == 21)
				{
					WeathIcon.setImageResource(R.drawable.isorainswrsday);
				}
				else if (WeathCode == 22)
				{
					WeathIcon.setImageResource(R.drawable.isosnowswrsday);
				}
				else if (WeathCode == 23)
				{
					WeathIcon.setImageResource(R.drawable.isosleetswrsday);
				}
				else if (WeathCode == 24)
				{
					WeathIcon.setImageResource(R.drawable.freezingdrizzle);
				}
				else if (WeathCode == 29)
				{
					WeathIcon.setImageResource(R.drawable.cloudrainthunder);
				}
				else if (WeathCode == 38)
				{
					WeathIcon.setImageResource(R.drawable.heavysnow);
				}
				else if (WeathCode == 39)
				{
					WeathIcon.setImageResource(R.drawable.blizzard);
				}
				else if (WeathCode == 45)
				{
					WeathIcon.setImageResource(R.drawable.fog);
				}
				else if (WeathCode == 49)
				{
					WeathIcon.setImageResource(R.drawable.freezingfog);
				}
				else if (WeathCode == 50)
				{
					WeathIcon.setImageResource(R.drawable.isorainswrsday);
				}
				else if (WeathCode == 51)
				{
					WeathIcon.setImageResource(R.drawable.isorainswrsday);
				}
				else if (WeathCode == 56)
				{
					WeathIcon.setImageResource(R.drawable.freezingdrizzle);
				}
				else if (WeathCode == 57)
				{
					WeathIcon.setImageResource(R.drawable.freezingdrizzle);
				}
				else if (WeathCode == 60)
				{
					WeathIcon.setImageResource(R.drawable.occlightrain);
				}
				else if (WeathCode == 61)
				{
					WeathIcon.setImageResource(R.drawable.modrain);
				}
				else if (WeathCode == 62)
				{
					WeathIcon.setImageResource(R.drawable.modrain);
				}
				else if (WeathCode == 63)
				{
					WeathIcon.setImageResource(R.drawable.modrain);
				}
				else if (WeathCode == 64)
				{
					WeathIcon.setImageResource(R.drawable.heavyrainswrsday);
				}
				else if (WeathCode == 65)
				{
					WeathIcon.setImageResource(R.drawable.heavyrain);
				}
				else if (WeathCode == 66)
				{
					WeathIcon.setImageResource(R.drawable.freezingdrizzle);
				}
				else if (WeathCode == 67)
				{
					WeathIcon.setImageResource(R.drawable.freezingdrizzle);
				}
				else if (WeathCode == 68)
				{
					WeathIcon.setImageResource(R.drawable.isosleetswrsday);
				}
				else if (WeathCode == 69)
				{
					WeathIcon.setImageResource(R.drawable.heavysleetswrsday);
				}
				else if (WeathCode == 70)
				{
					WeathIcon.setImageResource(R.drawable.isosnowswrsday);
				}
				else if (WeathCode == 71)
				{
					WeathIcon.setImageResource(R.drawable.isosnowswrsday);
				}
				else if (WeathCode == 72)
				{
					WeathIcon.setImageResource(R.drawable.modsnowswrsday);
				}
				else if (WeathCode == 73)
				{
					WeathIcon.setImageResource(R.drawable.modsnow);
				}
				else if (WeathCode == 74)
				{
					WeathIcon.setImageResource(R.drawable.heavysnowswrsday);
				}
				else if (WeathCode == 75)
				{
					WeathIcon.setImageResource(R.drawable.heavysnow);
				}
				else if (WeathCode == 79)
				{
					WeathIcon.setImageResource(R.drawable.heavysnow);
				}
				else if (WeathCode == 80)
				{
					WeathIcon.setImageResource(R.drawable.isorainswrsday);
				}
				else if (WeathCode == 81)
				{
					WeathIcon.setImageResource(R.drawable.heavyrainswrsday);
				}
				else if (WeathCode == 82)
				{
					WeathIcon.setImageResource(R.drawable.isorainswrsday);
				}
				else if (WeathCode == 83)
				{
					WeathIcon.setImageResource(R.drawable.isosleetswrsday);
				}
				else if (WeathCode == 84)
				{
					WeathIcon.setImageResource(R.drawable.modsleetswrsday);
				}
				else if (WeathCode == 85)
				{
					WeathIcon.setImageResource(R.drawable.modsnowswrsday);
				}
				else if (WeathCode == 86)
				{
					WeathIcon.setImageResource(R.drawable.heavysnow);
				}
				else if (WeathCode == 87)
				{
					WeathIcon.setImageResource(R.drawable.freezingrain);
				}
				else if (WeathCode == 88)
				{
					WeathIcon.setImageResource(R.drawable.freezingrain);
				}
				else if (WeathCode == 91)
				{
					WeathIcon.setImageResource(R.drawable.partcloudrainthunderday);
				}
				else if (WeathCode == 92)
				{
					WeathIcon.setImageResource(R.drawable.partcloudrainthunderday);
				}
				else if (WeathCode == 93)
				{
					WeathIcon.setImageResource(R.drawable.partcloudsleetsnowthunderday);
				}
				else if (WeathCode == 94)
				{
					WeathIcon.setImageResource(R.drawable.cloudrainthunder);
				}

				// Sets the text to display the results
				WeatherResponse.setText(CuWeather);



			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}



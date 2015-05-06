package brighton.uni.usmappedapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.location.places.Place;

public class FourSquareAPIService {

	private String API_KEY;
	private String poiType2;
	private String name;
	private String category;
	private String rating;
	private String open;
	private String vicinity;
	private double latitude;
	private double longitude;

	public FourSquareAPIService() {
		this.name = "";
		this.rating = "";
		this.open = "";
		this.vicinity = "";
		this.poiType2 = "";
		this.API_KEY = ""; 
		this.setCategory("");
	}

	
	public FourSquareAPIService(String apikey) {
		this.API_KEY = apikey;
	}

	public void setApiKey(String apikey) {
		this.API_KEY = apikey;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public void setPOIType(String poiType2) {
		this.poiType2 = poiType2;
	}

	public String getPOIType() {
		return poiType2;
	}
	
	public String getCategory() {
		return category;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getRating() {
		return rating;
	}

	public void setOpenNow(String open) {
		this.open = open;
	}

	public String getOpenNow() {
		return open;
	}


}

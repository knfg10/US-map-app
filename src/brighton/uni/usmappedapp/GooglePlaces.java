package brighton.uni.usmappedapp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.places.Place;

public class GooglePlaces {

	private String poiType2;
	private String name;
	private String category;
	private String rating;
	private String open;
	private String vicinity;
	private double latitude;
	private double longitude;

	
	private String nameFS;
	private String city;
	private String categoryFS;
	private double latitudeFS;
	private double longitudeFS;

	public GooglePlaces() {
		this.name = "";
		this.rating = "";
		this.open = "";
		this.vicinity = "";
		this.poiType2 = "aa";
		this.setCategory("");
		
		this.nameFS = "";
		this.city = "";
		this.setCategoryFS("");
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
	
	public Double getLatitudeFS() {
		return latitudeFS;
	}

	public void setLatitudeFS(Double latitudeFS) {
		this.latitudeFS = latitudeFS;
	}

	public Double getLongitudeFS() {
		return longitudeFS;
	}

	public void setLongitudeFS(Double longitudeFS) {
		this.longitudeFS = longitudeFS;
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

	public String getCity() {
		if (city.length() > 0) {
			return city;
		}
		return city;
	}

	public void setCity(String city) {
		if (city != null) {
			this.city = city.replaceAll("\\(", "").replaceAll("\\)", "");
			;
		}
	}

	public void setNameFS(String nameFS) {
		this.nameFS = nameFS;
	}

	public String getNameFS() {
		return nameFS;
	}

	public String getCategoryFS() {
		return categoryFS;
	}

	public void setCategoryFS(String categoryFS) {
		this.categoryFS = categoryFS;
	}
	
}

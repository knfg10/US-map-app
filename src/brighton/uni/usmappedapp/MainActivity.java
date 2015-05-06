package brighton.uni.usmappedapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.Place;



public class MainActivity extends Activity {
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button bnt = (Button) findViewById(R.id.button1);
		bnt.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
				
					// runs the Map activity
					Intent intent = new Intent(v.getContext(), Map.class);
					startActivityForResult(intent, 0);
				}
		});

	}
		
}



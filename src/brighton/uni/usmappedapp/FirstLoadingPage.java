package brighton.uni.usmappedapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class FirstLoadingPage extends Activity {

	// waiting time 
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    // Called when the activity is first created
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.loading_page);

        // New Handler to start the Menu-Activity 
        //  and close this Splash-Screen after some seconds
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Creates an intent that will start the Menu-Activity
                Intent mainIntent = new Intent(FirstLoadingPage.this,MainActivity.class);
                FirstLoadingPage.this.startActivity(mainIntent);
                FirstLoadingPage.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    }
		
	






package com.newjourney.android;

import com.newjourney.android.animation.ObjectAnimationSample;
import com.newjourney.android.view.ScrollViewSample;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent intent = new Intent(this, ScrollViewSample.class);
		intent = new Intent(this, ObjectAnimationSample.class);
		startActivity(intent);
		this.finish();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

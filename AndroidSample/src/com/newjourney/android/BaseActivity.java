package com.newjourney.android;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class BaseActivity extends FragmentActivity {

	protected String tag = getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(tag, "onCreate");
		setTitle(getIntent().getStringExtra("title"));
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(tag, "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(tag, "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(tag, "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.v(tag, "onStop");
	};

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.v(tag, "onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.v(tag, "onDestroy");
	};

}

package com.newjourney.android.asynctask;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.newjourney.android.BaseActivity;
import com.newjourney.android.R;
import com.newjourney.android.media.AudioCaptureActivity;

public class AsyncTaskAActivitity extends BaseActivity {

	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asynctask_a);
		imageView = (ImageView) findViewById(R.id.imageView1);
		new LoadingThread().start();
	}

	public void nextActivity(View view) {
		Intent intent = new Intent(this, AudioCaptureActivity.class);
		startActivity(intent);
		this.finish();
	}

	class LoadingThread extends Thread {
		@Override
		public void run() {
			Log.d(tag, "Begin...");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Log.d(tag, "End...");
			handler.sendEmptyMessage(1);
			handler.sendEmptyMessage(1);
			handler.sendEmptyMessage(1);
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		handler.removeMessages(1);
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.d(tag, "handleMessage...");
			imageView.setImageResource(R.drawable.logo6);
			ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, 0, 500).start();
			Toast.makeText(AsyncTaskAActivitity.this, "Toast ...", Toast.LENGTH_SHORT).show();
//			AlertDialog alert = new AlertDialog.Builder(AsyncTaskAActivitity.this).setMessage("Dialog ...").create();
//			alert.show();
		};
	};

}

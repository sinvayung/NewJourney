package com.newjourney.android.media;

import java.io.IOException;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.newjourney.android.BaseActivity;

public class AudioCaptureActivity extends BaseActivity implements SensorEventListener {
	private static final String LOG_TAG = "AudioCaptureActivity";
	private static String mFileName = null;

	private RecordButton mRecordButton = null;
	private MediaRecorder mRecorder = null;

	private PlayButton mPlayButton = null;
	private MediaPlayer mPlayer = null;
	private AudioManager am;

	private FrameLayout dialog;

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	class RecordButton extends Button {
		boolean mStartRecording = true;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onRecord(mStartRecording);
				if (mStartRecording) {
					setText("Stop recording");
				} else {
					setText("Start recording");
				}
				mStartRecording = !mStartRecording;
			}
		};

		public RecordButton(Context ctx) {
			super(ctx);
			setText("Start recording");
			setOnClickListener(clicker);
		}
	}

	class PlayButton extends Button {
		boolean mStartPlaying = true;

		OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("Stop playing");
				} else {
					setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;
			}
		};

		public PlayButton(Context ctx) {
			super(ctx);
			setText("Start playing");
			setOnClickListener(clicker);
		}
	}

	public AudioCaptureActivity() {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mFileName += "/audiorecordtest.3gp";
	}

	private SensorManager sm;
	private Sensor proximity;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		LinearLayout ll = new LinearLayout(this);
		mRecordButton = new RecordButton(this);
		ll.addView(mRecordButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		mPlayButton = new PlayButton(this);
		ll.addView(mPlayButton, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 0));

		FrameLayout frame = new FrameLayout(this);
		frame.addView(ll, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL));
		dialog = new FrameLayout(this);
		dialog.setBackgroundColor(Color.GRAY);
		dialog.setVisibility(View.INVISIBLE);
		frame.addView(dialog, new FrameLayout.LayoutParams(300, 300, Gravity.CENTER));
		setContentView(frame);

		this.sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		this.proximity = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
		this.am = (AudioManager) getSystemService(AUDIO_SERVICE);
		
		am.setMode(AudioManager.MODE_IN_CALL);
		am.setSpeakerphoneOn(false);
		am.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
		setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

		mRecordButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				int x = (int) event.getX();
				int y = (int) event.getY();
				Log.d(tag, "action = " + action + ", x = " + event.getX() + ", y = " + event.getY());
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					dialog.setVisibility(View.VISIBLE);
					startRecording();
					rect = findBoundary(mRecordButton, dialog);
					// Rect rect2 = new Rect();
					// mRecordButton.getGlobalVisibleRect(rect2);
					// Rect rect = new Rect();
					// dialog.getGlobalVisibleRect(rect, new Point(rect2.left +
					// (int) event.getX(), rect2.top + (int) event.getY()));
					Log.d(tag, rect.left + ", " + rect.top + ", " + rect.right + ", " + rect.bottom);
					if(thread != null) {
						thread.cancl();
					}
					thread = new XThread();
					thread.start();
					break;
				case MotionEvent.ACTION_MOVE:
					if (y < rect.bottom && y > rect.top && x > rect.left && x < rect.right) {
						if (dialog.getVisibility() == View.VISIBLE) {
							dialog.setVisibility(View.INVISIBLE);
						}
					} else {
						if (dialog.getVisibility() == View.INVISIBLE) {
							dialog.setVisibility(View.VISIBLE);
						}
					}
					break;
				case MotionEvent.ACTION_UP:
					dialog.setVisibility(View.INVISIBLE);
					stopRecording();
					if(thread != null) {
						thread.cancl();
					}
					break;
				}
				return true;
			}
		});

	}
	
	XThread thread;
	
	class XThread extends Thread {
		private boolean running = true;;
		
		@Override
		public void run() {
			while(running) {
				if(mRecorder == null) {
					break;
				}
				int ratio = mRecorder.getMaxAmplitude() / 500;
				int db = ratio > 0 ? (int)(20 * Math.log10(ratio)) : 0;
				Log.d(tag, "db = " + db);
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}
		
		public void cancl() {
			running = false;
		}
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 一定要在这里注册
//		sm.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL);
	};

	Rect rect;

	/**
	 * 边界检测
	 * 
	 * @param src
	 * @param dst
	 */
	public static Rect findBoundary(View src, View dst) {
		Rect srcRect = new Rect();
		src.getGlobalVisibleRect(srcRect);
		Rect dstRect = new Rect();
		dst.getGlobalVisibleRect(dstRect);
		Rect rect = new Rect();
		rect.top = dstRect.top - srcRect.top;
		rect.bottom = dstRect.bottom - srcRect.top;
		rect.left = dstRect.left - srcRect.left;
		rect.right = dstRect.right - srcRect.left;
		return rect;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Log.v(tag,
				"onSensorChanged, accuracy = " + event.accuracy + ", value = " + event.values[0] + ", maximumRange = " + proximity.getMaximumRange());
		if (event.values[0] == proximity.getMaximumRange()) {
			am.setMode(AudioManager.MODE_NORMAL);
			am.setSpeakerphoneOn(true);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			Log.d(tag, "正常模式");
		} else {
			am.setMode(AudioManager.MODE_IN_CALL);
			am.setSpeakerphoneOn(false);
			am.setRouting(AudioManager.MODE_NORMAL, AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_ALL);
			setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
			if(mPlayer != null) {				
				//mPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
			}
			Log.d(tag, "听筒模式");
			
			
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		Log.v(tag, "onAccuracyChanged, accuracy = " + accuracy);
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}

		// 一定要在这里解注册
		sm.unregisterListener(this, proximity);

	}
}
package com.newjourney.android.animation;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.newjourney.android.BaseActivity;
import com.newjourney.android.R;

public class AnimActivity extends BaseActivity {
	
	ImageView logo1;
	ImageView logo2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anim);
		
		logo1 = (ImageView) findViewById(R.id.logo1);
		logo2 = (ImageView) findViewById(R.id.logo2);
		
	}
	
	public void change(View view) {
		logo1.clearAnimation();
		logo2.clearAnimation();
		float height =  logo1.getMeasuredHeight();
		ObjectAnimator anim = ObjectAnimator.ofFloat(logo1, View.Y, 0, height);
		anim.setDuration(500);
		anim.setInterpolator(new AnticipateInterpolator());
		anim.start();
		showLogo2();
	}
	
	private void showLogo2() {
//		logo2.setVisibility(View.VISIBLE);
		float height =  logo2.getMeasuredHeight();
		logo2.setY(-height);
		ObjectAnimator anim = ObjectAnimator.ofFloat(logo2, View.Y, -height, 0);
		anim.setDuration(500);
		anim.setInterpolator(new AnticipateInterpolator());
		anim.start();
	}
	
	public void logo1Click(View view) {
		Toast.makeText(this, "LOGO 1", Toast.LENGTH_SHORT).show();
	}
	
	public void logo2Click(View view) {
		Toast.makeText(this, "LOGO 2", Toast.LENGTH_SHORT).show();
	}

}

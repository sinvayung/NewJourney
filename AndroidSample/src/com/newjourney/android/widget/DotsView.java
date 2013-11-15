package com.newjourney.android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.newjourney.android.R;

/**
 * 分栏、分页界面所用到的点点点指示
 * 
 * @author Stan Yung
 * 
 */
public class DotsView extends View {

	private static final String tag = "DotView";

	private Paint mPaint;
	private float dotRadius = 12.0f;	//6.0f;
	private float dotSpace = 30.0f;
	private int dotCount = 3;
	private int currDot = 0; // 选中的dot
	private int colorNormal, colorSelected;

	public DotsView(Context context) {
		super(context);
		init(context);
	}

	public DotsView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public DotsView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);

	}

	private void init(Context context) {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		colorNormal = Color.parseColor("#333333");
		colorSelected = Color.parseColor("#52aff7");
	}

	public void setDotRadius(float dotRadius) {
		this.dotRadius = dotRadius;
		invalidate();
	}

	public void setDotSpace(float dotSpace) {
		this.dotSpace = dotSpace;
		invalidate();
	}

	public void setDotCount(int dotCount) {
		this.dotCount = dotCount;
		invalidate();
	}

	/**
	 * 选中哪一个点
	 * 
	 * @param position
	 */
	public void selectDot(int position) {
		currDot = position;
		invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int width = getMeasuredWidth();
		int height = getMeasuredHeight();
		float left = (width - (dotRadius * 2 * dotCount + dotSpace * (dotCount - 1))) / 2;
		float top = (height - dotRadius * 2) / 2;
		canvas.save();
		for (int i = 0; i < dotCount; i++) {
			if (i == currDot) {
				mPaint.setColor(colorSelected);
			} else {
				mPaint.setColor(colorNormal);
			}
			//圆点
//			canvas.drawCircle(left, top, dotRadius, mPaint);
			//方形
//			RectF rect = new RectF(left, top, left + dotRadius, top + dotRadius);
//			canvas.drawRect(rect, mPaint);
			//图片
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rating_favorite);
			BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
			Rect src = new Rect(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());
			RectF dst = new RectF(left, top, left + bd.getIntrinsicWidth(), top + bd.getIntrinsicHeight());
			canvas.drawBitmap(bitmap, src, dst, mPaint);
			//或者：
//			canvas.drawBitmap(bitmap, left, top, null);
			
			left += (dotRadius * 2 + dotSpace);
		}
		canvas.restore();
	}

	private int measureWidth(int widthMeasureSpec) {
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		int result = 100;
		if (specMode == MeasureSpec.AT_MOST) {
			Log.d(tag, "measureWidth->AT_MOST");
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			Log.d(tag, "measureWidth->EXACTLY");
			result = specSize;
		}
		return result;
	}

	private int measureHeight(int heightMeasureSpec) {
		int specMode = MeasureSpec.getMode(heightMeasureSpec);
		int specSize = MeasureSpec.getSize(heightMeasureSpec);
		int result = 50;
		if (specMode == MeasureSpec.AT_MOST) {
			Log.d(tag, "measureHeight->AT_MOST");
			result = specSize;
		} else if (specMode == MeasureSpec.EXACTLY) {
			Log.d(tag, "measureHeight->EXACTLY");
			result = specSize;
		}
		return result;
	}

}
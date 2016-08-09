package com.kong.parallax;

import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

public class ParallaxListView extends ListView{

	public ParallaxListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public ParallaxListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ParallaxListView(Context context) {
		super(context);
	}
	private int maxHeight;
	private ImageView imageView;
	private int orignalHeight;
	public void setParallaxImageView( final ImageView imageView){
		this.imageView = imageView;
		
		imageView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				
				orignalHeight = imageView.getHeight();
				Log.e("tag", "orignalHeight: "+orignalHeight);
				int drawableHeight = imageView.getDrawable().getIntrinsicHeight();//图片的高度
				maxHeight = orignalHeight>drawableHeight?
						orignalHeight*2:drawableHeight;
			}
		});
		
	}
	
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX,
			int scrollY, int scrollRangeX, int scrollRangeY,
			int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
		
		if(deltaY<0 && isTouchEvent){
			if(imageView!=null){
				int newHeight = imageView.getHeight()-deltaY/3;
				if(newHeight>maxHeight)newHeight = maxHeight;
				
				imageView.getLayoutParams().height = newHeight;
				imageView.requestLayout();
			}
		}
		
		return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX,
				scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(ev.getAction()==MotionEvent.ACTION_UP){
			ValueAnimator animator = ValueAnimator.ofInt(imageView.getHeight(),orignalHeight);
			animator.addUpdateListener(new AnimatorUpdateListener() {
				@Override
				public void onAnimationUpdate(ValueAnimator animator) {
					int animatedValue = (Integer) animator.getAnimatedValue();
					
					imageView.getLayoutParams().height = animatedValue;
					imageView.requestLayout();
				}
			});
			animator.setInterpolator(new OvershootInterpolator(5));
			animator.setDuration(350);
			animator.start();
		}
		return super.onTouchEvent(ev);
	}
}

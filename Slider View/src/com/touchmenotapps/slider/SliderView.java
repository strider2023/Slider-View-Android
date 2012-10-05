package com.touchmenotapps.slider;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 
 * @author Arindam Nath
 *
 */
public class SliderView extends RelativeLayout {
	
	private final int PRIMARY_CONTAINER_ID = 98325;
	private final int BACKGROUND_CONTAINER_ID = 23498;
	
	private int SLIDE_DIRECTION = Slider.SLIDE_TOP_TO_BOTTOM;
	private int SLIDE_ANIM_SPEED = 500;
	private int SLIDE_POPOUT_MARGIN = 50;
	private int SHADOW_WIDTH = 5;
	
	private RelativeLayout mBackgroundContainer, mPrimaryContainer;
	private View mShadowLayout;
	
	private boolean isSliding = false, isSliderOpen = false; 
	private float originalLayoutTop = 0.0f, originalLayoutLeft = 0.0f;
	private ObjectAnimator openAnimation, closeAnimation;
	private DisplayMetrics metrics;
	
	/**
	 * 
	 * @param context
	 */
	public SliderView(Context context) {
		super(context);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		SLIDE_POPOUT_MARGIN = (int) (40 * metrics.density);
		initSliderView(context);
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 */
	public SliderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		initSliderView(context);
	}
	
	/**
	 * 
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public SliderView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		initSliderView(context);
	}
	
	/**
	 * 
	 * @param direction
	 */
	public void setSliderDirection(int direction) {
		SLIDE_DIRECTION = direction;
	}
	
	/**
	 * 
	 * @param animSpeed
	 */
	public void setAnimationSpeed(int animSpeed) {
		SLIDE_ANIM_SPEED = animSpeed;
	}
	
	/**
	 * 
	 * @param mFragmentTransaction
	 * @param foregroundFragment
	 */
	public void setPrimaryFragment(FragmentTransaction mFragmentTransaction, Fragment foregroundFragment) {
		mFragmentTransaction.replace(PRIMARY_CONTAINER_ID, foregroundFragment);
	}
	
	/**
	 * 
	 * @param mFragmentTransaction
	 * @param backgroundFragment
	 */
	public void setBackgroundFragment(FragmentTransaction mFragmentTransaction, Fragment backgroundFragment) {
		mFragmentTransaction.replace(BACKGROUND_CONTAINER_ID, backgroundFragment);
	}
	
	/**
	 * 
	 * @param foregroundView
	 * @param marginLeft
	 * @param marginTop
	 * @param marginRight
	 * @param margingBottom
	 */
	public void setPrimaryLayout(View foregroundView, int marginLeft, int marginTop, int marginRight,  int margingBottom, int shadowWidth) {
		mPrimaryContainer.removeAllViews();
		SHADOW_WIDTH = shadowWidth;
		LayoutParams shadowLayoutParams;
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
				
		switch (SLIDE_DIRECTION) {
		case Slider.SLIDE_TOP_TO_BOTTOM:
			layoutParams.setMargins(marginLeft, 0, marginRight, margingBottom);
			foregroundView.setLayoutParams(layoutParams);
			shadowLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, SHADOW_WIDTH);
			shadowLayoutParams.setMargins(marginLeft, marginTop, marginRight, 0);
			shadowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			mShadowLayout.setLayoutParams(shadowLayoutParams);
			break;
		case Slider.SLIDE_BOTTOM_TO_TOP:
			layoutParams.setMargins(marginLeft, marginTop, marginRight, 0);
			foregroundView.setLayoutParams(layoutParams);
			shadowLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, SHADOW_WIDTH);
			shadowLayoutParams.setMargins(marginLeft, 0, marginRight, margingBottom);
			shadowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			mShadowLayout.setLayoutParams(shadowLayoutParams);
			break;
		case Slider.SLIDE_LEFT_TO_RIGHT:
			layoutParams.setMargins(0, marginTop, marginRight, margingBottom);
			foregroundView.setLayoutParams(layoutParams);
			shadowLayoutParams = new LayoutParams(SHADOW_WIDTH, LayoutParams.FILL_PARENT);
			shadowLayoutParams.setMargins(marginLeft, marginTop, 0, margingBottom);
			shadowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			mShadowLayout.setLayoutParams(shadowLayoutParams);
			break;
		case Slider.SLIDE_RIGHT_TO_LEFT:
			layoutParams.setMargins(marginLeft, marginTop, 0, margingBottom);
			foregroundView.setLayoutParams(layoutParams);
			shadowLayoutParams = new LayoutParams(SHADOW_WIDTH, LayoutParams.FILL_PARENT);
			shadowLayoutParams.setMargins(0, marginTop, marginRight, margingBottom);
			shadowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			mShadowLayout.setLayoutParams(shadowLayoutParams);
			break;
		default:
			layoutParams.setMargins(marginLeft, 0, marginRight, margingBottom);
			foregroundView.setLayoutParams(layoutParams);
			shadowLayoutParams = new LayoutParams(LayoutParams.FILL_PARENT, SHADOW_WIDTH);
			shadowLayoutParams.setMargins(marginLeft, marginTop, marginRight, 0);
			shadowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			mShadowLayout.setLayoutParams(shadowLayoutParams);
			break;
		}
		mPrimaryContainer.addView(foregroundView);
		mPrimaryContainer.addView(mShadowLayout);
	}
	
	/**
	 * 
	 */
	public void removePrimaryLayout() {
		mPrimaryContainer.removeAllViews();
	}
		
	/**
	 * 
	 * @param resID
	 */
	public void setShadow(int resID) {
		mShadowLayout.setBackgroundResource(resID);
		mShadowLayout.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 
	 * @param backgroundView
	 * @param marginLeft
	 * @param marginTop
	 * @param marginRight
	 * @param margingBottom
	 */
	public void setBackgroundLayout(View backgroundView, int marginLeft, int marginTop, int marginRight,  int margingBottom) {
		mBackgroundContainer.removeAllViews();
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		switch (SLIDE_DIRECTION) {
		case Slider.SLIDE_TOP_TO_BOTTOM:
			if(mBackgroundContainer.getHeight() >= this.getHeight()) {
				layoutParams.setMargins(marginLeft, marginTop, marginRight, margingBottom + SLIDE_POPOUT_MARGIN);
				backgroundView.setLayoutParams(layoutParams);
				mBackgroundContainer.addView(backgroundView);
			}
			break;
		case Slider.SLIDE_BOTTOM_TO_TOP:
			if(mBackgroundContainer.getHeight() >= this.getHeight()) {
				layoutParams.setMargins(marginLeft, marginTop + SLIDE_POPOUT_MARGIN, marginRight, margingBottom);
				backgroundView.setLayoutParams(layoutParams);
				mBackgroundContainer.addView(backgroundView);
			}
			break;
		case Slider.SLIDE_LEFT_TO_RIGHT:
			if(mBackgroundContainer.getWidth() >= this.getWidth()) {
				layoutParams.setMargins(marginLeft, marginTop, marginRight + SLIDE_POPOUT_MARGIN, margingBottom);
				backgroundView.setLayoutParams(layoutParams);
				mBackgroundContainer.addView(backgroundView);
			}
			break;
		case Slider.SLIDE_RIGHT_TO_LEFT:
			if(mBackgroundContainer.getWidth() >= this.getWidth()) {
				layoutParams.setMargins(marginLeft + SLIDE_POPOUT_MARGIN, marginTop, marginRight, margingBottom);
				backgroundView.setLayoutParams(layoutParams);
				mBackgroundContainer.addView(backgroundView);
			}
			break;
		default:
			if(mBackgroundContainer.getHeight() >= this.getHeight()) {
				layoutParams.setMargins(marginLeft, marginTop, marginRight, margingBottom + SLIDE_POPOUT_MARGIN);
				backgroundView.setLayoutParams(layoutParams);
				mBackgroundContainer.addView(backgroundView);
			}
			break;
		}
	}
	
	/**
	 * 
	 */
	public void removeBackgroundLayout() {
		mBackgroundContainer.removeAllViews();
	}
	
	/**
	 * 
	 * @param context
	 */
	private void initSliderView(Context context) {		
		/** Init the primary container layout. **/
		mPrimaryContainer = new RelativeLayout(context);
		mPrimaryContainer.setId(PRIMARY_CONTAINER_ID);
		
		/** Init the background container layout. **/
		mBackgroundContainer = new RelativeLayout(context);
		mBackgroundContainer.setId(BACKGROUND_CONTAINER_ID);
		mBackgroundContainer.setVisibility(View.GONE);
		
		/** Init the shadow layout **/
		mShadowLayout = new View(context);
		mShadowLayout.setVisibility(View.GONE);
		
		/** Draw the background layer first next the primary layer **/
		this.addView(mBackgroundContainer);
		this.addView(mPrimaryContainer);
	}
			
	/**
	 * @param pixels 
	 */
	public void setPrimaryLayoutVisibilityFactor(int pixels) {
		SLIDE_POPOUT_MARGIN = (int) (pixels * metrics.density);
	}
	

	/**
	 * @return the isSliding
	 */
	public boolean isSliding() {
		return isSliding;
	}

	/**
	 * @return the isSliderOpen
	 */
	public boolean isSliderOpen() {
		return isSliderOpen;
	}

	/**
	 * 
	 */
	public void openBackgroundPanel() {
		if(!isSliderOpen && !isSliding) {
			originalLayoutTop = mPrimaryContainer.getY();
			originalLayoutLeft = mPrimaryContainer.getX();
			//Check for the slider direction and set the animation likewise
			switch (SLIDE_DIRECTION) {
			case Slider.SLIDE_TOP_TO_BOTTOM:
				openAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationY", mPrimaryContainer.getY(), this.getHeight() - SLIDE_POPOUT_MARGIN);
				break;
			case Slider.SLIDE_BOTTOM_TO_TOP:
				openAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationY", mPrimaryContainer.getY(), - (this.getHeight() - SLIDE_POPOUT_MARGIN));
				break;
			case Slider.SLIDE_LEFT_TO_RIGHT:
				openAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationX", mPrimaryContainer.getX(), this.getWidth() - SLIDE_POPOUT_MARGIN);
				break;
			case Slider.SLIDE_RIGHT_TO_LEFT:
				openAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationX", mPrimaryContainer.getX(), -(this.getWidth() - SLIDE_POPOUT_MARGIN));
				break;
			default:
				openAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationY", mPrimaryContainer.getY(), this.getHeight() - SLIDE_POPOUT_MARGIN);
				break;
			}
			openAnimation.setDuration(SLIDE_ANIM_SPEED);
			openAnimation.start();
			mBackgroundContainer.setVisibility(LinearLayout.VISIBLE);
			
			openAnimation.addListener(new AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
					isSliding = true;
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) { }
				
				@Override
				public void onAnimationEnd(Animator animation) {
					//This fix is for a bug. I don't get why it is animating on first click!
					if(SLIDE_DIRECTION == Slider.SLIDE_TOP_TO_BOTTOM || SLIDE_DIRECTION == Slider.SLIDE_BOTTOM_TO_TOP) {
						if(originalLayoutTop != mPrimaryContainer.getY()) {
							isSliding = false;
							isSliderOpen = true;
						}
					} else if(SLIDE_DIRECTION == Slider.SLIDE_LEFT_TO_RIGHT || SLIDE_DIRECTION == Slider.SLIDE_RIGHT_TO_LEFT) {
						if(originalLayoutLeft != mPrimaryContainer.getX()) {
							isSliding = false;
							isSliderOpen = true;
						}
					}
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					isSliding = false;
				}
			});
		}
	}
	
	/**
	 * 
	 */
	public void closeBackgroundPanel() {
		if(isSliderOpen && !isSliding) {
			//Check for the slider direction and set the animation likewise
			switch (SLIDE_DIRECTION) {
			case Slider.SLIDE_TOP_TO_BOTTOM:
				closeAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationY", mPrimaryContainer.getY(), originalLayoutTop);
				break;
			case Slider.SLIDE_BOTTOM_TO_TOP:
				closeAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationY", mPrimaryContainer.getY(), originalLayoutTop);
				break;
			case Slider.SLIDE_LEFT_TO_RIGHT:
				closeAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationX", mPrimaryContainer.getX(), originalLayoutLeft);
				break;
			case Slider.SLIDE_RIGHT_TO_LEFT:
				closeAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationX", mPrimaryContainer.getX(), originalLayoutLeft);
				break;
			default:
				closeAnimation = ObjectAnimator.ofFloat(mPrimaryContainer, "translationY", mPrimaryContainer.getY(), originalLayoutTop);
				break;
			}
			closeAnimation.setDuration(SLIDE_ANIM_SPEED);
			closeAnimation.start();
			closeAnimation.addListener(new AnimatorListener() {
				@Override
				public void onAnimationStart(Animator animation) {
					isSliding = true;
				}
				
				@Override
				public void onAnimationRepeat(Animator animation) { }
				
				@Override
				public void onAnimationEnd(Animator animation) {
					mBackgroundContainer.setVisibility(LinearLayout.GONE);
					isSliding = false;
					isSliderOpen = false;
				}
				
				@Override
				public void onAnimationCancel(Animator animation) {
					isSliding = true;
				}
			});
		}
	}
}

package com.touchmenotapps.slider;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private SliderView mSliderLayout;
	private Button mCloseButton, mOpenButton;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        LayoutInflater inflater = LayoutInflater.from(this);
        View primaryView = inflater.inflate(R.layout.layout_list, null);
        mOpenButton = (Button) primaryView.findViewById(R.id.open_slider_btn);
        
        View backgroundView= inflater.inflate(R.layout.layout_details, null);
        mCloseButton = (Button) backgroundView.findViewById(R.id.close_layout);
        
        mSliderLayout = (SliderView) findViewById(R.id.slider_layout);
        mSliderLayout.setSliderDirection(Slider.SLIDE_RIGHT_TO_LEFT);
        mSliderLayout.setPrimaryLayout(primaryView, 5, 5, 5, 5, 0);
        mSliderLayout.setBackgroundLayout(backgroundView, 5, 5, 5, 5);
        
        mCloseButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSliderLayout.closeBackgroundPanel();
			}
		});
        
        mOpenButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!mSliderLayout.isSliderOpen())
					mSliderLayout.openBackgroundPanel();
			}
		});
    }
}
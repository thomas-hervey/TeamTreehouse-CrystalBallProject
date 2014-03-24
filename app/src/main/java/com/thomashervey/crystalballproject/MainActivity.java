package com.thomashervey.crystalballproject;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thomashervey.crystalballproject.ShakeDetector.OnShakeListener;

/**
 * 			  This class generates the main activity and handles the effects
 * 			  (animation, sounds, text display) of a crystal ball simulator
 * 			  when a user shakes the Android device. With the help of the
 * 			  CrystalBall.java and ShakeDetector.java classes, the app
 * 			  simulates a crystal ball with entertaining effects, displaying
 * 			  a random response.
 *
 * 			  This project was created while following the teamtreehouse.com
 * 			  Build A Simple Android App project
 *
 * @version   Completed Jan 21, 2014
 * @author    Thomas Hervey <thomasahervey@gmail.com>
 */
public class MainActivity extends Activity {
	
	public static final String TAG = MainActivity.class.getSimpleName();

    // crystal ball object
	private CrystalBall mCrystalBall = new CrystalBall();

	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;

    /* sensor managers */
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;

    /**
     * Animation for text answer upon user shake
     *
     * @param  none
     * @return none
     */
    private void animateAnswer() {
    	AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1);
    	fadeInAnimation.setDuration(1500);
    	fadeInAnimation.setFillAfter(true);
    	
    	mAnswerLabel.setAnimation(fadeInAnimation);
    }

    /**
     * Animation for crystal ball upon user chake
     *
     * @param  none
     * @return none
     */
    private void animateCrystalBall() {
    	mCrystalBallImage.setImageResource(R.drawable.ball_animation);
    	AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
    	if (ballAnimation.isRunning()) {
    		ballAnimation.stop();
    	}
    	ballAnimation.start();
    }

    /**
     * Handler for new answer and animations upon user shake
     *
     * @param  none
     * @return none
     */
    private void handleNewAnswer() {
		String answer = mCrystalBall.getAnAnswer();
		
		// Update the label with our dynamic answer
		mAnswerLabel.setText(answer);
		
		animateCrystalBall();
		animateAnswer();
		playSound();
	}

    /**
     * Initial create method setting up sensor manager,
     * accelerometer, shake detector
     *
     * @param  savedInstanceState
     * @return none
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Assign the Views from the layout file
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
    	mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);

    	mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    	mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				handleNewAnswer();
			}
		});
    	
    	//Toast.makeText(this, "Yay! Our Activity was created!", Toast.LENGTH_LONG).show();
    	
    	Log.d(TAG, "We're logging from the onCreate() method!");
    }

    /**
     * Menu inflater
     *
     * @param  menu
     * @return boolean
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    /**
     * Remove shake detector listener on pause
     *
     * @param  none
     * @return none
     */
    @Override
    public void onPause() {
    	super.onPause();
    	mSensorManager.unregisterListener(mShakeDetector);
    }

    /**
     * Add shake detector listener on resume
     *
     * @param  none
     * @return none
     */
    @Override
    public void onResume() {
    	super.onResume();
    	mSensorManager.registerListener(mShakeDetector, mAccelerometer, 
    			SensorManager.SENSOR_DELAY_UI);
    }

    /**
     * Play sound along with animations upon new shake action
     *
     * @param  none
     * @return none
     */
	private void playSound() {
    	MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
    	player.start();
    	player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
    }
}

package com.stoll.charles.robotcarphoneapplication;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DrivingActivity extends AppCompatActivity {

    public static final int BUTTON_CONTROL_STYLE = 0;
    public static final int TOUCH_CONTROL_STYLE = 1;

    private static final String TAG = "driving activity tag";
    private static final String DRIVING_STYLE_KEY = "drivingStyleKey";
    private static final String CAMERA_STYLE_KEY = "cameraStyleKey";

    private int mDriveStyle;
    private int mCameraControlStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        if(savedInstanceState == null){
            //this means that it was just started
            mDriveStyle = getIntent().getIntExtra(DRIVING_STYLE_KEY, 0);
            mCameraControlStyle = getIntent().getIntExtra(CAMERA_STYLE_KEY, 0);
        }

        else {
            mDriveStyle = savedInstanceState.getInt(DRIVING_STYLE_KEY);
            mCameraControlStyle = savedInstanceState.getInt(CAMERA_STYLE_KEY);
        }

        FragmentManager fm = getSupportFragmentManager();
        Fragment driveFragment = fm.findFragmentById(R.id.drive_fragment_container);
        Fragment cameraControlFragment = fm.findFragmentById(R.id.camera_control_fragment_container);

        if(driveFragment == null) {
            driveFragment = setupDriveFragment();
        }
        if(cameraControlFragment == null) {
            cameraControlFragment = setupCameraFragment();
        }
        fm.beginTransaction().add(R.id.drive_fragment_container, driveFragment).commit();
        fm.beginTransaction().add(R.id.camera_control_fragment_container, cameraControlFragment).commit();

    }

    public static Intent newIntent(Context packageContext, int drivingStyle, int cameraControlStyle){
        Intent intent = new Intent(packageContext, DrivingActivity.class);
        //needs to get a driving style so we pass it a string of the drivingStyle and controlStyle
        intent.putExtra(DRIVING_STYLE_KEY, drivingStyle);
        intent.putExtra(CAMERA_STYLE_KEY, cameraControlStyle);
        return intent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(DRIVING_STYLE_KEY, mDriveStyle);
        outState.putInt(CAMERA_STYLE_KEY, mCameraControlStyle);
    }

    private Fragment setupDriveFragment() {
        Fragment driveFragment;
        switch (mDriveStyle) {
            case BUTTON_CONTROL_STYLE:  driveFragment = new ButtonControlFragment();
                                        break;
            case TOUCH_CONTROL_STYLE:   driveFragment = new TouchControlFragment();
                                        break;
            default:                    Log.e(TAG, "mDrive Style was invalid");
                                        driveFragment = new ButtonControlFragment();
        }
        return driveFragment;
    }

    private Fragment setupCameraFragment() {
        Fragment cameraFragment;
        switch(mCameraControlStyle) {
            case BUTTON_CONTROL_STYLE:  cameraFragment = new ButtonControlFragment();
                                        break;
            case TOUCH_CONTROL_STYLE:   cameraFragment = new TouchControlFragment();
                                        break;
            default:                    Log.e(TAG, "mCameraControlStyle was invalid");
                                        cameraFragment = new ButtonControlFragment();
        }

        return cameraFragment;
    }
}

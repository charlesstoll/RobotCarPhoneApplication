package com.stoll.charles.robotcarphoneapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DrivingActivity extends AppCompatActivity
    implements TouchControlFragment.OnTouchControlFragmentInteractionListener,
        ButtonControlFragment.OnButtonControlFragmentInteractionListener{

    public static final int BUTTON_CONTROL_STYLE = 0;
    public static final int TOUCH_CONTROL_STYLE = 1;

    private static final String TAG = "driving activity tag";
    private static final String DRIVING_STYLE_KEY = "drivingStyleKey";
    private static final String CAMERA_STYLE_KEY = "cameraStyleKey";

    private int mDriveStyle;
    private int mCameraControlStyle;
    private Button mPictureButton;
    private TextView mDebugText;

    private Robot mRobotModel = new Robot(Robot.STOP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving);

        mDebugText = findViewById(R.id.debug_text);

        if(savedInstanceState == null){
            //this means that it was just started
            mDriveStyle = getIntent().getIntExtra(DRIVING_STYLE_KEY, 0);
            mCameraControlStyle = getIntent().getIntExtra(CAMERA_STYLE_KEY, 0);
        }

        else {
            mDriveStyle = savedInstanceState.getInt(DRIVING_STYLE_KEY);
            mCameraControlStyle = savedInstanceState.getInt(CAMERA_STYLE_KEY);
        }

        mPictureButton = findViewById(R.id.take_picture_button);
        mPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRobotModel.takePicture();
                syncFirebase();
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        Fragment driveFragment = fm.findFragmentById(R.id.drive_fragment_container);
        Fragment cameraControlFragment = fm.findFragmentById(R.id.camera_control_fragment_container);

        if(driveFragment == null) {
            driveFragment = setupDriveFragment();
            fm.beginTransaction().add(R.id.drive_fragment_container, driveFragment).commit();
        }
        if(cameraControlFragment == null) {
            cameraControlFragment = setupCameraFragment();
            fm.beginTransaction().add(R.id.camera_control_fragment_container, cameraControlFragment).commit();
        }
        //fm.beginTransaction().add(R.id.drive_fragment_container, driveFragment).commit();
        //fm.beginTransaction().add(R.id.camera_control_fragment_container, cameraControlFragment).commit();

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
            case BUTTON_CONTROL_STYLE:  driveFragment = ButtonControlFragment.newInstance(true).setListener(DrivingActivity.this);
                                        break;
            case TOUCH_CONTROL_STYLE:   driveFragment = TouchControlFragment.newInstance(true).setListener(DrivingActivity.this);
                                        break;
            default:                    Log.e(TAG, "mDrive Style was invalid");
                                        driveFragment = ButtonControlFragment.newInstance(true).setListener(DrivingActivity.this);
        }
        return driveFragment;
    }

    private Fragment setupCameraFragment() {
        Fragment cameraFragment;
        switch(mCameraControlStyle) {
            case BUTTON_CONTROL_STYLE:  cameraFragment = ButtonControlFragment.newInstance(false).setListener(DrivingActivity.this);
                                        break;
            case TOUCH_CONTROL_STYLE:   cameraFragment = TouchControlFragment.newInstance(false).setListener(DrivingActivity.this);
                                        break;
            default:                    Log.e(TAG, "mCameraControlStyle was invalid");
                                        cameraFragment = ButtonControlFragment.newInstance(false).setListener(DrivingActivity.this);
        }
        return cameraFragment;
    }

    @Override
    public void onButtonControlFragmentInteraction(boolean drivingInstruction, double yPos, double xPos) {
        if(drivingInstruction) {
            mRobotModel.setForward(yPos);
            mRobotModel.setTurn(xPos);
        }
        else {
            mRobotModel.setCameraX(xPos);
            mRobotModel.setCameraY(yPos);
        }
        syncFirebase();
    }

    @Override
    public void onTouchControlFragmentInteraction(boolean drivingInstruction, double yPos, double xPos){
        if(drivingInstruction) {
            mRobotModel.setForward(yPos);
            mRobotModel.setTurn(xPos);
        }
        else {
            mRobotModel.setCameraX(xPos);
            mRobotModel.setCameraY(yPos);
        }
        syncFirebase();
    }

    private void syncFirebase() {
        //sync robot to firebase
        mDebugText.setText(mRobotModel.toString());
    }
}

package com.stoll.charles.robotcarphoneapplication;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class OpeningActivity extends AppCompatActivity {

    private RadioButton mDriveByTouchRadioButton;
    private RadioButton mDriveByButtonRadioButton;
    private RadioButton mControlCameraByTouchRadioButton;
    private RadioButton mControlCameraByButtonRadioButton;
    private RadioGroup mCameraControlStyleRadioGroup;
    private RadioGroup mDriveStyleRadioGroup;

    private Button mStartDrivingButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        setupRadioButtons();
        setupStartDrivingButton();
    }

    private void setupRadioButtons() {
        mDriveByButtonRadioButton = (RadioButton) findViewById(R.id.drive_by_button_radio_button);
        mDriveByTouchRadioButton = (RadioButton) findViewById(R.id.drive_by_touch_radio_button);
        mControlCameraByButtonRadioButton = (RadioButton) findViewById(R.id.control_camera_by_button_radio_button);
        mControlCameraByTouchRadioButton = (RadioButton) findViewById(R.id.control_camera_by_touch_radio_button);
        mCameraControlStyleRadioGroup = (RadioGroup) findViewById(R.id.camera_control_style_radio_group);
        mDriveStyleRadioGroup = (RadioGroup) findViewById(R.id.drive_style_radio_group);
    }

    private void setupStartDrivingButton(){
        mStartDrivingButton = (Button) findViewById(R.id.start_driving_button);
        mStartDrivingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int driveStyle = getDriveStyle();
                int cameraControlStyle = getCameraControlStyle();

                if(driveStyle == -1) {
                    Toast.makeText(OpeningActivity.this, R.string.no_driving_style_selected_text, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cameraControlStyle == -1) {
                    Toast.makeText(OpeningActivity.this, R.string.no_camera_control_style_selected_text, Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = DrivingActivity.newIntent(OpeningActivity.this, driveStyle, cameraControlStyle);
                startActivity(intent);
            }
        });
    }

    private int getDriveStyle() {
        if(mDriveByTouchRadioButton.isChecked())
            return DrivingActivity.TOUCH_CONTROL_STYLE;
        if(mDriveByButtonRadioButton.isChecked())
            return DrivingActivity.BUTTON_CONTROL_STYLE;
        return -1;
    }

    private int getCameraControlStyle() {
        if(mControlCameraByTouchRadioButton.isChecked())
            return DrivingActivity.TOUCH_CONTROL_STYLE;
        if(mControlCameraByButtonRadioButton.isChecked())
            return DrivingActivity.BUTTON_CONTROL_STYLE;
        return -1;
    }
}

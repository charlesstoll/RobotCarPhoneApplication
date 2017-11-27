package com.stoll.charles.robotcarphoneapplication;

/**
 * Created by charl on 11/25/2017.
 */

public class Robot {

    public static final int STOP = 0;
    public static final int DIGITAL_DRIVE = 1;
    public static final int ANALOG_DRIVE = 2;
    public static final int DRIVE_BY_PICTURE = 3;
    public static final int DRIVE_BY_COLOR = 4;

    private double mCameraY;
    private double mCameraX;
    private double mForward;
    private double mTurn;

    private int mDrivingMode;

    private boolean mTakePicture;

    public Robot(int drivingMode) {
        mDrivingMode = drivingMode;
        mCameraX = 0;
        mCameraY = 0;
        mForward = 0;
        mTurn = 0;
        mTakePicture = false;
    }

    public double getCameraY() {
        return mCameraY;
    }

    public void setCameraY(double cameraY) {
        mCameraY = cameraY;
    }

    public double getCameraX() {
        return mCameraX;
    }

    public void setCameraX(double cameraX) {
        mCameraX = cameraX;
    }

    public double getForward() {
        return mForward;
    }

    public void setForward(double forward) {
        mForward = forward;
    }

    public double getTurn() {
        return mTurn;
    }

    public void setTurn(double turn) {
        mTurn = turn;
    }

    public int getDrivingMode() {
        return mDrivingMode;
    }

    public void setDrivingMode(int drivingMode) {
        mDrivingMode = drivingMode;
    }

    public void takePicture() {
        mTakePicture = !mTakePicture; //toggle in order to show the user wants a picture taken
    }

    @Override
    public String toString() {
        return  "CameraY=" + mCameraY +
                ", CameraX=" + mCameraX +
                ", Forward=" + mForward +
                ", Turn=" + mTurn +
                ", DrivingMode=" + mDrivingMode +
                ", Picture=" + mTakePicture +
                '}';
    }
}

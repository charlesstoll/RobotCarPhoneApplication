package com.stoll.charles.robotcarphoneapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TouchControlFragment.OnTouchControlFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TouchControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TouchControlFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "touch control fgt";
    private static final String DRIVER_CONTROL_KEY = "driverControlKey";


    private GestureDetectorCompat mDetector;

    // TODO: Rename and change types of parameters
    private boolean mDriverControl;

    private OnTouchControlFragmentInteractionListener mListener;

    public TouchControlFragment() {
        // Required empty public constructor
    }


    public static TouchControlFragment newInstance(boolean driverControl) {
        TouchControlFragment fragment = new TouchControlFragment();
        Bundle args = new Bundle();
        args.putBoolean(DRIVER_CONTROL_KEY, driverControl);
        fragment.setArguments(args);
        return fragment;
    }

    public TouchControlFragment setListener(OnTouchControlFragmentInteractionListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDriverControl = getArguments().getBoolean(DRIVER_CONTROL_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_touch_control, container, false);

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    mListener.onTouchControlFragmentInteraction(mDriverControl, 777, 777);
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_MOVE) {
                    double yLoc = event.getY();
                    double xLoc = event.getX();
                    int height = v.getHeight();
                    int width = v.getWidth();

                    //set bounds so that you cannot get values beyond the fragment
                    if(yLoc < 0) {
                        yLoc = 0;
                    }
                    if(xLoc < 0) {
                        xLoc = 0;
                    }
                    if(yLoc > height) {
                        yLoc = height;
                    }
                    if(xLoc > width) {
                        xLoc = width;
                    }

                    //calculate where the click is relative to the center
                    // pos y -> above center line
                    // pos x -> to the right of center line
                    double centerRelY = height/2 - yLoc;
                    double centerRelX = xLoc - width/2;

                    //scale to -100 to 100
                    double scaledY = centerRelY/height * 200;
                    double scaledX = centerRelX/width * 200;

                    mListener.onTouchControlFragmentInteraction(mDriverControl, Math.floor(scaledY), Math.floor(scaledX));

                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP) {
                    mListener.onTouchControlFragmentInteraction(mDriverControl, 0, 0);
                    return true;
                }
                return false;
            }
        });
        //mDetector = new GestureDetectorCompat(v.getContext(),this);
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*
        if (mListener != null) {
            mListener.onTouchControlFragmentInteraction(uri);
        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnTouchControlFragmentInteractionListener) {
            mListener = (OnTouchControlFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnTouchControlFragmentInteractionListener {
        // TODO: Update argument type and name
        void onTouchControlFragmentInteraction(boolean drivingInstruction, double yPos, double xPos);
    }


}

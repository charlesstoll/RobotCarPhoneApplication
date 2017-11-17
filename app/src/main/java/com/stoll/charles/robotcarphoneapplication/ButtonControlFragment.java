package com.stoll.charles.robotcarphoneapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ButtonControlFragment.OnButtonControlFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ButtonControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ButtonControlFragment extends Fragment {
    private static final String TAG = "button control frg";
    private static final String DRIVER_CONTROL_KEY = "driverControlKey";

    private boolean mDriverControl;

    private OnButtonControlFragmentInteractionListener mListener;

    public ButtonControlFragment() {
        // Required empty public constructor
    }

    public static ButtonControlFragment newInstance(Boolean driverControl) {
        ButtonControlFragment fragment = new ButtonControlFragment();
        Bundle args = new Bundle();
        args.putBoolean(DRIVER_CONTROL_KEY, driverControl);
        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_button_control, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        Log.d(TAG, "onButtonPressed from button control fragment called");
        /*
        if (mListener != null) {
            mListener.onButtonControlFragmentInteraction(uri);
        }
        */
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Log.d(TAG, "onAttach from button control fragment called");
        /*
        if (context instanceof OnButtonControlFragmentInteractionListener) {
            mListener = (OnButtonControlFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
    }

    @Override
    public void onDetach() {

        Log.d(TAG, "onDetach from button control fragment called");
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
    public interface OnButtonControlFragmentInteractionListener {
        // TODO: Update argument type and name
        void onButtonControlFragmentInteraction(Uri uri);
    }
}

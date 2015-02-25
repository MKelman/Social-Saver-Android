package edu.gatech.bobsbuilders.socialsaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseUser;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Friends.OnFriendsSelectedListener} interface
 * to handle interaction events.
 * Use the {@link Friends#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Deals extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    OnDealsSelectedListener mCallback;
    AlertDialog alertDialog = null;
    Button addFriends, viewFriends;
    List<ParseUser> ob = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Friends.
     */
    // TODO: Rename and change types and number of parameters
    public static Friends newInstance(String param1, String param2) {
        Friends fragment = new Friends();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Class Constrcutor
     *
     */
    public Deals() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_deals, container,false);

        Button currentDeals = (Button) rootView.findViewById(R.id.currentdealsb);
        Button wantedDeals = (Button) rootView.findViewById(R.id.wanteddealsb);

        currentDeals.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CurrentDealsMoreInfo.class);
                startActivity(intent);
            }
        });

        wantedDeals.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WantedDealsMoreInfo.class);
                startActivity(intent);
            }
        });

        return rootView;
        //return inflater.inflate(R.layout.fragment_friends, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mCallback != null) {
            //mCallback.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnDealsSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDealsSelectedListener {
        // public void onArticleSelected(int position);
        //public void onFragmentInteraction(Uri uri);
    }
    /*
    public interface OnFriendsInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
    */
}
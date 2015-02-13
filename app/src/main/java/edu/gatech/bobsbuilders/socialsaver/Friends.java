package edu.gatech.bobsbuilders.socialsaver;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.parse.ParseObject;
import com.parse.ParseQuery;
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

public class Friends extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    OnFriendsSelectedListener mCallback;
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
     * Class Constrcuctor
     *
     */
    public Friends() {
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

        final View rootView = inflater.inflate(R.layout.fragment_friends, container,false);
        viewFriends = (Button)rootView.findViewById(R.id.ViewFriendsB);
        addFriends = (Button)rootView.findViewById(R.id.addfriendb);

        viewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FriendsMoreInfo.class);
                startActivity(intent);
            }
        });

        addFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setTitle("Add a friend \n");

                final EditText userName = new EditText(getActivity());
                userName.setHint("Friend's Name");//optional
                final EditText userEmail = new EditText(getActivity());
                userEmail.setHint("Friend's Email");//optional

                //in my example i use TYPE_CLASS_NUMBER for input only numbers

                userName.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                userEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                LinearLayout lay = new LinearLayout(getActivity());
                lay.setOrientation(LinearLayout.VERTICAL);
                lay.addView(userName);
                lay.addView(userEmail);
                lay.setPadding(5,5,5,5);
                alertDialogBuilder.setView(lay);


                alertDialogBuilder.setPositiveButton("Add Friend", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        ParseQuery<ParseUser> query = ParseUser.getQuery();
                        //ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                        String username = userEmail.getText().toString().toLowerCase();
                        String name = userName.getText().toString().toLowerCase();
                        query.whereEqualTo("username", userEmail.getText().toString().toLowerCase());
                        query.whereEqualTo("name", userName.getText().toString().toLowerCase());
                        try {
                            ob = query.find();
                            if(ob.size()>0){
                                //get object id if i want it
                                for (ParseObject userList : ob) {
                                    //String a = userList.get("objectId").toString();
                                    String friendEmail = userList.get("username").toString();

                                    ParseObject Friends = new ParseObject("Friends");

                                    ParseUser user = ParseUser.getCurrentUser();
                                    Friends.put("friendOne", ParseUser.getCurrentUser().getUsername());
                                    Friends.put("friendTwo", friendEmail);
                                    Friends.saveInBackground();
                                    Toast.makeText(getActivity(), "Friend Added!", Toast.LENGTH_LONG).show();

                                }
                            } else {
                                Toast.makeText(getActivity(), "User not found", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Sorry, there was an issue adding your friend. Please try again later.", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }


                        alertDialog.dismiss();
                    }
                });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
                // create alert dialog
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

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
            mCallback = (OnFriendsSelectedListener) activity;
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
    public interface OnFriendsSelectedListener {
        // public void onArticleSelected(int position);
        //public void onFragmentInteraction(Uri uri);
    }
    /*
    public interface OnFriendsInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    */
}

package edu.gatech.bobsbuilders.socialsaver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeScreen.OnHomeScreenSelectedListener} interface
 * to handle interaction events.
 * Use the {@link HomeScreen#newInstance} factory method to
 * create an instance of this fragment.
 */

public class HomeScreen extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnHomeScreenSelectedListener mCallback;

    private RadioGroup dealrb;
    EditText salesEndDateET;
    TextView salesEndDateTV;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView txtLat;
    String lat;
    String provider;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;
    private AlertDialog alertDialog;
    private EditText saleEndET;
    private ParseObject sDeal;
    private Date endDate;
    private String locationFound;
    private ParseGeoPoint point;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeScreen.
     */
    public static HomeScreen newInstance(String param1, String param2) {
        HomeScreen fragment = new HomeScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Class constructor
     *
     */
    public HomeScreen() {
        // Required empty public constructor
    }

    @SuppressWarnings("EmptyMethod")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
        */


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_home_screen, container, false);

        Button submitdeal = (Button) rootView.findViewById(R.id.SubmitDeal);
        dealrb = (RadioGroup) rootView.findViewById(R.id.dealtyperg);
        final RadioGroup locationrb = (RadioGroup) rootView.findViewById(R.id.onlineorlocalrb);


        final TextView salesEndDateTV = (TextView) rootView.findViewById(R.id.salesendtv);
        final EditText salesEndDateET = (EditText) rootView.findViewById(R.id.salesendet);
        final EditText itemet = (EditText) rootView.findViewById(R.id.itemet);
        final EditText maxPriceet = (EditText) rootView.findViewById(R.id.maxPrice);
        final TextView foundtv = (TextView) rootView.findViewById(R.id.foundtv);
        final EditText actualet = (EditText) rootView.findViewById(R.id.actuallocationet);
        final TextView actualtv = (TextView) rootView.findViewById(R.id.actuallocationtv);


        dealrb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.wantedButton:

                        salesEndDateET.setVisibility(View.INVISIBLE);
                        salesEndDateTV.setVisibility(View.INVISIBLE);
                        locationrb.setVisibility(View.INVISIBLE);
                        foundtv.setVisibility(View.INVISIBLE);
                        actualet.setVisibility(View.INVISIBLE);
                        actualtv.setVisibility(View.INVISIBLE);
                        break;

                    case R.id.FoundButton:

                        salesEndDateET.setVisibility(View.VISIBLE);
                        salesEndDateTV.setVisibility(View.VISIBLE);
                        locationrb.setVisibility(View.VISIBLE);
                        foundtv.setVisibility(View.VISIBLE);
                        actualet.setVisibility(View.VISIBLE);
                        actualtv.setVisibility(View.VISIBLE);
                        break;

                }

            }
        });

        submitdeal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                int dealType = dealrb.getCheckedRadioButtonId();
                if (dealType == R.id.wantedButton) {
                    //database is seekingDeals
                    ParseObject sDeals = new ParseObject("SeekingDeals");


                    GPSTracker gps = new GPSTracker(getActivity());

                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        point = new ParseGeoPoint(latitude, longitude);
                    } else {
                        // can't get location
                        // GPS or Network is not enabled
                        // Ask user to enable GPS/network in settings
                        //gps.showSettingsAlert();
                        point = new ParseGeoPoint(0.00, 0.00);
                    }

                    String itemPrice = maxPriceet.getText().toString();
                    if(itemPrice.equals("") || itemPrice.equals("")  || itemet.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Sorry, not all the fields have been filled in.", Toast.LENGTH_LONG).show();
                    } else {
                        Number price = Double.parseDouble(itemPrice);
                        sDeals.put("maxPrice", price);
                        sDeals.put("userLocation", point);
                        sDeals.put("userEmail", ParseUser.getCurrentUser().getUsername());
                        sDeals.put("item", itemet.getText().toString());

                        sDeals.saveInBackground();
                        Toast.makeText(getActivity(), "Wanted Deal Posted!", Toast.LENGTH_LONG).show();
                        maxPriceet.setText("");
                        itemet.setText("");
                    }


                } else {
                    // foundDeal database
                    sDeal = new ParseObject("FoundDeals");

                    int location = locationrb.getCheckedRadioButtonId();

                    if(location == R.id.onlinebutton) {
                        locationFound = "Online";
                    } else {
                        locationFound = "In-Store";
                    }

                    GPSTracker gps = new GPSTracker(getActivity());
                    //ParseGeoPoint point;
                    // check if GPS enabled
                    if(gps.canGetLocation()){
                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();
                        point = new ParseGeoPoint(latitude, longitude);
                    } else {
                        point = new ParseGeoPoint(0.00, 0.00);
                    }

                    saleEndET = (EditText) rootView.findViewById(R.id.salesendet);
                    @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");



                    try {
                        endDate = formatter.parse(saleEndET.getText().toString());
                    } catch(ParseException e) {
                        endDate = null;
                    }
                   // Calendar cal = Calendar.getInstance();
                    if(actualet.getText().toString().equals("") || locationFound.equals("") || itemet.getText().toString().equals("") ||  maxPriceet.getText().toString().equals("")) {
                        Toast.makeText(getActivity(), "Sorry, not all the fields have been filled in", Toast.LENGTH_LONG).show();
                    } else {

                        if (locationFound.equals("In-Store")) {
                            // Start an intent for the dispatch activity
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setTitle("Deals Found!");
                            alertDialogBuilder
                                    .setMessage("Would you like to add your current location as the location of the sale?")
                                    .setPositiveButton("NO, send me to Google Maps", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            alertDialog.dismiss();
                                            Intent intent = new Intent(getActivity(), MapPinPoint.class);
                                            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                            intent.putExtra("userEmail", ParseUser.getCurrentUser().getUsername());
                                            intent.putExtra("actualLocation", actualet.getText().toString());
                                            intent.putExtra("item", itemet.getText().toString());
                                            intent.putExtra("saleEndDate", saleEndET.getText().toString());
                                            intent.putExtra("foundLocation", locationFound);
                                            String itemPrice = maxPriceet.getText().toString();
                                            Number price = Double.parseDouble(itemPrice);
                                            intent.putExtra("maxPrice", price);

                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            alertDialog.dismiss();
                                            sDeal.put("userEmail", ParseUser.getCurrentUser().getUsername());
                                            sDeal.put("actualLocation", actualet.getText().toString());
                                            sDeal.put("item", itemet.getText().toString());
                                            sDeal.put("saleEndDate", endDate);
                                            sDeal.put("foundLocation", locationFound);
                                            String itemPrice = maxPriceet.getText().toString();
                                            Number price = Double.parseDouble(itemPrice);
                                            sDeal.put("maxPrice", price);
                                            sDeal.put("userLocation", point);

                                            sDeal.saveInBackground();
                                            Toast.makeText(getActivity(), "Found Deal Posted!", Toast.LENGTH_LONG).show();


                                            maxPriceet.setText("");
                                            itemet.setText("");
                                            actualet.setText("");
                                            saleEndET.setText("");
                                        }
                                    });
                            // create alert dialog
                            alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {

                            sDeal.put("userEmail", ParseUser.getCurrentUser().getUsername());
                            sDeal.put("actualLocation", actualet.getText().toString());
                            sDeal.put("item", itemet.getText().toString());
                            sDeal.put("saleEndDate", endDate);
                            sDeal.put("foundLocation", locationFound);
                            String itemPrice = maxPriceet.getText().toString();
                            Number price = Double.parseDouble(itemPrice);
                            sDeal.put("maxPrice", price);
                            sDeal.put("userLocation", point);

                            sDeal.saveInBackground();
                            Toast.makeText(getActivity(), "Found Deal Posted!", Toast.LENGTH_LONG).show();


                            maxPriceet.setText("");
                            itemet.setText("");
                            actualet.setText("");
                            saleEndET.setText("");
                        }
                    }
                }

            }

        });

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnHomeScreenSelectedListener) activity;
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
    public interface OnHomeScreenSelectedListener {
        // public void onArticleSelected(int position);
        //public void onFragmentInteraction(Uri uri);
    }

    /*
    public interface OnHomeScreenInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
    */



}

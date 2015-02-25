package edu.gatech.bobsbuilders.socialsaver;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class WantedDealsMoreInfo extends ActionBarActivity {
    private List<DealListings> dealListings = null;

    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    String objectID, email, item, userid;
    Number maxPrice;
    ParseGeoPoint point;
    ListView listview;
    WantedDealsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wanted_deals_more_info);
        new RemoteDataTask().execute();
    }



    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(WantedDealsMoreInfo.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please wait");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            dealListings = new ArrayList<DealListings>();

            ParseUser user = ParseUser.getCurrentUser();

            try {

                ParseQuery<ParseObject> foundDeals = ParseQuery.getQuery("SeekingDeals");

                userid = user.getUsername();


                try { // find the user when a student
                    ob = foundDeals.find();
                } catch (com.parse.ParseException e) {

                    e.printStackTrace();
                }

                for (ParseObject dealList : ob) {

                    item = (String) dealList.get("item");
                    maxPrice = (Number) dealList.get("maxPrice");
                    point = (ParseGeoPoint) dealList.get("userLocation");
                    email = (String) dealList.get("userEmail");

                    DealListings DL = new DealListings();

                    DL.setItem(item);
                    DL.setEmail(email);
                    DL.setObjectID((String) dealList.getObjectId());
                    DL.setMaxPrice(maxPrice);
                    DL.setGeoPoint(point);
                    dealListings.add(DL);
                }


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            TextView sorry = (TextView)findViewById(R.id.tvdisplaysorry);
            if ((ob.size()) == 0) {
                sorry.setText("You currently don't have any items you want.");
            } else {
                sorry.setVisibility(View.INVISIBLE);
            }

            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new WantedDealsAdapter(WantedDealsMoreInfo.this,dealListings);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog
            mProgressDialog.getProgress();
            mProgressDialog.dismiss();
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wanted_deals_more_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
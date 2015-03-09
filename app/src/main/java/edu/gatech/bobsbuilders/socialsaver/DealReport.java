package edu.gatech.bobsbuilders.socialsaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DealReport extends ActionBarActivity {
    private List<DealListings> dealListings = null;

    List<ParseObject> ob,ob2,ob3;
    ProgressDialog mProgressDialog;
    String objectID, email, item, found, foundLocation, userid;
    Date saleEndDate;
    Number maxPrice;
    ParseGeoPoint point;
    ListView listview;
    DealsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_report);

        new RemoteDataTask().execute();

        Button homePage = (Button)findViewById(R.id.toHomePageB);
        homePage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //go to homepage on button click
                Intent intent = new Intent(DealReport.this, DispatchActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }


    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DealReport.this);
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
                /*
                * Query again from the loginpage
                * */

                ParseQuery<ParseObject> wantedDeals = ParseQuery.getQuery("SeekingDeals");
                ParseQuery<ParseObject> foundDeals = ParseQuery.getQuery("FoundDeals");

                String userid = ParseUser.getCurrentUser().getUsername();

                wantedDeals.whereEqualTo("userEmail", userid);

                // Locate the column named "ranknum" in Parse.com and order list by ascending
                wantedDeals.orderByDescending("createdAt");
                foundDeals.orderByDescending("createdAt");

                try { // find the user when a student
                    ob = foundDeals.find();
                } catch (com.parse.ParseException e1) {
                    e1.printStackTrace();
                }

                try { // find the user when a tutor
                    ob2 = wantedDeals.find();
                } catch (com.parse.ParseException e2) {
                    e2.printStackTrace();
                }

                boolean isFound = false;
                for (ParseObject Userlist : ob2) { //seeking deals
                    String itemName = Userlist.get("item").toString().toLowerCase();
                    Number priceSeeking = (Number) (Userlist.get("maxPrice"));

                    for (ParseObject dealList : ob) { // foundDeals
                        String item = dealList.get("item").toString().toLowerCase();
                        Number priceFound = (Number) (dealList.get("maxPrice"));

                        if (itemName.equals(item)) {
                            if (priceSeeking.doubleValue() >= priceFound.doubleValue()) {

                            String userEmail = dealList.get("userEmail").toString();
                            ParseQuery<ParseObject> Friends = ParseQuery.getQuery("Friends");

                            boolean isFriends = false;

                                try { // find the user when a student
                                    ob3 = Friends.find();
                                } catch (com.parse.ParseException e) {
                                    e.printStackTrace();
                                }

                                for (ParseObject users : ob3) {

                                    if ((ParseUser.getCurrentUser().getUsername().toString().equals(users.get("friendOne").toString())
                                            && (users.get("friendTwo").toString().equals(dealList.get("userEmail").toString())))
                                            || (ParseUser.getCurrentUser().getUsername().toString().equals(users.get("friendTwo").toString())
                                            && (users.get("friendOne").toString().equals(dealList.get("userEmail").toString())))) {

                                        //check if current date is before sale end date!
                                        Date endDate = (Date) dealList.get("saleEndDate");
                                        if(!new Date().after(endDate)) {
                                            isFriends = true;
                                        }
                                   }
                                }

                            if (isFriends) {

                                item = (String) dealList.get("item");
                                saleEndDate = (Date) dealList.get("saleEndDate");
                                found = (String) dealList.get("actualLocation");
                                foundLocation = (String) dealList.get("foundLocation");
                                maxPrice = (Number) dealList.get("maxPrice");
                                point = (ParseGeoPoint) dealList.get("userLocation");
                                email = (String) dealList.get("userEmail");

                                DealListings DL = new DealListings();

                                DL.setItem(item);
                                DL.setEmail(email);
                                DL.setSaleEndDate(saleEndDate);
                                DL.setObjectID((String) dealList.getObjectId());
                                DL.setFound(found);
                                DL.setFoundLocation(foundLocation);
                                DL.setMaxPrice(maxPrice);
                                DL.setGeoPoint(point);
                                dealListings.add(DL);
                            }

                            }
                        }
                    }
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
                sorry.setText("There are currently no current deals available!");
            } else {
                sorry.setVisibility(View.INVISIBLE);
            }
            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new DealsAdapter(DealReport.this,dealListings);
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
        getMenuInflater().inflate(R.menu.menu_deal_report, menu);
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

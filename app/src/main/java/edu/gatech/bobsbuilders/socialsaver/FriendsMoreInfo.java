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

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FriendsMoreInfo extends ActionBarActivity {
    private List<ParseObject> ob;
    private List<ParseObject> ob2;
    private List<ParseUser> ob3;
    private ProgressDialog mProgressDialog;
    private List<UserListings> userlistings = null;
    private String userid;
    private String name;
    private String email;
    private String rating;
    private String totalsales;
    private ParseFile image;
    View rootView;
    private ListView listview;
    private FriendsAdapter adapter; // was listviewadapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        new RemoteDataTask().execute();

    }


    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(FriendsMoreInfo.this);
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

            userlistings = new ArrayList<>();

            ParseUser user = ParseUser.getCurrentUser();

            try {

                ParseQuery<ParseObject> asFriendOne = ParseQuery.getQuery("Friends");
                ParseQuery<ParseObject> asFriendTwo = ParseQuery.getQuery("Friends");

                userid = user.getUsername();

                asFriendOne.whereEqualTo("friendOne", userid);

                asFriendTwo.whereEqualTo("friendTwo", userid);

                // Locate the column named "ranknum" in Parse.com and order list by ascending
                asFriendOne.orderByDescending("createdAt");
                asFriendTwo.orderByDescending("createdAt");

                try { // find the user when a student
                    ob = asFriendOne.find();
                } catch (com.parse.ParseException e) {

                    e.printStackTrace();
                }

                try { // find the user when a tutor
                    ob2 = asFriendTwo.find();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                for (ParseObject Userlist : ob) {

                    /*User as Friend One*/
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", Userlist.get("friendTwo"));
                    try {
                        ob3 = query.find();
                        for (ParseObject user2 : ob3) {
                            // Locate images in pic column
                            image = (ParseFile) user2.get("userImage"); // get tutor photo
                            name = (String) user2.get("name");
                            email = (String) user2.get("username");
                            rating = user2.get("Rating") + "/5.0";
                            totalsales = (String) user2.get("reportCount");
                        }
                    } catch (com.parse.ParseException e) {
                        e.printStackTrace();
                    }

                    UserListings UL = new UserListings();

                    UL.setCurrentRate(rating);
                    UL.setEmail(email);
                    UL.setName(name);
                    UL.setObjectID(Userlist.getObjectId());
                    UL.setTotalSales(totalsales);
                    UL.setUserImage(image.getUrl());
                    userlistings.add(UL); // just one


                }

                for (ParseObject Userlist : ob2) {
                    // THIS IS ALL WHEN USER IS THE friend two
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", Userlist.get("friendOne"));
                    try {
                        ob3 = query.find();
                        for (ParseObject user2 : ob3) {
                            // Locate images in pic column
                            image = (ParseFile) user2.get("userImage"); // get tutor photo
                            name = (String) user2.get("name");
                            email = (String) user2.get("username");
                            rating = user2.get("Rating") + "/5.0";
                            totalsales = (String) user2.get("reportCount");
                        }
                    } catch (com.parse.ParseException e) {
                        e.printStackTrace();
                    }


                    UserListings UL = new UserListings();

                    UL.setCurrentRate(rating);
                    UL.setEmail(email);
                    UL.setName(name);
                    UL.setObjectID(Userlist.getObjectId());
                    UL.setTotalSales(totalsales);
                    UL.setUserImage(image.getUrl());
                    userlistings.add(UL); // just one

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
            if ((ob.size() + ob2.size()) == 0) {
                sorry.setText("You do not have any current friends. Go back to the last page and add a friend!");
            } else {
                sorry.setVisibility(View.INVISIBLE);
            }

            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.listview);
            // Pass the results into ListViewAdapter.java
            adapter = new FriendsAdapter(FriendsMoreInfo.this,userlistings);
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
        getMenuInflater().inflate(R.menu.menu_friends_more_info, menu);
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

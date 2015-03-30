package edu.gatech.bobsbuilders.socialsaver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SingleFriendView extends ActionBarActivity {
    private ProgressDialog mProgressDialog;
    private Bitmap bmImg = null;
    private String name;
    private String email;
    private String totalReport;
    private String rating;
    private String objectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_friend_view);
        // Execute loadSingleView AsyncTask
        //getActionBar().setDisplayHomeAsUpEnabled(true); //back button in action bar

        new LoadSingleView().execute();

        Button removeFriend = (Button)findViewById(R.id.removeFriend);
        removeFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseObject.createWithoutData("Friends", objectId).deleteEventually();
                Toast.makeText(SingleFriendView.this,"Friend Removed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SingleFriendView.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    public class LoadSingleView extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Create a progressdialog
            mProgressDialog = new ProgressDialog(SingleFriendView.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Please Wait");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

        }

        @Override
        protected String doInBackground(String... args) {
            try {
                // Retrieve data from ListViewAdapter on click event
                Intent i = getIntent();
                // Get the result of title

                objectId = i.getStringExtra("OBJECTID");
                name = i.getStringExtra("NAME");
                String userimage = i.getStringExtra("USERIMAGE");
                email = i.getStringExtra("EMAIL");
                rating = i.getStringExtra("CURRENTRATE");
                totalReport = i.getStringExtra("TOTALSALES");

                // Download the Image from the result URL given by userimage
                URL url = new URL(userimage);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bmImg = BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            //Boolean dismiss = false;
            /*
            if(dismiss){
                Intent i=new Intent(SingleFriendView.this,HomeScreen.class);
                startActivity(i);
            }
            */

            return null;
        }

        @Override
        protected void onPostExecute(String args) {
            // Locate the TextViews in singleitemview.xml
            TextView txtname = (TextView) findViewById(R.id.tvnameASFVLOAD);
            TextView txtemail = (TextView) findViewById(R.id.tvemailASFVLOAD);
            TextView txtreportcount=(TextView)findViewById(R.id.tvreportcountASFVLOAD);
            TextView txtrating=(TextView)findViewById(R.id.tvratingASFVLOAD);

            // Locate the ImageView in singleitemview.xml
            ImageView imguserimage = (ImageView) findViewById(R.id.personImage);

            // Set results to the TextViews
            txtname.setText(name);
            txtemail.setText(email);
            txtreportcount.setText(totalReport);
            txtrating.setText(rating);

            // Set results to the ImageView
            imguserimage.setImageBitmap(bmImg);
            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }




}

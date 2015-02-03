package edu.gatech.bobsbuilders.socialsaver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseInstallation;
import com.parse.ParseUser;


public class DispatchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            ParseUser user = ParseUser.getCurrentUser();
            String isiton = user.get("isOn").toString();
            /*
            * DOES NOT WORK FOR NOW... FIGURE OUT ISSUE AT A LATER TIME
            * */
            ParseInstallation installation = ParseInstallation.getCurrentInstallation();
            installation.put("useremail", ParseUser.getCurrentUser().getUsername());
            installation.put("isOn", isiton);
            installation.saveInBackground();


            startActivity(new Intent(this, MainActivity.class));
            finish();

        } else {
            // Start and intent for the logged out activity
            startActivity(new Intent(this, LoginPage.class));
            finish();

        }
    }

}

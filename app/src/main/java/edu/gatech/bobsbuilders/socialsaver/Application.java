package edu.gatech.bobsbuilders.socialsaver;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by Mitchell on 1/26/15.
 */
public class Application extends android.app.Application {

    /**
     * Constructor
     *
     */
    public Application() {
    }

    @Override
    public void onCreate() {
        /*DO NOT TOUCH THIS CLASS*/

        super.onCreate();
        // Initialize the Parse SDK.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "FgnEzgiFVZzNZtvCxczTe846fuGasJUrDwJc6CND", "8ovebhfbn6o8Sfswf5mdub001AFNHO6OMSc695KV"); //ONLY ONCE!!!
        // Associate the device with a user
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.saveInBackground();

        //PushService.setDefaultPushCallback(this, LoginPage.class);
        //ParseAnalytics.trackAppOpened(getIntent());
        //	ParseInstallation.getCurrentInstallation().saveInBackground();

        // Specify an Activity to handle all pushes by default.
        //PushService.setDefaultPushCallback(this, MainActivity.class);
    }
}
package edu.gatech.bobsbuilders.socialsaver;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import java.util.Locale;


public class LoginPage extends Activity {
    private EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_login_page);


        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(this, DispatchActivity.class);
            startActivity(i);
        } else {
            //stop the keyboard from automatically popping up initially!]
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            // Remove title bar- this refers to the "Activity"
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_login_page);

            username = (EditText) findViewById(R.id.emailfield);
            password = (EditText) findViewById(R.id.passfield);

            findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(isNetworkStatusAvialable (getApplicationContext())) {
                        // Set up a progress dialog
                        final ProgressDialog dlg = new ProgressDialog(LoginPage.this);
                        dlg.setTitle("Please wait.");
                        dlg.setMessage("Logging in.  Please wait.");
                        dlg.show();
                        String emailwhite = username.getText().toString().toLowerCase(Locale.getDefault());
                        String email = emailwhite.replaceAll("\\s+", "");
                        // Call the Parse login method
                        ParseUser.logInInBackground(email, password.getText()
                                .toString().toLowerCase(Locale.getDefault()), new LogInCallback() {

                            @Override
                            public void done(ParseUser user, ParseException e) {
                                dlg.dismiss();
                                if (e != null) {
                                    // Show the error message
                                    Toast.makeText(LoginPage.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    // Start an intent for the dispatch activity
                                    Intent intent = new Intent(LoginPage.this, DispatchActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }

                        }); //ends parseloginbackground

                    } else { //double email?
                        Toast.makeText(LoginPage.this, "Please check you internet connection!", Toast.LENGTH_LONG).show();
                    }

                }
            });

            findViewById(R.id.newuser).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Intent intent = new Intent(LoginPage.this, SignUpUser.class);
                    startActivity(intent);
                }
            });

            /*  FORGOT PASSWORD  */
            findViewById(R.id.forgotpassword).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if(isEmpty(username)){
                        Toast.makeText(getApplicationContext(),"Please type in your email address used to initally sign up in the email field", Toast.LENGTH_LONG).show();
                    }
                    else{

                        // Set up a progress dialog
                        final ProgressDialog dlg = new ProgressDialog(LoginPage.this);
                        dlg.setTitle("Please wait.");
                        dlg.setMessage("Resetting Password ");
                        dlg.show();
                        String emailwhite=username.getText().toString().toLowerCase(Locale.getDefault());
                        String email=emailwhite.replaceAll("\\s+", "");
                        ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback(){
                            @Override
                            public void done(ParseException e) {
                                dlg.dismiss();
                                if(e==null){
                                    Toast.makeText(getApplicationContext(),"Nice! Check your email to reset your password. (check your junk folder just in case)", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"opps, we dont have an email like that in our database. Please check the email address again", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
            /*
            * End forgot password
            * */



        }
    }

    /**
     * Checks current network status before attempting to connect with parse
     *
     * @param  Context of Activity
     */
    public static boolean isNetworkStatusAvialable (Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if(netInfos != null)
                if(netInfos.isConnected())
                    return true;
        }
        return false;
    }

    /**
     * Checks field inputted to see if field is empty
     *
     * @param  Edittext field to check
     */
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        } else {
            return true;
        }
    }
}

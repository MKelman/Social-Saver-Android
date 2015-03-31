package edu.gatech.bobsbuilders.socialsaver;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.mock.MockContext;
import android.widget.Button;
/*
* Author: Mitchell Kelman
*
* */
public class LoginPageTest extends ActivityInstrumentationTestCase2<LoginPage> {

    private LoginPage myActivity;
    private Button loginButton;


    public LoginPageTest() {
        super(LoginPage.class);
    }


    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        myActivity = getActivity();
        loginButton = (Button) myActivity.findViewById(R.id.login);


    }

    public void testPushMeButton_clickButtonAndExpectInfoText() {

        TouchUtils.clickView(this, loginButton);
        LoginPage ma = new LoginPage();
        Context context = myActivity;

        MockContext mockcontext = new MockContext();
        assertEquals(true,ma.isNetworkStatusAvialable(context));

        Exception a = new UnsupportedOperationException();
        try {
            ma.isNetworkStatusAvialable(mockcontext);
        } catch (Exception e) {
            assertEquals(e.toString(),a.toString());
        }

    }


}
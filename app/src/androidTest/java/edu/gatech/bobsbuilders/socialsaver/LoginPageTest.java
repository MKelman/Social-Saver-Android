package edu.gatech.bobsbuilders.socialsaver;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginPageTest extends ActivityInstrumentationTestCase2<LoginPage> {

    private EditText nameField, passField;
    //private LoginPage loginPage;
    private LoginPage myActivity;
    private Button loginButton;
    private TextView theText;
    private final String expectedInfoText = "Login";
    private EditText inputText;


    public LoginPageTest() {
        super(LoginPage.class);
    }


    public void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(true);

        myActivity = getActivity();
        nameField = (EditText) myActivity.findViewById(R.id.emailfield);
        loginButton = (Button) myActivity.findViewById(R.id.login);
        passField = (EditText) myActivity.findViewById(R.id.passfield);


    }

    public void testPushMeButton_clickButtonAndExpectInfoText() {

        TouchUtils.clickView(this, loginButton);
        LoginPage ma = new LoginPage();
        Context context = myActivity;
        assertEquals(true,ma.isNetworkStatusAvialable(context));
       // assertTrue(View.VISIBLE == theText.getVisibility());
       // assertEquals(expectedInfoText, theText.getText());

    }


}
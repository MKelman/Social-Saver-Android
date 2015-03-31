package edu.gatech.bobsbuilders.socialsaver;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
/*
* Author: Jake Yamaki
*
* */
public class SignUpUserTest extends ActivityInstrumentationTestCase2<SignUpUser> {
    public EditText name, email, password, passwordverify;
    public SignUpUser activity;

    public SignUpUserTest() {
        super(SignUpUser.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        setActivityInitialTouchMode(true);
        name = (EditText) activity.findViewById(R.id.namefield);
        email = (EditText) activity.findViewById(R.id.emailfield);
        password = (EditText) activity.findViewById(R.id.password);
        passwordverify = (EditText) activity.findViewById(R.id.confirmpassword);
    }

    public void testFields() throws Exception {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                assertNotNull(activity);
                assertNotNull(name);
                assertNotNull(email);
                assertNotNull(password);
                assertNotNull(passwordverify);

                name.requestFocus();
                name.setText("Abc Def");
                assertEquals(name.getText().toString(), "Abc Def");
                assertTrue(name.getText().toString().contains(" "));
                name.setText("Abcdef");
                assertFalse(name.getText().toString().contains(" "));

                email.requestFocus();
                email.setText("Abc Def");
                assertFalse(email.getText().toString().contains("@"));
                assertFalse(email.getText().toString().contains("."));
                email.setText("abc@def.com");
                assertTrue(email.getText().toString().contains("@"));
                assertTrue(email.getText().toString().contains("."));

                password.requestFocus();
                password.setText("abcdef");
                passwordverify.requestFocus();
                passwordverify.setText("abcdef");
                assertTrue(password.getText().toString().equals(passwordverify.getText().toString()));
                passwordverify.setText("defabc");
                assertFalse(password.getText().toString().equals(passwordverify.getText().toString()));


            }
        });
    }
/*
    public void testOnCreate() throws Exception {
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {

                //assertNotNull(activity);
                //assertNotNull(name);
                //assertNotNull(email);
                //assertNotNull(password);
                //assertNotNull(passwordverify);

                name.requestFocus();
                name.setText("Abc Def");
                assertEquals(name.getText().toString(), "Abc Def");
                assertTrue(name.getText().toString().contains(" "));
                name.setText("Abcdef");
                assertFalse(name.getText().toString().contains(" "));

                email.requestFocus();
                email.setText("Abc Def");
                assertFalse(email.getText().toString().contains("@"));
                assertFalse(email.getText().toString().contains("."));
                email.setText("abc@def.com");
                assertTrue(email.getText().toString().contains("@"));
                assertTrue(email.getText().toString().contains("."));

                password.requestFocus();
                password.setText("abcdef");
                passwordverify.requestFocus();
                passwordverify.setText("abcdef");
                assertTrue(password.getText().equals(passwordverify.getText()));
                passwordverify.setText("defabc");
                assertFalse(password.getText().equals(passwordverify.getText()));
            }
        });
    }
        */
}
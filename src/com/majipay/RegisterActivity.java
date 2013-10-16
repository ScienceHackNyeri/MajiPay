package com.majipay;



import majipay.dashboard.R;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.majipay.database.DatabaseHandler;
import com.majipay.database.UserFunctions;

public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputIDNo;
	EditText inputKra;
	EditText inputPlot;
	EditText inputAcc;
	EditText inputEmail;
	EditText inputPassword;
	EditText inputPhoneNumber;
	EditText inputconfirmPassword;
	TextView registerErrorMsg;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	static String KEY_C_NO = "confirmPassword";
	private static String KEY_P_NO = "p_no";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputIDNo=(EditText) findViewById(R.id.registerID);
		inputKra=(EditText) findViewById(R.id.registerKra);
		inputPlot=(EditText) findViewById(R.id.registerPlot);
		inputAcc=(EditText)findViewById(R.id.registerAcc);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		inputconfirmPassword = (EditText) findViewById(R.id.confirmPassword);
		inputPhoneNumber = (EditText) findViewById(R.id.registerPhone);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View view) {
				String name = inputFullName.getText().toString();
				String id_no = inputIDNo.getText().toString();
				String kra = inputKra.getText().toString();
				String plot = inputPlot.getText().toString();
				String acc = inputAcc.getText().toString();
				String p_no = inputPhoneNumber.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				String confirmPassword = inputconfirmPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(name, id_no, kra, plot, acc, p_no, email, password, confirmPassword);
				
				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						registerErrorMsg.setText("Welcome to MajiPay!! you have succesfully registered");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							// user successfully registered
							// Store user details in SQLite Database
							DatabaseHandler db = new DatabaseHandler(getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");
							
							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_CREATED_AT), json.getString(KEY_UID));						
							// Launch Booking Screen
							Intent FrontPage = new Intent(getApplicationContext(), AndroidDashboardDesignActivity.class);
							// Close all views before launching Booking
							FrontPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(FrontPage);
							//Close Registration Screen
							finish();
						}else{
							// Error in registration
							registerErrorMsg.setText("Error occured in registration");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}
}

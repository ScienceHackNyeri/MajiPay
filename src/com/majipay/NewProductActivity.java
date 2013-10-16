package com.majipay;

import java.util.ArrayList;
import java.util.List;

import majipay.dashboard.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewProductActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputConstituency;
	EditText inputWard;
	EditText inputName;
	EditText inputPrice;
	EditText inputDesc;
	EditText inputTitledeedNo;
	EditText inputOwnerIdNo;
	EditText inputOwnerName;

	// url to create new product
	private static String url_create_product = "http://10.0.2.2/android_connect_4/new_landowner.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_product);

		// Edit Text
		inputConstituency = (EditText) findViewById(R.id.inputConstituency);
		inputName = (EditText) findViewById(R.id.inputName);
		inputTitledeedNo = (EditText) findViewById(R.id.inputTitledeedNo);
		inputOwnerIdNo = (EditText) findViewById(R.id.inputOwnerIdNo);
		inputPrice = (EditText) findViewById(R.id.inputPrice);
	
	


		// Create button
		Button btnCreateProduct = (Button) findViewById(R.id.btnCreateProduct);

		// button click event
		btnCreateProduct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				
				if(inputConstituency.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter FullName.", Toast.LENGTH_LONG).show();
					  return;
				  }	
				
				if(inputName.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter Land Number.", Toast.LENGTH_LONG).show();
					  return;
				  }	
				if(inputTitledeedNo.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter Title Deed No.", Toast.LENGTH_LONG).show();
					  return;
				  }	
				if(inputOwnerIdNo.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter owner Id No.", Toast.LENGTH_LONG).show();
					  return;
				  }
				if(inputOwnerName.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter constituency.", Toast.LENGTH_LONG).show();
					  return;
				  }	
				if(inputPrice.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter price.", Toast.LENGTH_LONG).show();
					  return;
				  }	
				if(inputDesc.getText().toString().trim().length() == 0) { 
					  Toast.makeText(getApplicationContext(), "Please enter description.", Toast.LENGTH_LONG).show();
					  return;
				  }	
				// creating new product in background thread
				new CreateNewProduct().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewProductActivity.this);
			pDialog.setMessage("Updating New Customer...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Updating New Land Owner
		 * */
		protected String doInBackground(String... args) {
			String constituency = inputConstituency.getText().toString();
			String name = inputName.getText().toString();
			String titledeedNo =inputTitledeedNo.getText().toString();
			String OwnerIdNo =inputOwnerIdNo.getText().toString();
			String price = inputPrice.getText().toString();
			
			

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("constituency", constituency));
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("titledeedNo", titledeedNo));
			params.add(new BasicNameValuePair("OwnerIdNo", OwnerIdNo));
			params.add(new BasicNameValuePair("price", price));
		

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created product
					Intent i = new Intent(getApplicationContext(), AllProductsActivity.class);
					startActivity(i);
					
					// closing this screen
					finish();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}

package com.majipay;


import majipay.dashboard.R;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import android.app.Activity;
import android.content.Intent;


public class AccountsActivity extends Activity {
	/** Called when the activity is first created. */
	EditText registerAccNo;
	Spinner bankSpinner;
	Button buttonNext;
	Button buttonBack;
	Button buttonSave;
    @Override
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.account_details);
	        
	        bankSpinner = (Spinner) findViewById(R.id.bankSpinner);
	        registerAccNo = (EditText) findViewById(R.id.registerAccNo);
	        buttonSave=(Button)findViewById(R.id.buttonSave);
      		buttonBack=(Button)findViewById(R.id.buttonBack);
    		buttonNext=(Button)findViewById(R.id.buttonNext);
	        ArrayAdapter<CharSequence> ir_adapter = ArrayAdapter
    				.createFromResource(this, R.array.bank_array,
    						android.R.layout.simple_spinner_item);
    		ir_adapter
    				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	bankSpinner.setAdapter(ir_adapter);
	       
    	buttonBack.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						AndroidDashboardDesignActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	//buttonNext.setOnClickListener(new View.OnClickListener() {

			//public void onClick(View view) {
				//Intent i = new Intent(getApplicationContext(),
					//	AboutActivity.class);
				//startActivity(i);
				// Close Registration View
				//finish();
			//}
	//	});
    }
    
}

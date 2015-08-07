package com.example.calculator;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {

    private Firebase mRef;
    String key="https://cloudm.firebaseio.com/.json";



    EditText etNum1;
    EditText etNum2;

    Button btnAdd;
    Button btnSub;
    Button btnMult;
    Button btnDiv;

    TextView tvResult;
    TextView json;

    String oper = "";

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);


        // find the elements
        etNum1 = (EditText) findViewById(R.id.etNum1);
        etNum2 = (EditText) findViewById(R.id.etNum2);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnMult = (Button) findViewById(R.id.btnMult);
        btnDiv = (Button) findViewById(R.id.btnDiv);

        tvResult = (TextView) findViewById(R.id.tvResult);
        json = (TextView) findViewById(R.id.text);

        // set a listener
        btnAdd.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnMult.setOnClickListener(this);
        btnDiv.setOnClickListener(this);

        mRef = new Firebase("https://cloudm.firebaseio.com/");
      String strJson = " { \"Employee\":[{\"value\":\"04\"}]}";
       String data = "";
        try {
           JSONObject jsonRootObject = new JSONObject(strJson);

          //Get the instance of JSONArray that contains JSONObjects
           JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");

         //  Iterate the jsonArray and print the info of JSONObjects
          for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);

              int value = Integer.parseInt(jsonObject.optString("value").toString());


               data += "value" + value;
           }
           json.setText(data);
       } catch (JSONException e) {
            e.printStackTrace();
        }
        //try {
          //  JSONObject o=new JSONObject(key);
            //String from=o.getString("value");
            //json.setText(from);
        //} catch (JSONException e) {
          //  e.printStackTrace();
        //}

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        double num1 = 0;
        double num2 = 0;
        double result = 0;


        // check if the fields are empty
        if (TextUtils.isEmpty(etNum1.getText().toString())
                || TextUtils.isEmpty(etNum2.getText().toString())) {
            return;
        }

        // read EditText and fill variables with numbers
        num1 = Double.parseDouble(etNum1.getText().toString());
        num2 = Double.parseDouble(etNum2.getText().toString());

        // defines the button that has been clicked and performs the corresponding operation
        // write operation into oper, we will use it later for output
        switch (v.getId()) {
            case R.id.btnAdd:
                oper = "+";
                result = num1 + num2;
                mRef.child("value").setValue(result);
                break;
            case R.id.btnSub:
                oper = "-";
                result = num1 - num2;
                mRef.child("value").setValue(result);
                break;
            case R.id.btnMult:
                oper = "*";
                result = num1 * num2;
                mRef.child("value").setValue(result);
                break;
            case R.id.btnDiv:
                oper = "/";
                result = num1 / num2;
                mRef.child("value").setValue(result);
                break;
            default:
                break;
        }

        // form the output line
        tvResult.setText(num1 + " " + oper + " " + num2 + " = " + result);

    }
}
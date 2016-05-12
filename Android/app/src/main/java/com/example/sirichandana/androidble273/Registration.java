package com.example.sirichandana.androidble273;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    EditText fname,lname,id,mac;

    Button submit;
    String first;
    String last;
    String Id;
    String Mac;
    String leclass="273";
    String password;
    public static int response;


    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();


    // JSON Node names
    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Context ctx = this;



        fname = (EditText)findViewById(R.id.fnameet);
        lname = (EditText)findViewById(R.id.lnameet);
        id = (EditText)findViewById(R.id.idet);
        mac = (EditText)findViewById(R.id.macet);
        submit = (Button)findViewById(R.id.subbut);



        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        BluetoothAdapter mBlue = BluetoothAdapter.getDefaultAdapter();
        mBlue.getAddress();

       /* mac.setText(mBlue.getAddress());*/ // setting device mac address
        String blue= android.provider.Settings.Secure.getString(this.getContentResolver(), "bluetooth_address");
        mac.setText(blue);

        submit.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        first = fname.getText().toString();
        last = lname.getText().toString();
        Id = id.getText().toString();
        Mac = mac.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(first)){
            fname.setError(getString(R.string.error_field_required));
            focusView = fname;
            cancel = true;
        }
        if (TextUtils.isEmpty(last)){
            lname.setError(getString(R.string.error_field_required));
            focusView = lname;
            cancel = true;
        }
        if (TextUtils.isEmpty(Id)){
            id.setError(getString(R.string.error_field_required));
            focusView = id;
            cancel = true;
        }


        if (first.equalsIgnoreCase("")
                || last.equalsIgnoreCase("")
                || Id.equalsIgnoreCase(""))
                 {
            Toast.makeText(Registration.this, "All Fields Required.", Toast.LENGTH_SHORT).show();
        }
          new NewUser().execute();

        Log.e("msg2", String.valueOf(response));

        this.finish();


    }

    class NewUser extends AsyncTask<String, String, Integer> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Registration.this);
            pDialog.setMessage("Registering User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();


        }

        /**
         * Registering User
         */
        protected Integer doInBackground(String... args) {

            // Building Parameters
            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("first", first));
            params.add(new BasicNameValuePair("last", last));
            params.add(new BasicNameValuePair("Id", Id));
            params.add(new BasicNameValuePair("Mac", Mac));
            params.add(new BasicNameValuePair("Password", password));

            String suc;
            String id;
            String str;
            InputStream inputStream = null;
            String result = "";
            try {
            /*DefaultHttpClient httpClient = new DefaultHttpClient();
//http://chandu.890m.com/android_connect/create_user.php?name=hello&enno=27&branch=be&year=2012&cpi=7&ssc=12&hsc=10&email=shah&pwd=1212
            str = new String("http://siri.electrobucket.org/siri/BLENewUser.php?");
            str += "first=" + first;
            str += "&last=" + last;
            str += "&Id=" + Id;
            str += "&Mac=" + Mac;
            str += "&leclass=" + leclass;

            HttpGet request = new HttpGet(str);
            //     HttpPost httpPost = new HttpPost(url_create_product);
            //     httpPost.setEntity(new UrlEncodedFormEntity(params));

            HttpResponse httpResponse = httpClient.execute(request);

            String response = EntityUtils.toString(httpResponse.getEntity());
            Log.e("result of service ", response);

            JSONObject jsonObject = new JSONObject(response);
            suc = jsonObject.getString("success");
            id = jsonObject.getString("message");

            Toast.makeText(getApplicationContext(),id, Toast.LENGTH_LONG).show();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return "";*/


                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("https://bleserver.herokuapp.com/register/");

                String json = "";

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("first", first);
                jsonObject.put("last", last);
                jsonObject.put("Id", Id);
                jsonObject.put("Mac", Mac);
                jsonObject.put("leclass", leclass);
            /*jsonObject.put("Password",password);*/
                Log.e("Our Json", String.valueOf(jsonObject));
            /*suc = jsonObject.getString("success");
            id = jsonObject.getString("message");*/

                json = jsonObject.toString();
            /*str = new String("https://bleserver.herokuapp.com/register/");
            str += "first=" + first;
            str += "&last=" + last;
            str += "&Id=" + Id;
            str += "&Mac=" + Mac;
            str += "&leclass=" + leclass;
            */
                StringEntity se = new StringEntity(json);

                httpPost.setEntity(se);

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                HttpResponse httpResponse = httpClient.execute(httpPost);

/*
            inputStream = httpResponse.getEntity().getContent();


            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";*/


                Log.w("Response ", "Status line : " + httpResponse.getStatusLine().getStatusCode());

                Toast.makeText(getApplicationContext(),"Here",Toast.LENGTH_LONG).show();
                if(response == 204) {

                    Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(),com.example.sirichandana.androidble273.Login.class);
                    startActivity(i);
                    return 1;

        } else {
            Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
                    return 0;
        }




            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }


            return response;


            /*HttpGet request = new HttpGet(str);*/
            //     HttpPost httpPost = new HttpPost(url_create_product);
            //     httpPost.setEntity(new UrlEncodedFormEntity(params));

            /*HttpResponse httpResponse = httpClient.execute(request);

            String response = EntityUtils.toString(httpResponse.getEntity());
            Log.e("result of service ", response);
/*Toast.makeText(getApplicationContext(), ,Toast.LENGTH_LONG).show();*/


        }


        protected void onPostExecute(Integer file_url) {
            // dismiss the dialog once done
            pDialog.hide();
        }
    }




    }


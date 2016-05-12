package com.example.sirichandana.androidble273;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText uname,pass;
    String Id,pwd;
    Button signIn;
    public SharedPreferences pref;
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = (EditText)findViewById(R.id.idet);
        pass= (EditText)findViewById(R.id.passet);
        Id = uname.getText().toString();
        pwd = pass.getText().toString();

        signIn = (Button)findViewById(R.id.subbut);

        signIn.setOnClickListener(this);


        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(Id)) {
            uname.setError(getString(R.string.error_field_required));
            focusView = uname;
            cancel = true;
        }


    }

    @Override
    public void onClick(View v) {
        InputStream inputStream = null;
        String result = "";
        /*ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        String status = info.isConnected() + "";


        if (status.equals("false")) {
            Toast.makeText(getApplicationContext(), "No Internet Connection \n\nPlease Connect to Internet", Toast.LENGTH_SHORT).show();
        }
        else if(status.equals("true")) {
*/
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("Username", Id));
        nameValuePairs.add(new BasicNameValuePair("Password", pwd));

        StrictMode.setThreadPolicy(policy);

        String str, suc, id;
        try {

            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("https://bleserver.herokuapp.com/register/");

            String json = "";

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userid", Id);
            jsonObject.put("password", pwd);

            Log.e("Our Json", String.valueOf(jsonObject));
            /*suc = jsonObject.getString("success");
            id = jsonObject.getString("message");*/

            json = jsonObject.toString();
            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpClient.execute(httpPost);


            inputStream = httpResponse.getEntity().getContent();


            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

            HttpResponse response = httpClient.execute(httpPost);
            Log.w("Response ","Status line : "+ response.getStatusLine().toString());

            if(response.equals("1")) {

                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            else if (response.equals("0")) {
                Toast.makeText(getApplicationContext(), "Invalid Username and Password", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }



                /*DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
                str = new String("http://siri.electrobucket.org/siri/BLElogin.php?");
                str += "first=" + user;
                str += "&Id=" + Id;
                HttpGet request = new HttpGet(str);
                HttpResponse httpResponse = defaultHttpClient.execute(request);
                String response = EntityUtils.toString(httpResponse.getEntity());
                Log.e("result of service ", response);

                JSONObject jsonObject = new JSONObject(response);
                suc = jsonObject.getString("suc");
                id = jsonObject.getString("id");

                Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                if(suc.equals("1")) {

                    Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
                else if (suc.equals("0")) {
                    Toast.makeText(getApplicationContext(), "Invalid Username and Id", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }*/
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}

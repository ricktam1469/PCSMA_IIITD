package com.example.kajal.shiv.pcsma3asgnmnt;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Get_Quiz_Status extends Activity implements View.OnClickListener
  {
    EditText etResponse;
    TextView tvIsConnected;
      Button stats1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gt_status);
        // get reference to the views
        etResponse = (EditText) findViewById(R.id.etResponse);
        tvIsConnected = (TextView) findViewById(R.id.tvIsConnected);
        stats1 = (Button) findViewById(R.id.rd);
        // Set OnClick Listener on SignUp button
        stats1.setOnClickListener(this);
        // check if you are connected or not
        if(isConnected())
        {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are conncted");
        }
        else
        {
            tvIsConnected.setText("You are NOT conncted");
        }
        // show response on the EditText etResponse 
        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://192.168.21.207:8080/quizdetails");

           }
      public void onClick(View v)
      {
          switch (v.getId())
          {
              case R.id.rd:
                  // TODO Auto-generated method stub
                  /// Create Intent for SignUpActivity  abd Start The Activity
                  Intent intentSignUP = new Intent(getApplicationContext(), MainActivity.class);
                  startActivity(intentSignUP);
                  finish();
                  break;
          }
      }
    public static String GET(String url)
    {
        InputStream inputStream = null;
        String result = "";
        try
        {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        }
        catch (Exception e)
        {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException
    {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = " ";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        //System.out.println(result);
        return result;
    }
    public boolean isConnected()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... urls)
        {
            return GET(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            etResponse.setText(result);
            if(result==" ")
            {
                stats1.setEnabled(false);
                Toast.makeText(getBaseContext(), "Woohoo! There is no Quiz Today!", Toast.LENGTH_LONG).show();

            }
        }
    }
}
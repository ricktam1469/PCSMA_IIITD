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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Info extends Activity implements View.OnClickListener
 {
    private TextView name, rollno, email;
    RadioGroup rgOpinion;
    Button btnSubmit;
     StuQuizDetails person;
     RadioButton selectRadio;
     protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_file);
        name = (TextView) findViewById(R.id.name);
        rollno = (TextView) findViewById(R.id.rollno);
        email = (TextView) findViewById(R.id.email);
        Intent data = getIntent();
        name.setText((data.getExtras().get("name")).toString());
        rollno.setText((data.getExtras().get("rollno")).toString());
        email.setText((data.getExtras().get("email")).toString());
        /** Called when the activity is first created. */
        // Init Widget GUI
        rgOpinion = (RadioGroup) findViewById(R.id.rgOpinion);
        btnSubmit = (Button) findViewById(R.id.sbmt);
        // Attached Click listener to Button
        btnSubmit.setOnClickListener(this);
    }
     public static String POST(String url, StuQuizDetails person)
     {
         InputStream inputStream = null;
         String result = "";
         try
         {
             // 1. create HttpClient
             HttpClient httpclient = new DefaultHttpClient();

             // 2. make POST request to the given URL
             HttpPost httpPost = new HttpPost(url);

             String json = "";

             // 3. build jsonObject
             JSONObject jsonObject = new JSONObject();
             jsonObject.accumulate("name", person.getName());
             jsonObject.accumulate("rollnumber", person.getRollNumber());
             jsonObject.accumulate("email", person.getEmail());
             jsonObject.accumulate("response", person.getResponse());

             // 4. convert JSONObject to JSON to String
             json = jsonObject.toString();

             // ** Alternative way to convert Person object to JSON string usin Jackson Lib
             // ObjectMapper mapper = new ObjectMapper();
             // json = mapper.writeValueAsString(person);

             // 5. set json to StringEntity
             StringEntity se = new StringEntity(json);

             // 6. set httpPost Entity
             httpPost.setEntity(se);

             // 7. Set some headers to inform server about the type of the content
             httpPost.setHeader("Accept", "application/json");
             httpPost.setHeader("Content-type", "application/json");

             // 8. Execute POST request to the given URL
             HttpResponse httpResponse = httpclient.execute(httpPost);

             // 9. receive response as inputStream
             inputStream = httpResponse.getEntity().getContent();


             // 10. convert inputstream to string
             if(inputStream != null)
                 result = convertInputStreamToString(inputStream);
             else
                 result = "Did not work!";

         }
         catch (Exception e)
         {
             Log.d("InputStream", e.getLocalizedMessage());
         }
         // 11. return result
         return result;
     }

     public boolean isConnected()
     {
         ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
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
             person = new StuQuizDetails();
             person.setName(name.getText().toString());
             person.setRollNumber(rollno.getText().toString());
             person.setEmail(email.getText().toString());
             person.setResponse(selectRadio.getText().toString());

            return POST(urls[0],person);
         }
         // onPostExecute displays the results of the AsyncTask.
         @Override
         protected void onPostExecute(String result)
         {
             Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
         }
     }
     private boolean validate()
     {
         if(name.getText().toString().trim().equals(""))
             return false;
         else if(rollno.getText().toString().trim().equals(""))
             return false;
         else if(email.getText().toString().trim().equals(""))
             return false;
         else
             return true;
     }
     private static String convertInputStreamToString(InputStream inputStream) throws IOException
     {
         BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
         String line = "";
         String result = "";
         while((line = bufferedReader.readLine()) != null)
             result += line;
         inputStream.close();
         return result;
     }
    public void onClick(View v)
    {
     // TODO Auto-generated method stub
        if (v == btnSubmit)
        {
            if(!validate())
                Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
            // call AsynTask to perform network operation on separate thread
            new HttpAsyncTask().execute("http://192.168.21.207:8080/quizpath");
            // Get Selected Radio Button and display output
            selectRadio = (RadioButton) findViewById(rgOpinion.getCheckedRadioButtonId());
            String opinion = selectRadio.getText().toString();
            Toast.makeText(this, "Your option is : " + opinion, Toast.LENGTH_LONG).show();
        }
    }
}
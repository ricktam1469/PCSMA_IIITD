package com.example.kajal.shiv.pcsma3asgnmnt;
import android.app.Activity;
import android.content.Intent;
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

public class MainActivity extends Activity implements View.OnClickListener
  {
      private EditText name, rollno;
      TextView email;
      private Button submit_btn, clear_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.etName);
        rollno = (EditText) findViewById(R.id.etRoll);
        email= (TextView) findViewById(R.id.email);
        submit_btn = (Button) findViewById(R.id.nxt);
        clear_btn = (Button) findViewById(R.id.clr);
        submit_btn.setOnClickListener(this);
        clear_btn.setOnClickListener(this);
        Intent data = getIntent();
        email.setText((data.getExtras().get("email")).toString());
        // show response on the EditText etResponse
        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask().execute("http://192.168.21.207:8080/quizdetails");
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
        System.out.println(result);
        return result;
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
            Toast.makeText(getBaseContext(), "Response Received From Server!", Toast.LENGTH_LONG).show();
            if(result==" ")
            {
                submit_btn.setEnabled(false);
                Toast.makeText(getBaseContext(), "Woohoo! There is no Quiz Today!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getBaseContext(), "You Can Start Your Quiz!", Toast.LENGTH_LONG).show();
            }

            }
    }

      public void onClick(View v)
      {
          switch (v.getId())
          {
              case R.id.nxt:
                  Intent Main2Activity = new Intent(this, Info.class);
                  Main2Activity.putExtra("name", name.getText().toString());
                  Main2Activity.putExtra("rollno", rollno.getText().toString());
                  Main2Activity.putExtra("email", email.getText().toString());
                  startActivity(Main2Activity);
                  break;

              case R.id.clr:
                  name.setText("");
                  rollno.setText("");
                  break;

              default:
                  break;
          }
      }

}
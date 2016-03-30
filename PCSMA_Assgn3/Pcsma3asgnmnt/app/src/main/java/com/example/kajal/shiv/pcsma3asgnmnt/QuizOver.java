package com.example.kajal.shiv.pcsma3asgnmnt;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import java.util.ArrayList;
import java.util.List;

public class QuizOver extends AppCompatActivity {

    TextView status;
    AsyncTask<String, Void, String> task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_over);

        status = (TextView) findViewById(R.id.status);
        Intent data = getIntent();
        status.setText((data.getExtras().get("status")).toString());

        task = new HttpAsyncTask1().execute("http://192.168.21.207:8080/detailedPerformance");
    }


    public static String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = " ";
        while ((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        System.out.println(result);
        return result;
    }

    private class HttpAsyncTask1 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return GET(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override

        protected void onPostExecute(final String result) {


           // Runnable r = new Runnable() {
               // @Override
                //public void run() {
                    try {
                        ////////////////////////////String Parsing////////////////////

                        //String str="{\"qid\":12,\"ques\":\"Question you know?\",\"timer\":1,\"answer\":\"NA\"}";
                        String resultN=result.substring(2, result.length() - 1);
                        String[] commaSplit = resultN.split(",");

                        List<Integer> quizId=new ArrayList<Integer>();
                        List<String> emails=new ArrayList<String>();
                        List<String> qrslt=new ArrayList<String>();
                        int length=commaSplit.length;
                        int i=0;

                        while(length>i){
                            String[] qid = commaSplit[i].split(":");
                            quizId.add(Integer.parseInt(qid[1]));
                            String[] email = commaSplit[i+4].split(":");
                            String[] emailnew=email[1].split("_");
                           // emails.add(email[1].substring(1,(email[1].length()-1)));
                            emails.add(emailnew[0]);
                            String[] rslt = commaSplit[i+5].split(":");
                            qrslt.add(rslt[1].substring(0,rslt[1].length()-1));

                            i=i+6;
                        }

                        //ques = qt[1].substring(1, (qt[1].length() - 1));
                        // timer = Integer.parseInt(time[1]);


                        // Toast.makeText(getBaseContext(),+"--"+timer[1], Toast.LENGTH_LONG).show();
                        ////////////////////////////////////////////////

                       // Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
                        if (result == null) {
                            // submit_btn.setEnabled(false);
                            Toast.makeText(getBaseContext(), "Woohoo! There is no Quiz Today!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "You Can Start Your Quiz!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Nothing to Show", Toast.LENGTH_SHORT).show();
                        // timer = 0;
                        // ques = null;
                    }

                   // task = new HttpAsyncTask1().execute("http://192.168.21.207:8080/detailedPerformance");
                //}
           // };
           // Handler h = new Handler();
           // h.postDelayed(r, 3000);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            //timer = 0;
            //ques = null;

        }
    }
}
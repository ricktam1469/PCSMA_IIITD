package com.smartapps.Rick_Accelerometer;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;

import com.opencsv.CSVWriter;

public class MainActivity extends Activity implements SensorEventListener,
		OnClickListener {

	private SensorManager sensorManager;
	private Button btnStart, btnStop, btnUpload;
	private boolean started = false;
	private LinearLayout layout;
	Sensor sense_Acl;
	double x;
	double y;
	double z;
	long timestamp;
	TextView adata ,messageText;
	List<String[]> csvVal;
	String csvstr;

	String baseDir ;
	String fileName ;
	String filePath;
	private Socket client;
	private FileInputStream fileInputStream;
	private BufferedInputStream bufferedInputStream;
	private OutputStream outputStream;
	Socket socket;
	private String host;
	private int port;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		messageText  = (TextView)findViewById(R.id.messageText);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		//sensorData = new ArrayList<AccelerometerClass>();


		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		sense_Acl = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		btnUpload = (Button) findViewById(R.id.btnUpload);

		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
		btnUpload.setOnClickListener(this);

		btnStart.setEnabled(true);

		btnStop.setEnabled(false);
		btnStop.setBackgroundColor(Color.GRAY);

		btnUpload.setEnabled(false);
		btnUpload.setBackgroundColor(Color.GRAY);

		adata=(TextView)findViewById(R.id.a_data);


	}

	public void print()
	{
		Toast.makeText(getApplicationContext(),adata.getText(),Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),filePath,Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),sense_Acl.getName(),Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),fileName,Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),btnStop.getText(),Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),messageText.getText(),Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),btnStart.getText(),Toast.LENGTH_LONG).show();
		Toast.makeText(getApplicationContext(),btnUpload.getText(),Toast.LENGTH_LONG).show();

	}


	public Button getBtnStart() {
		return btnStart;
	}

	public Button getBtnUpload() {
		return btnUpload;
	}

	public TextView getAdata() {
		return adata;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void setBtnUpload(Button btnUpload) {
		this.btnUpload = btnUpload;
	}

	public void setBtnStart(Button btnStart) {
		this.btnStart = btnStart;
	}

	@Override
	protected void onResume() {
		super.onResume();
		sensorManager.registerListener(this, sense_Acl, sensorManager.SENSOR_DELAY_NORMAL);

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (started == true) {
			sensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		if (started) {

			x = event.values[0];
			y = event.values[1];
			z = event.values[2];
			timestamp = System.currentTimeMillis();

			csvstr=x+","+y+","+z+","+timestamp;

			csvVal.add(new String[]{csvstr});

			adata.setText("x= "+x+"\n y= "+y+"\n z= "+z+"\n TS= "+timestamp);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.btnStart:

				csvVal = new ArrayList<String[]>();
				messageText.setText("Accelerometer Started");
			btnStart.setEnabled(false);
			btnStart.setBackgroundColor(Color.GRAY);

			btnStop.setEnabled(true);
			btnStop.setBackgroundColor(Color.RED);

			btnUpload.setEnabled(false);

			started = true;
			Sensor sense_Acl = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

			sensorManager.registerListener(this, sense_Acl,sensorManager.SENSOR_DELAY_NORMAL);
			break;

		case R.id.btnStop:

			messageText.setText("Accelerometer Stopped");
			btnStart.setEnabled(true);
			btnStart.setBackgroundColor(Color.GREEN);

			btnStop.setEnabled(false);
			btnStop.setBackgroundColor(Color.GRAY);

			btnUpload.setEnabled(true);
			btnUpload.setBackgroundColor(Color.YELLOW);

			started = false;
			sensorManager.unregisterListener(this);

			CSVWrite(csvVal);

			Toast.makeText(getApplicationContext(),fileName +" is saved to memory Card",Toast.LENGTH_SHORT).show();

			adata.setText("x= "+x+"\n y= "+y+"\n z= "+z+"\n TS= "+timestamp);
			break;

		case R.id.btnUpload:

			messageText.setText("Client Server Protocol Started");
			SocketClientAssgn t=new SocketClientAssgn();
			t.connect(getApplicationContext(), "192.168.21.207",4007); //IP Address of the server
			//t.disconnect(getApplicationContext());

            break;

		default:
			break;
		}

	}

	void CSVWrite(List<String[]> csvVal)
	{
		try {
			baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
			fileName = "AnalysisData.csv";
			filePath = baseDir + File.separator + fileName;
			File fle = new File(filePath );
			CSVWriter csv_writer;
            // File exist
			if(fle.exists() && !fle.isDirectory()){

				//csv_writer = new CSVWriter(new FileWriter(filePath , true), ',', CSVWriter.NO_QUOTE_CHARACTER); //Appending
				csv_writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER); //Create a new CSV
			}
			else {
				csv_writer = new CSVWriter(new FileWriter(filePath), ',', CSVWriter.NO_QUOTE_CHARACTER);
			}
			csv_writer.writeAll(csvVal);

			csv_writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class SocketClientAssgn {

		private final String TAG = SocketClientAssgn.class.getSimpleName();

		private Socket socket;
		private PrintWriter out;
		private boolean cnctd;

		public SocketClientAssgn()
		{
			socket = null;
			out = null;
			cnctd = false;
		}


		public void connect(Context context, String host, int port)
		{
			new ConnectTask(context).execute(host, String.valueOf(port));
		}

		private class ConnectTask extends AsyncTask<String, Void, Void> {

			private Context context;

			public ConnectTask(Context context) {
				this.context = context;
			}

			@Override
			protected void onPreExecute() {
				showToast(context, "Connecting to Server..");
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void result) {
				if (cnctd) {
					showToast(context, "Connection successfull with the server");
				}
				super.onPostExecute(result);
			}



			@Override
			protected Void doInBackground(String... params) {
				try {
					String host = params[0];
					int port = Integer.parseInt(params[1]);
					socket = new Socket(host, port);
					out = new PrintWriter(socket.getOutputStream(), true);
					send(csvstr);
				} catch (UnknownHostException e) {
					showToast(context, "Host is Unknown:: " + host + ":" + port);
					Log.e(TAG, e.getMessage());
				} catch (IOException e) {
					showToast(context, "There is no I/O for the connection to: " + host + ":" + port);
					Log.e(TAG, e.getMessage());
				}
				cnctd = true;
				return null;
			}


		}

		public void disconnect(Context context)
		{
			if ( cnctd )
			{
				try {
					out.close();
					socket.close();
					cnctd = false;
				} catch (IOException e) {
					showToast(context, "There is no I/O for the connection");
					Log.e(TAG, e.getMessage());
				}
			}
		}


		public void send(String command)
		{
			FileInputStream fstrm = null;
			BufferedInputStream bstream = null;
			OutputStream os = null;
			DataOutputStream dstream=null;
			try
				{
                	//Send the message to the server
					os = socket.getOutputStream();
					dstream=new DataOutputStream((os));
					OutputStreamWriter osw = new OutputStreamWriter(os);
					BufferedWriter bw = new BufferedWriter(osw);

					File file = new File(filePath);

					byte [] mybytearray  = new byte [(int)file.length()];

					fstrm = new FileInputStream(file);
					bstream = new BufferedInputStream(fstrm);

					while (fstrm.read(mybytearray) > 0) {
						dstream.write(mybytearray);
					}

					//bis.read(mybytearray,0,mybytearray.length);
					//os.write(mybytearray, 0, mybytearray.length);
					dstream.flush();
					//String number = "2";

					//String sendMessage = "Val is: "+ command + "\n";
					//bw.write(sendMessage);

					//bw.flush();
					//System.out.println("Message sent to the server : " + sendMessage);

					//Get the return message from the server
				//	InputStream is = socket.getInputStream();
				//	InputStreamReader isr = new InputStreamReader(is);
				//	BufferedReader br = new BufferedReader(isr);
				//	String message = br.readLine();
				//	System.out.println("Message received from the server : " +message);
				}
				catch (Exception exception)
				{
					exception.printStackTrace();
				}
				finally
				{
					//Closing the socket
					try
					{
						socket.close();
						if (bstream != null) bstream.close();
						if (dstream != null) dstream.close();

					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}

		}
		private void setTxt (final Context context, final String message) {
			new Handler(context.getMainLooper()).post(new Runnable() {

				@Override
				public void run() {

				}
			});
		}
		private void getTxt (final Context context, final String message) {
			new Handler(context.getMainLooper()).post(new Runnable() {

				@Override
				public void run() {

				}
			});
		}

		private void showToast(final Context context, final String message) {
			new Handler(context.getMainLooper()).post(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				}
			});
		}
	}

}

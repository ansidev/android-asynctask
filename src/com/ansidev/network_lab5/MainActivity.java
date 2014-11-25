package com.ansidev.network_lab5;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText mEditText = (EditText) findViewById(R.id.editText1);
	TextView mTextView = (TextView) findViewById(R.id.textView1);
	ImageView mImageView = (ImageView) findViewById(R.id.imageView1);
	ProgressDialog progDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (android.os.Build.VERSION.SDK_INT> 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			}
		setContentView(R.layout.activity_main);
	}
	
	public void getData() {
		GetDataAsyncTask task = new GetDataAsyncTask();
		task.execute(new String[] { mEditText.getText().toString() });
	}
	
	public void clear() {
		mEditText.setText(null);
		mTextView.setText(null);
		mImageView.setImageBitmap(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class GetDataAsyncTask extends AsyncTask<String, Integer, HttpEntity> {
		protected HttpEntity doInBackground(String... url) {
			HttpEntity entity = RESTFULL.doGet(url[0]);
			if(RESTFULL.flag == 0) {
				StringBuffer sBuffer = new StringBuffer();
				try {
					sBuffer.append(EntityUtils.toString(entity));
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mTextView.setText(sBuffer.toString());
			}
			if(RESTFULL.flag == 1) {
				Bitmap mBitmap = null;
				try {
					mBitmap = new BitmapDrawable(entity.getContent()).getBitmap();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				mImageView.setImageBitmap(mBitmap);
			}
			return entity;
	     }

	     protected void onProgressUpdate(Integer... values) {
	    	 super.onProgressUpdate(values);
	    	 progDialog = new ProgressDialog(MainActivity.this);
	    	 progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	    	 progDialog.setTitle("Processing...");
	    	 progDialog.setMessage("Please wait for a while...");
	    	 progDialog.show();
	     }

	     protected void onPostExecute(HttpEntity result) {
	    	 super.onPostExecute(result);
	    	 progDialog.dismiss();
	     }
     }
}

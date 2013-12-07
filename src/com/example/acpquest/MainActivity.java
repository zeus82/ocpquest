package com.example.acpquest;

import java.io.File;
import java.util.ArrayList;

import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText question;
	EditText category;
	EditText title;
	Button camera;
	Button submit;
	String emailContent;
	ArrayList catArray;
	String emailTitle;

	String path = Environment.getExternalStorageDirectory() + "/CameraImages/example.jpg";
    File file = new File(path);
    Uri outputFileUri = Uri.fromFile( file );
    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
	//Camera cam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		title = (EditText) findViewById(R.id.titleText);
		setContentView(R.layout.activity_main);
		emailContent = "";
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//catArray = new ArrayList();
				emailTitle = (String) title.getText().toString();
				
				emailContent += "[category " + emailTitle + "]\n";
				emailContent += "[tags " + question.getText() + "]";
				
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mave560hima@post.wordpress.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
				i.putExtra(Intent.EXTRA_TEXT   , emailContent);
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}
				
			}});
		category = (EditText) findViewById(R.id.editTextCategory);
		question = (EditText) findViewById(R.id.question);
		camera = (Button) findViewById(R.id.camera);
		camera.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//cam = Camera.open();
				//cam.startPreview();
				//outputFileUri = "/storage/image";
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, -1);
				String outputFileUri = null ;
				intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
				
				/*Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mave560hima@post.wordpress.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
				i.putExtra(Intent.EXTRA_TEXT   , "body of email");
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
				    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
				}*/
				
			}});
		//categoriesLabel.set
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	 
	
}

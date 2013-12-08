package com.example.acpquest;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText question;
	EditText category;
	EditText title;
	EditText tags;
	EditText whatItIs;
	EditText whatIKnowAboutIt;
	EditText whatIWantToKnowAboutIt;
	EditText whatIWantToDoAboutIt;
	Button camera;
	Button submit;
	String emailContent;
	ArrayList catArray;
	String emailTitle;
	String emailCategory;
	String emailTags;
	String emailQuestion;
	Uri outputFileUri= null;
	
//	long dtMili = System.currentTimeMillis();
 
//	String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/" + dtMili + ".JPG";
//    File file = new File(path);
//    Uri outputFileUri = Uri.fromFile( file );
 //  Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
	//Camera cam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		title = (EditText) findViewById(R.id.titleText);
		category = (EditText) findViewById(R.id.editTextCategory);
		tags = (EditText) findViewById(R.id.editTextTags);
		question = (EditText) findViewById(R.id.question);
		
		emailContent = "";
		submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				//catArray = new ArrayList();
				emailTitle = (String) title.getText().toString();
				emailCategory = (String) category.getText().toString();
				emailTags = (String) tags.getText().toString();
				emailQuestion = (String) question.getText().toString();
				
						
				emailContent += "[category " + emailCategory + "]\n";
				emailContent += "[tags " + emailTags + "]\n";
				emailContent += emailQuestion;
				
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mave560hima@post.wordpress.com"});
				i.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
				i.putExtra(Intent.EXTRA_TEXT   , emailContent);
				//i.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/Myimage.jpeg"));
				//i.putExtra(Intent.EXTRA_STREAM, Uri.parse(outputFileUri.getPath()));
				i.setType("image/jpeg");
				
				i.putExtra(Intent.EXTRA_STREAM, outputFileUri);
				try {
				    startActivity(Intent.createChooser(i, "Send mail..."));
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(getApplicationContext(), "There are no email clients installed." + "ex:  " + ex.getMessage(), Toast.LENGTH_SHORT).show();
				}
				
			}});

		camera = (Button) findViewById(R.id.camera);
		camera.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				//cam = Camera.open();
				//cam.startPreview();
				//outputFileUri = "/storage/image";
				long dtMili = System.currentTimeMillis();
				String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/" + dtMili + ".jpg";
			    File file = new File(path);
			    outputFileUri = Uri.fromFile( file );
			    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
				//Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
			    startActivityForResult(intent, -1);
				//String outputFileUri = null ;
				
				
				
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

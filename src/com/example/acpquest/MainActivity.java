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
    
    /*Button that user presses to activate the camera*/
    Button camera;
    
    /*Button to submit the email*/
    Button submit;
    
    /*String used to collect  the contents of the email*/
    String emailContent;
    
    /*String representing the title of the email*/
    String emailTitle;
    
    /*String representing the category of the email*/
    String emailCategory;
    
    /*String representing a tag for the post*/
    String emailTags;
    
    /*String representing the Uri for the location of the picture*/
    Uri outputFileUri= null;
    
    /*boolean*/
    boolean hasPicture = false;
  
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        /*Initializing all the gui fields*/
        title = (EditText) findViewById(R.id.titleText);
        category = (EditText) findViewById(R.id.editTextCategory);
        tags = (EditText) findViewById(R.id.editTextTags);
        question = (EditText) findViewById(R.id.question);
        whatItIs = (EditText) findViewById(R.id.EditText1);
        whatIKnowAboutIt = (EditText) findViewById(R.id.EditText02);
        whatIWantToKnowAboutIt = (EditText) findViewById(R.id.question);
        whatIWantToDoAboutIt = (EditText) findViewById(R.id.EditText03);
        submit = (Button) findViewById(R.id.submit);
        
        /*adding onClickListener to the submit button
         * -Once clicked we build the email content from fields represented
         * 	by the references:  whatItIs, whatIKnowAboutIt, whatIWantToKnowAboutIt, and whatIWantToKnowAboutIt
         *  We currently check if any of these fields are not blank and if they are we exclude them from the email content
         *  -We also check if the boolean value hasPicture is true
         *  	this indicates if the camera button has been pressed and then we add the picture
         *  	to the email
         *  -Next we start the email intent
         *  -once the email intent is finished we then reset all the text fields and set the 
         *  	hasPicture to false.  The user can now take another picture and submit a new
         *  	post*/
        submit.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                emailTitle = (String) title.getText().toString();
                emailCategory = (String) category.getText().toString();
                emailTags = (String) tags.getText().toString();
                emailContent += "[category " + emailCategory + "]\n";
                emailContent += "[tags " + emailTags + "]\n";
                
                /*check if the question fields have been filled*/ 
                if(!whatItIs.getText().toString().equals("")){
                    emailContent += "What it is?: " + whatItIs.getText().toString() + "\n";
                }
                if(!whatIKnowAboutIt.getText().toString().equals("")){
                    emailContent += "\nWhat I know about it?: " + whatIKnowAboutIt.getText().toString() + "\n";
                }
                if(!whatIWantToKnowAboutIt.getText().toString().equals("")){
                    emailContent += "\nWhat I want to know about it?: " + whatIWantToKnowAboutIt.getText().toString() + "\n";
                }
                if(!whatIWantToDoAboutIt.getText().toString().equals("")){
                    emailContent += "\nWhat I want to do about it?: " + whatIWantToDoAboutIt.getText().toString() + "\n";
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"mave560hima@post.wordpress.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, emailTitle);
                i.putExtra(Intent.EXTRA_TEXT   , emailContent);
                
                /*Check if a picture has been taken and if so include it in the email*/
                //if(hasPicture){
	            i.setType("image/jpeg");
	            i.putExtra(Intent.EXTRA_STREAM, outputFileUri);
                //}
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed." + "ex:  " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                
                /*Clears all the fields*/
                whatItIs.setText("");
                whatIKnowAboutIt.setText("");
                whatIWantToKnowAboutIt.setText("");
                whatIWantToDoAboutIt.setText("");
                title.setText("");
                category.setText("");
                tags.setText("");
                question.setText("");
                hasPicture = false;
                emailContent = "";
                emailTitle = "";
                System.exit(0);
                
            }});


        emailContent = "";
        
        
        camera = (Button) findViewById(R.id.camera);
        
        /*This button handles the image capture and then sets the hasPicture boolean*/
        camera.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                long dtMili = System.currentTimeMillis();
                String path = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/" + dtMili + ".jpg";
                File file = new File(path);
                outputFileUri = Uri.fromFile( file );
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
                intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
                startActivityForResult(intent, -1);
                //hasPicture = true;
            }});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    } 
    
}
package com.example.licykk.facetag;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

/**
 * Created by Licykk on 12/1/18.
 */

public class GameActivity extends AppCompatActivity {
    static final int REQUEST_TAKE_PHOTO = 1;
    private String photoPath = null;
    private String team;
    MediaPlayer mediaPlayer;
    TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //GRAB TEAM FROM INTENT
        team = getIntent().getStringExtra(TeamActivity.team);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void takePicture(View view) {
        // Create intent to open camera app
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Proceed only if there is a camera app
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            // Attempt to allocate a file to store the photo
            File photoFile;
            try {
                File storageDir = getFilesDir();
                photoFile = File.createTempFile("SNAPSHOT", ".jpg", storageDir);
                photoPath = photoFile.getAbsolutePath();
            } catch (IOException ex) { return; }
            // Send off to the camera app to get a photo
            Uri photoURI = FileProvider.getUriForFile(this, "com.example.clarifaialarm.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }
    }
}

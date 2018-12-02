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
    String photoPath = null;
    String team = "MLH";
    TextView score;
    TextView info;
    boolean ispoint = false;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //GRAB TEAM FROM INTENT
        team = getIntent().getStringExtra(TeamActivity.EXTRA_MESSAGE);
        score = findViewById(R.id.score);
        info = findViewById(R.id.info);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void takePicture(View view) {
        info.setText("Score:");
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

    private class ClarifaiTask extends AsyncTask<File, Integer, Boolean> {

        protected Boolean doInBackground(File... images) {
            // Connect to Clarifai using your API token
            ClarifaiClient client = new ClarifaiBuilder("fbe6ff593211430e8555fd3c7fcd6c4f").buildSync();
            List<ClarifaiOutput<Concept>> predictionResults;
            // For each photo we pass, send it off to Clarifai
            for (File image : images) {
                predictionResults = client.getDefaultModels().generalModel().predict()
                        .withInputs(ClarifaiInput.forImage(image)).executeSync().get();
                // Check if Clarifai thinks the photo contains the object we are looking for
                for (ClarifaiOutput<Concept> result : predictionResults)
                    for (Concept datum : result.data())
                        if ((datum.name().contains(team)))
                            //if(datum.value() > 50)
                            ispoint = true;
                            return true;
            }
            return false;
        }
        protected void onPostExecute(Boolean result) {
            // Delete photo
            (new File(photoPath)).delete();
            photoPath = null;

            if(ispoint){
                info.setText("COOZ");
            }else{
                info.setText("LOZER");
            }

            if(score.getText()=="0"){
                score.setText("1");
            }
            if(score.getText()=="1"){
                score.setText("2");
            }

            // If image contained object, close the AlarmActivity
            if (score.getText() == "2") {
                info.setText("Success!");
                score.setText("Score: " + Integer.parseInt(score.getText().toString()));
                finish();
            }
            ispoint = false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        // If we've taken a photo, send it off to Clarifai to check
        if (photoPath != null) {
            new ClarifaiTask().execute(new File(photoPath));
        }
    }
}
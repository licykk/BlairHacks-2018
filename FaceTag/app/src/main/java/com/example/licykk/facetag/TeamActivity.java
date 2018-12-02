package com.example.licykk.facetag;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class TeamActivity extends AppCompatActivity{
    //ADD TEAM INTO INTENT
    String team = "MLH";

    public static final String EXTRA_MESSAGE = "com.example.licykk.MESSAGE";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void RedTeam(View view){
        team = "MLH";
        playGame();
    }

    public void BlueTeam(View view){
        team = "BlairHacks";
        playGame();
    }

    public void playGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(EXTRA_MESSAGE, team);
        startActivity(intent);
    }


}

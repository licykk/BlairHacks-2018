package com.example.licykk.facetag;

import android.widget.EditText;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Licykk on 12/1/18.
 */

public class TeamActivity extends AppCompatActivity{
    //ADD TEAM INTO INTENT
    String team;

    public static final String EXTRA_MESSAGE = "com.example.licykk.MESSAGE";
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public void RedTeam(){
        team = "MLH";
    }

    public void BlueTeam(){
        team = "BlairHacks";
    }

}

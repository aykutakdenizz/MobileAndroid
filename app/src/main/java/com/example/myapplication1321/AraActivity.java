package com.example.myapplication1321;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AraActivity extends AppCompatActivity {
    private Button mailButton,userListButton,userDetailsButton,userNoteButton,sensorListButton,asyncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ara);

        setComponents();
        setClickListeners();

    }
    private void setComponents(){
        mailButton = (Button) findViewById(R.id.mailButton);
        userListButton = (Button) findViewById(R.id.userListButton);
        userDetailsButton = (Button) findViewById(R.id.userDetailsButton);
        userNoteButton = (Button) findViewById(R.id.userNoteButton);
        sensorListButton = (Button) findViewById(R.id.sensorListButton);
        asyncButton = (Button) findViewById(R.id.araAsyncTask);

    }
    private  void setClickListeners(){
        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mailIntent = new Intent(AraActivity.this, MailActivity.class);
                startActivity(mailIntent);
            }
        });
        userListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userListIntent = new Intent(AraActivity.this,ListActivity.class);
                startActivity(userListIntent);
            }
        });
        userDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userDetailsIntent = new Intent(AraActivity.this, UserDetailActivity.class);
                startActivity(userDetailsIntent);
            }
        });
        userNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userNoteIntent = new Intent(AraActivity.this, NoteListActivity.class);
                startActivity(userNoteIntent);
            }
        });
        sensorListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sensorListIntent = new Intent(AraActivity.this,LightSensorActivity.class);
                startActivity(sensorListIntent);
            }
        });
        asyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asyncIntent = new Intent(AraActivity.this,AsyncTaskActivity.class);
                startActivity(asyncIntent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

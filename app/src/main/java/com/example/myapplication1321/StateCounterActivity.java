package com.example.myapplication1321;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionEvent;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.ActivityTransitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static java.lang.Thread.sleep;

public class StateCounterActivity extends AppCompatActivity {
    SharedPreferences sharedpreferences ;
    public static final String MyPREFERENCES = "Counter" ;
    private SharedPreferences.Editor editor;
    public TextView textCounter;
    public boolean loop;
    private List<ActivityTransition> transitions;
    private ActivityTransitionRequest request;
    private PendingIntent myPendingIntent;
    private Button counterReset;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_counter);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        loop=true;
        setComponent();
        setTransitions();

        request = new ActivityTransitionRequest(transitions);

        Intent intent = new Intent(StateCounterActivity.this, BroadcastReceiverClass.class);
        intent.setAction("com.example.myapplication1321.StateCounterActivity");
        myPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Task<Void> task = ActivityRecognition.getClient(this)
                .requestActivityUpdates(3000,
                        myPendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        System.out.println("Transitions Api was successfully registered.");
                    }
                }
        );

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("Transitions Api could not be registered:"+e);
                    }
                }
        );


        StateCounterActivity.AsyncTaskCounter asyncTask=new StateCounterActivity.AsyncTaskCounter();
        asyncTask.execute("aaa");

    }

    private void setComponent() {
        textCounter = (TextView) findViewById(R.id.counterCounter);
        counterReset = (Button) findViewById(R.id.counterReset);

        counterReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putInt("count", 0);
                editor.putString("last_type","Unknown");
                editor.apply();
            }
        });
    }

    private void setTransitions() {
        transitions = new ArrayList<>();
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.IN_VEHICLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.IN_VEHICLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_BICYCLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_BICYCLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.RUNNING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.STILL)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());
        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());
    }
    public void deregisterHandler() {
        Task<Void> task = ActivityRecognition.getClient(getApplicationContext())
                .removeActivityTransitionUpdates(myPendingIntent);
        task.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                myPendingIntent.cancel();
                System.out.println("Transitions Api was successfully deregister.");
            }
        });

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("Transitions Api could not be deregister:"+e);
                    }
                }
        );
    }
    private class AsyncTaskCounter extends AsyncTask<String, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            while(loop){
                try{
                    sleep(1000);
                    publishProgress(sharedpreferences.getInt("count",0 ));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            return 1;
        }
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            textCounter.setText(progress[0]+"");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        loop=false;
        deregisterHandler();
    }
}

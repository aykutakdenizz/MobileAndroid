package com.example.myapplication1321;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import static java.lang.Thread.sleep;

public class BroadcastReceiverClass extends BroadcastReceiver {
    Context thisContext;
    MediaPlayer mediaPlayer;
    private String filePath = "Log";
    private File currentFile ;
    SharedPreferences sharedpreferences ;
    public static final String MyPREFERENCES = "Counter" ;
    private SharedPreferences.Editor editor;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getAction();
        System.out.println("On bradcastReceiver :"+state+ " arrived.");

        if (state.equals("android.intent.action.PHONE_STATE")) {
            currentFile = new File(context.getFilesDir().toString(),filePath);
            if(!currentFile.isDirectory()){
                currentFile.mkdir();
            }

            if (intent.getExtras().getString(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                String savedNumber;
                savedNumber = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();

                try{
                    File file = new File(currentFile.getPath(),"call.txt");
                    FileOutputStream fOut = new FileOutputStream(file,true);
                    fOut.write((formatter.format(date)+ " IN COMING CALL: " + savedNumber+"\n").getBytes());
                    fOut.close();
                    Toast.makeText(context, "IN COMING:" + savedNumber, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "Error occur", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            currentFile = new File(context.getFilesDir().toString(),filePath);
            if(!currentFile.isDirectory()){
                currentFile.mkdir();
            }

            Bundle bundle = intent.getExtras();
            String number = getSMSNumber(bundle);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date date = new Date();

            try{
                File file = new File(currentFile.getPath(),"message.txt");
                FileOutputStream fOut = new FileOutputStream(file,true);
                fOut.write(((formatter.format(date)+ " IN COMING SMS: " + number+"\n").getBytes()));
                fOut.close();
                Toast.makeText(context, "IN SMS:"+number, Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(context, "Error occur", Toast.LENGTH_SHORT).show();
            }
        }
        else if(state.equals("com.example.myapplication1321.AlarmActivity")){
            thisContext=context;
            Toast.makeText(context, "--ALARM--", Toast.LENGTH_LONG).show();
            thread.start();
        }
        else if(state.equals("com.example.myapplication1321.StateCounterActivity")){
            if (ActivityRecognitionResult.hasResult(intent)) {
                sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                editor = sharedpreferences.edit();
                ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
                ArrayList<DetectedActivity> detectedActivities = (ArrayList) result.getProbableActivities();
                for (DetectedActivity e:detectedActivities) {
                    System.out.println(getTypeString(e.getType()));

                    if(getTypeString(e.getType()).equals("Still") && !sharedpreferences.getString("last_type","Unknown").equals("Still")){
                        editor.putInt("count", sharedpreferences.getInt("count",0 )+1);
                        editor.apply();
                        System.out.println("count arttı");
                    }

                    editor.putString("last_type",getTypeString(e.getType()));
                    editor.apply();
                    Toast.makeText(context, getTypeString(e.getType()), Toast.LENGTH_LONG).show();
                }

            }
        }
    }


    private String getSMSNumber(Bundle bundle){
        Object[] pdus = (Object[]) bundle.get("pdus");
        SmsMessage[] msgs = new SmsMessage[pdus.length];
        String number = "";
        for (int i=0; i<msgs.length; i++)
        {
            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i], "3gpp" );
            number += msgs[i].getDisplayOriginatingAddress();
        }
        return number;
    }
    private String getTypeString(int id){
        switch(id){
            case 0:
                return "Vehicle";
            case 1:
                return "Bicycle";
            case 2:
                return "On Foot";
            case 3:
                return "Still";
            case 4:
                return "Unknown";
            case 5:
                return "Tilting";
            case 7:
                return "Walking";
            case 8:
                return "Running";
        }
        return "No Data";
    }
    public Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                mediaPlayer = MediaPlayer.create(thisContext, R.raw.alarm);
                mediaPlayer.start();
                try {
                    sleep(3200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mediaPlayer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}

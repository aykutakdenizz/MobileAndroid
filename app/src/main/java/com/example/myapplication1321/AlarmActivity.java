package com.example.myapplication1321;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmActivity extends Activity {
    Button alarmPickerButton, alarmButtonOn, alarmButtonOff;
    EditText alarmMinute, alarmHour;
    AlarmManager alarmManager;
    Intent intent;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        tanimla();
        setClickers();

    }

    private void tanimla() {
        alarmButtonOn = (Button) findViewById(R.id.AlarmButtonOn);
        alarmButtonOff = (Button) findViewById(R.id.AlarmButtonOff);
        alarmPickerButton = (Button) findViewById(R.id.Alarmpicker);
        alarmHour = (EditText) findViewById(R.id.AlarmHour);
        alarmMinute = (EditText) findViewById(R.id.alarmMinute);
        intent = new Intent(getApplicationContext(), BroadcastReceiverClass.class);
    }

    private void setClickers() {
        alarmPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        alarmHour.setText(selectedHour + "");
                        alarmMinute.setText(selectedMinute + "");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        alarmButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour = -1, minute = -1;
                try {
                    hour = Integer.parseInt(alarmHour.getText().toString());
                    minute = Integer.parseInt(alarmMinute.getText().toString());
                    if ((hour < 24 && hour >= 0) && (minute < 60 && minute >= 0)) {
                        Toast.makeText(getApplicationContext(), "Alarm set:" + hour + "." + minute, Toast.LENGTH_SHORT).show();
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        intent.setAction("com.example.myapplication1321.AlarmActivity");
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid hour or minute", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Invalid hour or minute", Toast.LENGTH_SHORT).show();
                }
            }

        });
        alarmButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        intent, PendingIntent.FLAG_NO_CREATE);
                if (pIntent != null) {
                    pIntent.cancel();
                    Toast.makeText(getApplicationContext(), "Canceled alarm", Toast.LENGTH_SHORT).show();
                    System.out.println("ALARMI IPTAL ETTIM");
                }
            }
        });
    }
}

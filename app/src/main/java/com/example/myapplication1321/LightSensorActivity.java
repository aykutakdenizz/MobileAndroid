package com.example.myapplication1321;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class LightSensorActivity extends AppCompatActivity implements SensorEventListener {
    private int waitingTime=5000;
    private SensorManager sensorManager;
    private Sensor lightSensor,accSensor;
    private TextView sensorListTextView;
    private long oldTime,nowTime;
    private float oldX=0,oldY=0,oldZ=(float)9.81,threshold=(float)(0.1);
    private LightSensorActivity thisClass=this;
    private boolean loopFlag=true ,threadExit=false;

    public Thread thread = new Thread() {
        @Override
        public void run() {
            try {
                while(loopFlag) {
                    sleep(500);
                    if(System.currentTimeMillis()-oldTime>waitingTime){
                        thisClass.finishAffinity();
                        loopFlag=(false);
                        threadExit=true;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_sensor);

        sensorListTextView = (TextView) findViewById(R.id.sensorListTextView);
        sensorListTextView.setText("NO SENSOR");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList= sensorManager.getSensorList(Sensor.TYPE_ALL);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        accSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        String sensorListString = "SENSORS";
        for (int i = 0; i < sensorList.size(); i++) {
            sensorListString=sensorListString+("\n" +(i+1)+". sensor is:"+sensorList.get(i).getName() );
        }

        sensorListTextView.setText(sensorListString);

        oldTime=System.currentTimeMillis();
        nowTime=System.currentTimeMillis();
        thread.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            float lux = event.values[0];
            int colorInt = (int)(255*lux/event.sensor.getMaximumRange());
            sensorListTextView.setBackgroundColor(Color.rgb(colorInt,colorInt,colorInt));

            //When text color is (255-colorInt), can not see text so, I check value
            if(colorInt<128){
                sensorListTextView.setTextColor(Color.rgb(255,255,255));
            }else{
                sensorListTextView.setTextColor(Color.rgb(0,0,0));
            }

        }
       if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
           float accX = event.values[0];
           float accY = event.values[1];
           float accZ = event.values[2];
           if(!(Math.abs(accX-oldX)<threshold && Math.abs(accY-oldY)<threshold && Math.abs(accZ-oldZ)<threshold)){
               oldTime=System.currentTimeMillis();
               oldX=accX;
               oldY=accY;
               oldZ=accZ;
           }
       }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this,lightSensor,sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,accSensor,sensorManager.SENSOR_DELAY_NORMAL);
    }
    public void threadExitNotification(){
        Toast.makeText(thisClass.getApplicationContext(),"No movement while 5 second, App closing !",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(this,lightSensor);
        sensorManager.unregisterListener(this,accSensor);
        if(threadExit){
            threadExitNotification();
        }
        loopFlag=false;
        thread=null;
        super.onDestroy();
    }
}

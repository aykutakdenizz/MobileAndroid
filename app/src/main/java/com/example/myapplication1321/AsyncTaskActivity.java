package com.example.myapplication1321;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.Thread.sleep;

public class AsyncTaskActivity extends AppCompatActivity {
    ImageView imageView= null;
    ProgressDialog processDialog;
    int progressInt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);


        Button button = (Button) findViewById(R.id.asyncTaskButton);
        imageView = (ImageView) findViewById(R.id.asyncTaskImage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskExample asyncTask=new AsyncTaskExample();
                asyncTask.execute("aaa");
            }
        });
    }

    private class AsyncTaskExample extends AsyncTask<String, Integer, Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            processDialog = new ProgressDialog(AsyncTaskActivity.this);
            processDialog.setMessage("Starting ...");
            processDialog.setIndeterminate(true);
            processDialog.setCancelable(false);
            processDialog.setMax(100);
            processDialog.show();
        }
        @Override
        protected Integer doInBackground(String... strings) {
            while(progressInt<100){
                Random r=new Random();
                progressInt = progressInt + r.nextInt(10);
                if(progressInt>=100){
                    publishProgress(100);
                }
                else{
                    publishProgress(progressInt);
                }
                try {
                    sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return new Integer(100);
        }
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            processDialog.setProgress(progress[0]);
            processDialog.setMessage("Progress:"+progress[0]+"%");
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if(integer >= 100) {
                processDialog.hide();
                imageView.setImageResource(R.drawable.avatar1);
            }else {
                processDialog.show();
            }
        }
    }
}

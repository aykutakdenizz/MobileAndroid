package com.example.myapplication1321;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "UserDetail" ;
    private int wrongLogin ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText uname = (EditText) findViewById(R.id.editTextUserName);
        final EditText passwd = (EditText) findViewById(R.id.editTextPassword);

        final Button loginbutton = (Button) findViewById(R.id.buttonLogin);

        this.getPermission();

        this.wrongLogin=0;
        final User user= new User("aykut","123");
        loginbutton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v){
                if(user.checkPassword(uname.getText().toString(),passwd.getText().toString())){
                    Toast.makeText(getApplicationContext(),"SUCCESS",Toast.LENGTH_SHORT).show();

                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();

                    String username = uname.getText().toString();
                    editor.putString(("username"), username);
                    editor.apply();

                    Intent helloAndroidIntent = new Intent(MainActivity.this,AraActivity.class);
                    startActivity(helloAndroidIntent);
                }
                else{
                    wrongLogin= wrongLogin+1;
                    uname.setText("");
                    passwd.setText("");
                    Toast.makeText(getApplicationContext(),"WRONG NAME OR PASSWORD",Toast.LENGTH_SHORT).show();
                }
                if(wrongLogin >2){
                    finish();
                    System.exit(0);
                }
            }

        });
    }
    private void getPermission(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1);
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}

package com.example.myapplication1321;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class UserDetailActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "UserDetail" ;
    private String[] languageSpinner = {"Türkçe","English","Espanol","Italiano","Deutsch"};
    private String[] appThemeSpinner = {"Light","Dark"};
    private SharedPreferences.Editor editor;
    private String keyUsername ;
    private EditText userUsername,userAge,userWeight,userHeight ;
    private RadioGroup userGender;
    private Spinner userLanguage,userAppTheme;
    private Button updateButton,backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        setComponents();

        ArrayAdapter arrayAdapterLanguage = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,languageSpinner);
        ArrayAdapter arrayAdapterAppTheme = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,appThemeSpinner);

        userLanguage.setAdapter(arrayAdapterLanguage);
        userAppTheme.setAdapter(arrayAdapterAppTheme);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        setValues();
        setClickListeners();

    }

    private void setComponents(){
        userUsername = (EditText) findViewById(R.id.detailEditTextUserName);
        userGender = (RadioGroup) findViewById(R.id.detailRadioGroupGender);
        userLanguage = (Spinner) findViewById(R.id.detailSpinnerLanguage);
        userAppTheme = (Spinner) findViewById(R.id.detailSpinnerAppTheme);
        userAge = (EditText) findViewById(R.id.detailEditTextAge);
        userWeight = (EditText) findViewById(R.id.detailEditTextWeight);
        userHeight = (EditText) findViewById(R.id.detailEditTextHeight);
        updateButton = (Button) findViewById(R.id.updateButton);
        backButton = (Button) findViewById(R.id.userDetailBackButton1);
    }
    private void setClickListeners(){
        userLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        userAppTheme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userAgeString = userAge.getText().toString();
                String userWeightString = userWeight.getText().toString();
                String userHeightString = userHeight.getText().toString();
                int userGenderId = userGender.getCheckedRadioButtonId();
                String userLanguageString = userLanguage.getSelectedItem().toString();
                String userAppThemeString = userAppTheme.getSelectedItem().toString();

                editor.putString(keyUsername+"_userAppTheme", userAppThemeString);
                editor.putString(keyUsername+"_userLanguage", userLanguageString);
                editor.putString(keyUsername+"_userAge", userAgeString);
                editor.putString(keyUsername+"_userWeight", userWeightString);
                editor.putString(keyUsername+"_userHeight", userHeightString);
                editor.putInt(keyUsername+"_userGender", userGenderId);
                editor.apply();

                if(userAppThemeString.equals("Dark")){
                    setTheme(R.style.Theme_AppCompat_DayNight);
                }
                else{
                    setTheme(R.style.AppTheme);
                }
                Toast.makeText(getApplicationContext(),"SAVED",Toast.LENGTH_SHORT).show();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setValues(){
        keyUsername =sharedpreferences.getString("username","USERNAME_DEFAULT" );
        userUsername.setText(sharedpreferences.getString("username","USERNAME_DEFAULT" ));
        userGender.check(sharedpreferences.getInt(keyUsername+"_userGender",R.id.detailRadioButtonMen));
        userAge.setText(sharedpreferences.getString(keyUsername+"_userAge","0" ));
        userWeight.setText(sharedpreferences.getString(keyUsername+"_userWeight","0" ));
        userHeight.setText(sharedpreferences.getString(keyUsername+"_userHeight","0" ));
        userAppTheme.setSelection(findIndexOfArray(appThemeSpinner,sharedpreferences.getString(keyUsername+"_userAppTheme","NO_VALUE" )));
        userLanguage.setSelection(findIndexOfArray(languageSpinner,sharedpreferences.getString(keyUsername+"_userLanguage","NO_VALUE" )));

    }
    public int findIndexOfArray(String[] array,String wantedString){
        int i=0;
        for (String str:array) {
            if (str.equals(wantedString)){
                return i;
            }
            i++;
        }
        return 0;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
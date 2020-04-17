package com.example.myapplication1321;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MailActivity extends AppCompatActivity {
    public Uri uri;
    private EditText textTo ,textSubject,textMessage;
    private Button buttonAttachment,sendButton,backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_mail);

        setComponents();
        setClickListeners();

    }
    private void setComponents(){
        this.textTo = (EditText) findViewById(R.id.mailTo);
        this.textSubject = (EditText) findViewById(R.id.mailSubject);
        this.buttonAttachment = (Button) findViewById(R.id.mailAttachment);
        this.textMessage = (EditText) findViewById(R.id.mailMessage);
        this.sendButton = (Button) findViewById(R.id.mailSendButton);
        this.backButton = (Button) findViewById(R.id.mailBackButton);
    }

    private void setClickListeners(){
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent mailIntent = new Intent(Intent.ACTION_SEND);
                    mailIntent.setType("plain/text");
                    mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{textTo.getText().toString()});
                    mailIntent.putExtra(Intent.EXTRA_SUBJECT, textSubject.getText().toString());
                    mailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    mailIntent.putExtra(Intent.EXTRA_TEXT, textMessage.getText().toString());
                    startActivity(Intent.createChooser(mailIntent, "Choose App"));
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "ERROR OCCURRED", Toast.LENGTH_SHORT).show();
                    System.out.println(e);
                }
            }
        });
        buttonAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent attachmentIntent = new Intent(Intent.ACTION_GET_CONTENT);
                attachmentIntent.setType("*/*");
                attachmentIntent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(attachmentIntent, "Choose File"), 100);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 100:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    Uri res_uri = data.getData();
                    this.uri = res_uri;
                    Toast.makeText(getApplicationContext(), "File Added", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

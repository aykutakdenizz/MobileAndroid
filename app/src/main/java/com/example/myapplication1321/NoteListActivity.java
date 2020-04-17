package com.example.myapplication1321;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class NoteListActivity extends AppCompatActivity {
    private String filePath = "notes";
    private String[] noteList ={};
    private File currentFile ;
    private Spinner noteSpinner ;
    private Button noteSaveButton,noteShowButton,noteDeleteButton,noteNewNoteButton,backButton;
    private LinearLayout showComponent ;
    private EditText noteNameText,noteText;
    private ArrayAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        currentFile = new File(getApplicationContext().getFilesDir().toString(),filePath);
        if(!currentFile.isDirectory()){
            currentFile.mkdir();
        }

        setComponents();

        setClickListeners();

        showComponent.setVisibility(View.INVISIBLE);
        setList();

    }

    private void setComponents(){
        noteSpinner = (Spinner) findViewById(R.id.noteSpinner);
        noteSaveButton = (Button) findViewById(R.id.noteSaveButton);
        noteShowButton = (Button) findViewById(R.id.noteShowButton);
        noteDeleteButton = (Button) findViewById(R.id.noteDeleteButton);
        noteNewNoteButton = (Button) findViewById(R.id.noteNewNoteButton);
        backButton = (Button) findViewById(R.id.noteBackButton);
        showComponent = (LinearLayout) findViewById(R.id.noteAddLayout);
        noteNameText = (EditText) findViewById(R.id.noteNameText);
        noteText = (EditText) findViewById(R.id.noteEditTextNote);
    }
    public void setList(){
        File[] files = currentFile.listFiles();
        ArrayList<String> a =new ArrayList<>();
        for(File f:files){
            a.add(f.getName());
        }
        this.noteList= a.toArray(new String[a.size()]);
        dataAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item,noteList);
        noteSpinner.setAdapter(dataAdapter);
    }
    private void setClickListeners(){
        noteSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(currentFile.getPath(),noteNameText.getText().toString()+".txt");
                    FileOutputStream fOut = new FileOutputStream(file);
                    fOut.write(noteText.getText().toString().getBytes());
                    fOut.close();

                    Toast.makeText(getBaseContext(), "File saved", Toast.LENGTH_SHORT).show();
                    showComponent.setVisibility(View.INVISIBLE);
                    noteText.setText("");
                    noteNameText.setText("");
                    setList();

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        noteShowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = new File(currentFile.getPath(),noteSpinner.getSelectedItem().toString());
                    FileInputStream fin = new FileInputStream(file);
                    int c;
                    String notetext = "";
                    while ((c = fin.read()) != -1) {
                        notetext = notetext + Character.toString((char) c);
                    }
                    fin.close();
                    showComponent.setVisibility(View.VISIBLE);

                    String name = noteSpinner.getSelectedItem().toString().substring(0,noteSpinner.getSelectedItem().toString().length()-4);
                    noteNameText.setText(name);
                    noteNameText.setClickable(false);
                    noteNameText.setInputType(InputType.TYPE_NULL);
                    noteText.setText(notetext);
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        noteDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String dir = currentFile.getPath();
                    File file = new File(dir, noteSpinner.getSelectedItem().toString());
                    boolean deleted = file.delete();

                    showComponent.setVisibility(View.INVISIBLE);
                    noteText.setText("");
                    noteNameText.setText("");
                    Toast.makeText(getBaseContext(), "Deleted:"+deleted, Toast.LENGTH_SHORT).show();
                    setList();
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        noteNewNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteNameText.setClickable(true);
                noteNameText.setInputType(InputType.TYPE_CLASS_TEXT);
                noteText.setText("");
                noteNameText.setText("");
                showComponent.setVisibility(View.VISIBLE);

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
    protected void onDestroy() {
        super.onDestroy();
    }
}

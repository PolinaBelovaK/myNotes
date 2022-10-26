package com.example.notes;

import static com.example.notes.R.color.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import Models.Notes;

/**
 * This class is an adapter and allows us to correctly display the created notes on the main screen
 */
public class NotesTakenActivity extends AppCompatActivity {

    EditText editText_title, editText_notes;
    FloatingActionButton imageView_save;
    Notes notes;
    RelativeLayout card;
    String color;
    ImageButton backBtn;
    boolean isOldNote = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taken);

        backBtn = findViewById(R.id.backBtn); //back to main screen button
        editText_title = findViewById(R.id.editText_title); //field with title
        editText_notes = findViewById(R.id.editText_notes); //field with description
        card = findViewById(R.id.card); //all note
        notes = new Notes(); //model

        //update old note start
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_notes");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        //update old note end

        imageView_save = findViewById(R.id.imageView_save);//save button
        //save button logic start
        imageView_save.setOnClickListener(view -> {
            String title = editText_title.getText().toString();
            String note = editText_notes.getText().toString();
            //checking for empty text
            if (note.isEmpty()) {
                Toast.makeText(NotesTakenActivity.this, "Введите текст", Toast.LENGTH_SHORT).show();
                return;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss"); //insert the date according to the selected pattern
            Date date = new Date();

            //Checking if a note has already been created
            if (!isOldNote) {
                notes = new Notes();
            }
            notes.setTitle(title);
            notes.setNotes(note);
            notes.setDate(dateFormat.format(date));

            //Transferring all assignments to activity.RESULT_OK and back to main screen
            Intent intent = new Intent();
            intent.putExtra("note", notes);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        //back button realization
        backBtn.setOnClickListener(view -> {
            Intent intent = new Intent(NotesTakenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
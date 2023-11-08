package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity {

    EditText ThemeEditText;
    EditText NoteEditText;
    EditText NoteEditText2;
    EditText NoteEditText3;
    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        ThemeEditText = findViewById(R.id.themeEditText);
        NoteEditText = findViewById(R.id.noteEditText);
        NoteEditText2 = findViewById(R.id.noteEditText2);
        NoteEditText3 = findViewById(R.id.noteEditText3);
        Intent fromMainActivityIntent = getIntent();

        ThemeEditText.setText(fromMainActivityIntent.getExtras().getString("namesite"));
        NoteEditText.setText(fromMainActivityIntent.getExtras().getString("urlsite"));
        NoteEditText2.setText(fromMainActivityIntent.getExtras().getString("login"));
        NoteEditText3.setText(fromMainActivityIntent.getExtras().getString("passord"));
        Position = fromMainActivityIntent.getIntExtra(MainActivity.KEY_POSITION,-1);

        if(Position == -1)
        {
            Log.d("Note activity","Invalid position");
        }


    }

    public void OnBackButtonClick(View view)
    {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("namesite",ThemeEditText.getText().toString());
        returnIntent.putExtra("urlsite",NoteEditText.getText().toString());
        returnIntent.putExtra("login",NoteEditText2.getText().toString());
        returnIntent.putExtra("passord",NoteEditText3.getText().toString());
        returnIntent.putExtra(MainActivity.KEY_POSITION,Position);
        setResult(RESULT_OK,returnIntent);
        finish();

    }
}
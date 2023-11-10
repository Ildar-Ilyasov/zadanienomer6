package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity {

    EditText NameEditText;
    EditText UrlEditText;
    EditText LoginEditText;
    EditText PasswordEditText;
    int Position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        NameEditText = findViewById(R.id.nameEditText);
        UrlEditText = findViewById(R.id.urlEditText);
        LoginEditText = findViewById(R.id.loginEditText);
        PasswordEditText = findViewById(R.id.passwordEditText);
        Intent fromMainActivityIntent = getIntent();

        NameEditText.setText(fromMainActivityIntent.getExtras().getString("namesite"));
        UrlEditText.setText(fromMainActivityIntent.getExtras().getString("urlsite"));
        LoginEditText.setText(fromMainActivityIntent.getExtras().getString("login"));
        PasswordEditText.setText(fromMainActivityIntent.getExtras().getString("password"));
        Position = fromMainActivityIntent.getIntExtra(MainActivity.KEY_POSITION,-1);
    }

    public void OnBackButtonClick(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("namesite",NameEditText.getText().toString());
        returnIntent.putExtra("urlsite",UrlEditText.getText().toString());
        returnIntent.putExtra("login",LoginEditText.getText().toString());
        returnIntent.putExtra("password",PasswordEditText.getText().toString());
        returnIntent.putExtra(MainActivity.KEY_POSITION,Position);
        setResult(RESULT_OK,returnIntent);
        finish();

    }
}
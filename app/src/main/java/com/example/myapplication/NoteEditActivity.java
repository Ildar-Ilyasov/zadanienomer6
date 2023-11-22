package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class NoteEditActivity extends AppCompatActivity {
    int Position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);
        Intent fromMainActivityIntent = getIntent();

        String Notename = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_NAME);
        String Noteurl = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_URL);
        String Notelogin = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_LOGIN);
        String Notepassword = fromMainActivityIntent.getExtras().getString(MainActivity.KEY_PASSWORD);

        //создание фрагмента
        NoteEditFragment fragment = new NoteEditFragment();

        Bundle bundle = new Bundle();
        bundle.putString(MainActivity.KEY_NAME, Notename);
        bundle.putString(MainActivity.KEY_URL, Noteurl);
        bundle.putString(MainActivity.KEY_LOGIN, Notelogin);
        bundle.putString(MainActivity.KEY_PASSWORD, Notepassword);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();

        Position = fromMainActivityIntent.getIntExtra(MainActivity.KEY_POSITION, -1);
    }

    public void OnClickButtonBack(String name,String url,String login,String password) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MainActivity.KEY_NAME, name);
        returnIntent.putExtra(MainActivity.KEY_URL, url);
        returnIntent.putExtra(MainActivity.KEY_LOGIN, login);
        returnIntent.putExtra(MainActivity.KEY_PASSWORD, password);
        returnIntent.putExtra(MainActivity.KEY_POSITION, Position);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
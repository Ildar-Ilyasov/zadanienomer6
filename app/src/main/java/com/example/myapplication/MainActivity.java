package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    final public static String KEY_POSITION = "position";

    ListView ThemesListView;

    SimpleCursorAdapter noteAdapter;
    DataBaseAccessor db;

    // создание launcher для получения данных из дочерней активити
    ActivityResultLauncher<Intent> NotesLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        //получить данные
                        Intent returnedIntent = result.getData();
                        int id = returnedIntent.getIntExtra(KEY_POSITION,-1);
                        String name = returnedIntent.getStringExtra("namesite");
                        String url = returnedIntent.getStringExtra("urlsite");
                        String login = returnedIntent.getStringExtra("login");
                        String password = returnedIntent.getStringExtra("password");
                        //обновить БД и интерфейс
                        db.updateNote(id,name,url,login,password);
                        noteAdapter = AdapterUpdate();
                    }
                    else
                    {
                        Log.d("MainActivity" ,"Invalid note activity result");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создать аксессор к бд
        db = new DataBaseAccessor(this);

        setContentView(R.layout.activity_main);
        ThemesListView = findViewById(R.id.ListView);

        noteAdapter = AdapterUpdate();
        Intent NoteIntent = new Intent(this, NoteEditActivity.class);

        // обработка клика по listView
        ThemesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                //Добыть данные из адаптера
                String name = ((Cursor) noteAdapter.getItem(position)).getString(1);
                String url = ((Cursor) noteAdapter.getItem(position)).getString(2);
                String login = ((Cursor) noteAdapter.getItem(position)).getString(3);
                String password = ((Cursor) noteAdapter.getItem(position)).getString(4);

                Toast.makeText(MainActivity.this, login, Toast.LENGTH_SHORT).show();
                //отправить данные в дочернюю акливити
                NoteIntent.putExtra("namesite", name);
                NoteIntent.putExtra("urlsite", url);
                NoteIntent.putExtra("login", login);
                NoteIntent.putExtra("password", password);


                //id - идентификатор записи в БД
                //без приведения к int перидется и получать long а я не хотел переписывать дочернюю активити
                NoteIntent.putExtra(KEY_POSITION, (int)id);
                //запустить дочернюю активити
                NotesLauncher.launch(NoteIntent);
            }
        });
    }

    private SimpleCursorAdapter AdapterUpdate() {
        // получить адаптер из класса
        SimpleCursorAdapter adapter = db.getCursorAdapter(this,
                android.R.layout.two_line_list_item, // Разметка одного элемента ListView
                new int[]{android.R.id.text1,android.R.id.text2}); // текст этого элемента

        // установить адаптер в listview
        ThemesListView.setAdapter(adapter);
        return adapter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // закрыть БД
        db.close();
    }
}
package com.example.myapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    final public static String KEY_WEBSITE = "website";
    final public static String KEY_URL = "url";
    final public static String KEY_LOGIN = "login";
    final public static String KEY_PASSWORD = "password";
    final public static String KEY_POSITION = "position";
    private  static final int MY_PERMISSIONS_REQUEST_INTERNET = 777;
    private  static final String SERVICE_ADDRESS = "http://37.77.105.18/api/PasswordManager";

    ListView ThemesListView;
    ArrayAdapter<String> noteAdapter;
    //DataBaseAccessor db;

    // создание launcher для получения данных из дочерней активити
    ActivityResultLauncher<Intent> NotesLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // все ли хорошо при получении данных из дочерней активити?
                    if(result.getResultCode() == Activity.RESULT_OK)
                    {
                        //получить данные
                        Intent returnedIntent = result.getData();
                        String web = returnedIntent.getStringExtra(KEY_WEBSITE);
                        String url = returnedIntent.getStringExtra(KEY_URL);
                        String login = returnedIntent.getStringExtra(KEY_LOGIN);
                        String password = returnedIntent.getStringExtra(KEY_PASSWORD);
                    }

                }
            });

//

    ArrayList<websiten> websites;
    ArrayAdapter<String> websitesAdapter;
    ServerAccessor serverAccessor = new ServerAccessor(SERVICE_ADDRESS);
    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    Intent NoteIntent;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // создать аксессор к бд
        setContentView(R.layout.activity_main);
        ThemesListView = findViewById(R.id.ListView);


        websitesAdapter = AdapterUpdate(new ArrayList<websiten>());
        NoteIntent = new Intent(this, NoteEditActivity.class);
        Button backButton = findViewById(R.id.button3);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NoteIntent.putExtra(KEY_WEBSITE, "none");
                NoteIntent.putExtra(KEY_URL, "");
                NoteIntent.putExtra(KEY_LOGIN, "");
                NoteIntent.putExtra(KEY_PASSWORD, "");
                //запустить дочернюю активити
                NotesLauncher.launch(NoteIntent);
            }
        });
           // обработка клика по listView
        ThemesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                System.out.println("----------------------------------");
                serverAccessor.getObject(websites.get(position));

                Map<String, String> data = serverAccessor.getObject(websites.get(position));
                String web = data.get("website");
                String url = data.get("url");
                String login = data.get("login");
                String password = data.get("password");

                System.out.println(web);
                System.out.println(url);
                System.out.println(login);
                System.out.println(password);

                NoteIntent.putExtra(KEY_WEBSITE, web);
                NoteIntent.putExtra(KEY_URL, url);
                NoteIntent.putExtra(KEY_LOGIN, login);
                NoteIntent.putExtra(KEY_PASSWORD, password);



                //запустить дочернюю активити
                NotesLauncher.launch(NoteIntent);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            // Разрешение не предоставлено, запросить его у пользователя
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_INTERNET);
        }

        //Запуск фоновой задачи
        ProgressTask progressTask = new ProgressTask();
        executorService.submit(progressTask);
    }

    private ArrayAdapter<String> AdapterUpdate(ArrayList<websiten> list) {

        ArrayList<String> stringList = serverAccessor.getStringListFromNoteList(list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,stringList);
        // установить адаптер в listview
        ThemesListView.setAdapter(adapter);
        return adapter;
    }


    //  // выбор режима отображеня
    class ProgressTask implements Runnable {
        String connectionError = null;

        @Override
        public void run() {
            try {
                // выполнение в фоне
                websites = serverAccessor.getData();

                // Обновление UI осуществляется в основном потоке
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (connectionError == null) {
                            websitesAdapter = AdapterUpdate(websites);
                        } else {
                            //проблемы с интернетом
                        }
                    }
                });

            } catch (Exception ex) {
                connectionError = ex.getMessage();
            }
        }
    }
}
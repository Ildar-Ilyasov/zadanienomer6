package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

//Класс для доступа к БД
public class DataBaseAccessor extends SQLiteOpenHelper
{
    // Основные данные базы
    private static final String DATABASE_NAME = "db7.db";
    private static final int DB_VERSION = 4;

    // таблицы
    private static final String TABLE_MANAGERPASSWORDS = "NOTE";

    // столбцы таблицы Note
    private static final String COLUMN_ID = "_id";//Обязательно с подчеркиванием
    private static final String COLUMN_NAME = "namesite";
    private static final String COLUMN_URL = "url";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "parol";

    public DataBaseAccessor(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создать таблицу
        db.execSQL("CREATE TABLE " + TABLE_MANAGERPASSWORDS + "("
                                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                                + COLUMN_NAME + " TEXT,"
                                + COLUMN_URL + " TEXT,"
                                + COLUMN_LOGIN + " TEXT,"
                                + COLUMN_PASSWORD + " TEXT);");

        // Добавить пару записей в таблицу
        db.execSQL("INSERT INTO " + TABLE_MANAGERPASSWORDS + "(" + COLUMN_NAME + ", "+ COLUMN_URL + ", "+ COLUMN_LOGIN + ", "+ COLUMN_PASSWORD +") values('Google','https://www.google.ru/','1234','21IT17')");
        db.execSQL("INSERT INTO " + TABLE_MANAGERPASSWORDS + "(" + COLUMN_NAME + ", "+ COLUMN_URL + ", "+ COLUMN_LOGIN + ", "+ COLUMN_PASSWORD +") values('Яндекс','https://ya.ru/','65393','WcsPQLdcs')");
        db.execSQL("INSERT INTO " + TABLE_MANAGERPASSWORDS + "(" + COLUMN_NAME + ", "+ COLUMN_URL + ", "+ COLUMN_LOGIN + ", "+ COLUMN_PASSWORD +") values('ВК','https://vk.com/','Ильдар','Egthhdrgf')");
        db.execSQL("INSERT INTO " + TABLE_MANAGERPASSWORDS + "(" + COLUMN_NAME + ", "+ COLUMN_URL + ", "+ COLUMN_LOGIN + ", "+ COLUMN_PASSWORD +") values('Яндекс Диск','https://disk.yandex.com.am/client/disk','76458854','QfbgPOSKCmdsp')");
    }

    public SimpleCursorAdapter getCursorAdapter(Context context,int layout, int[] viewIds)
    {
        // запрос данных для отображения
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_MANAGERPASSWORDS,null);

        // какие столбцы и в каком порядке показывать в listview
        String[] columns = new  String[] {COLUMN_NAME,COLUMN_URL,COLUMN_LOGIN,COLUMN_PASSWORD};

        // создание адаптера
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(context,layout,cursor,columns,viewIds,0);
        return  adapter;
    }


    public void updateNote(int id, String name,String url,String login,String password)
    {
        // выполнить запрос на обновление БД
        getReadableDatabase().execSQL("UPDATE "+ TABLE_MANAGERPASSWORDS
                                        + " SET "
                                        + COLUMN_NAME + "='" + name + "', "
                                        + COLUMN_LOGIN + "='" + login + "', "
                                        + COLUMN_PASSWORD + "='" + password + "', "
                                        + COLUMN_URL + "='" + url + "'"
                                        + " WHERE "
                                        + COLUMN_ID + "=" + id);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try
        {
            //удалить старую таблицу
            db.execSQL("DROP TABLE " + TABLE_MANAGERPASSWORDS);
        }
        catch (Exception exception)
        {

        }
        finally {
            //создать новую и заполнить ее
            onCreate(db);
        }
    }
}

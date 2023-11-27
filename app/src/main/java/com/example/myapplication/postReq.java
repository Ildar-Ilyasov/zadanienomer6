package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class postReq extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        try {

            // Создаем URL-объект с адресом сервера
            URL url = new URL(params[0]);

            // Создаем подключение HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Создаем JSON объект и добавляем в него данные
            JSONObject requestData = new JSONObject();
            try {
                requestData.put("website", params[1]);
                requestData.put("url", params[2]);
                requestData.put("login", params[3]);
                requestData.put("password", params[4]);
                // Добавьте сюда свои данные
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Получаем OutputStream и пишем в него данные
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestData.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            // Получаем ответ от сервера
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Закрываем подключение
            connection.disconnect();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
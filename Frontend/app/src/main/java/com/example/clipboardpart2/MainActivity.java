package com.example.clipboardpart2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String Data="";
        OkHttpClient okHttpClient = new OkHttpClient();

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        Data = (String) item.getText();

        if(Data.length()==0){

            Request requestback = new Request.Builder().url("http://172.18.16.1:5000/get").build();

            okHttpClient.newCall(requestback).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(MainActivity.this, "network not found", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    clipboard.setPrimaryClip(ClipData.newPlainText("Return from remote clipboard",response.body().string()));
                    Toast.makeText(MainActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();

                }
            });

        }
        else {

            RequestBody formbody = new FormBody.Builder().add("clipboard_content", Data).build();

            Request request = new Request.Builder().url("http://172.18.16.1:5000/post").post(formbody).build();

            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(MainActivity.this, "network not found", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    Toast.makeText(MainActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();
                }
            });

            Request requestback = new Request.Builder().url("http://172.18.16.1:5000/get").build();

            okHttpClient.newCall(requestback).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Toast.makeText(MainActivity.this, "network not found", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    clipboard.setPrimaryClip(ClipData.newPlainText("Return from remote clipboard",response.body().string()));
                    Toast.makeText(MainActivity.this, "Copied to Clipboard", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
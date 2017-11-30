package com.example.andrey.justsweethome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ScrollView;

public class LogActivity extends AppCompatActivity implements Transport.ListenerOfTransport  {

    TextView textViewLog;
    Transport transport;
    ScrollView scrollView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);


        transport = new Transport("89.169.58.253", 7777, "user1", "12345");
        transport.addListener(this); // добавляем  активити в список слушателей класса Transport
        textViewLog = (TextView) findViewById(R.id.TextView);
        scrollView = (ScrollView) findViewById(R.id.ScrollView);
        editText = (EditText) findViewById(R.id.EditText);
    }



    public void onClick(View v){

        transport.sendPackage(editText.getText().toString());
        scrollView.fullScroll(View.FOCUS_DOWN);
    }

    @Override
    public void onAcceptingTCPPackage(String str) {
        Log.print("Ответ сервера: " + str);
        textViewLog.append(str + "\n");

    }
}

package com.example.andrey.justsweethome;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.ScrollView;

public class LogActivity extends AppCompatActivity implements Transport.ListenerOfTransport  {

    TextView textViewLog;
    Transport transport;
    NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Отправка пакета на контроллер", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                transport.sendTest();
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        transport = new Transport("89.169.58.253", 7777, "user1", "12345");
        transport.addListener(this); // добавляем  активити в список слушателей класса Transport
        textViewLog = (TextView) findViewById(R.id.LogTextView);
        scrollView = (NestedScrollView) findViewById(R.id.NestedScrollView);
    }




    @Override
    public void onAcceptingTCPPackage(NetworkPackage np) {
        textViewLog.append(getString(np.UIN) + " " + getString(np.responseToUIN)+ " " + np.data);
    }
}

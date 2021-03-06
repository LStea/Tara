package com.example.asus.tara;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.asus.tara.bluetooth.MyBluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter thisAdapter;
    BTObjects bto;
    Activity activity = this;

    private OutputStream outStream;
    private InputStream inStream;
    private static final String message = "!<<01_GET_FAST_*46D9>>!";

    private String weightDisplay;
    private String taraDisplay;
    private Thread thread;
    String test = "wlaczone";
    private Button display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        int weight = 150;
//        String weightDisplay = (weight + " kg");

        display = (Button) findViewById(R.id.weight_display);
        display.setGravity(Gravity.CENTER);
//        display.setText(weightDisplay);
       

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                while (bto.isConnected) {
                    try {
                        sendingMessage();
                        gettingMessage();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }});
//

        int weight_two = 3580;
        String taraDisplay = ("Tara: " + weight_two + " kg");
        TextView display_two = (TextView) findViewById(R.id.tara_display);
        display_two.setGravity(Gravity.CENTER);
        display_two.setText(taraDisplay);

        Button taraButton = (Button) findViewById(R.id.button_left);
        taraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Left button clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button holdButton = (Button) findViewById(R.id.button_right);
        holdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Right button clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void gettingMessage() {
            inStream = bto.inStream;
            Runnable runnable = new GetMessageThread(inStream,
                    bto.btSocket, activity, display);
            thread = new Thread(runnable);
            thread.start();
//    }
    }

    private void sendingMessage() {
            try {
                outStream = bto.outStream;
                byte[] data = message.getBytes();
                outStream.write(data);
                Log.d("THREAD", "MSGSENDING:" + Arrays.toString(data));
//                outStream.flush();
                } catch (IOException e) {
                e.printStackTrace();
            }} ;

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.bluetooth) {
            Intent bluetoothIntent = new Intent(MainActivity.this, MyBluetooth.class);
            startActivity(bluetoothIntent);
        } else if (id == R.id.load) {

        } else if (id == R.id.unload) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (!thisAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

        }
    }
}
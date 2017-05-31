package com.example.asus.tara.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.asus.tara.R;

import java.util.Set;

public class MyBluetooth extends AppCompatActivity {

    private TextView mTextMessage;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BroadcastReceiver broadcastReceiver;
    private String deviceName;
    private String deviceAddress;
    private IntentFilter filter;
    

//        Log.d("DEVICELIST", "Super called for DeviceListFragment onCreate\n");
//        deviceItemList = new ArrayList<DeviceItem>();
//
//        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//
//        if (pairedDevices.size() > 0) {
//            for (BluetoothDevice device : pairedDevices) {
//                DeviceItem newDevice= new DeviceItem(device.getName(),device.getAddress(),"false");
//                deviceItemList.add(newDevice);
//            }
//        }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list_of_devices:
                    mTextMessage.setText(R.string.list_of_devices);

                    return true;
                case R.id.navigation_list_of_paired_devices:
                    mTextMessage.setText(R.string.list_of_paired_devices);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bluetooth);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }
}



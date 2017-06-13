package com.example.asus.tara.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.tara.R;
import com.example.asus.tara.paired_devices.PairedDevices;

import java.util.Set;

public class MyBluetooth extends AppCompatActivity {

    private static final int DISCOVERABLE_DURATION = 60;
    private static final int REQUEST_DISCOVERABLE_BT = 1;
    private TextView mTextMessage;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private BroadcastReceiver broadcastReceiver;
    private String deviceName;
    private String deviceAddress;
    private IntentFilter filter;
    private Switch switchButton;

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
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.root, new PairedDevices())
                            .commit();

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

        switchButton = (Switch) findViewById(R.id.discover_switch_button);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(R.drawable.ic_bluetooth_disabled_black_24dp)
                    .show();
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_BLUETOOTH = 1;
                startActivityForResult(enableBluetooth, REQUEST_BLUETOOTH);
            }
        }
    }

    public void onSwitchClicked(View view) {
        boolean on = ((Switch) view).isChecked();
        Intent discoverableIntent;
        if (on) {
            discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVERABLE_DURATION);

            startActivityForResult(discoverableIntent, REQUEST_DISCOVERABLE_BT);
        } else {
            Toast.makeText(this, "discovering", Toast.LENGTH_SHORT).show();

        }


        final Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address

                Toast.makeText(MyBluetooth.this, deviceName, Toast.LENGTH_SHORT).show();
                Toast.makeText(MyBluetooth.this, deviceHardwareAddress, Toast.LENGTH_SHORT).show();
            }
        }
    }
}





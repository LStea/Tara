package com.example.asus.tara.data;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.List;
import java.util.Set;

/**
 * Created by asus on 08.06.2017.
 */

public class BluetoothHelper {

    public Set<BluetoothDevice> getPairedDevices() {
        return BluetoothAdapter.getDefaultAdapter().getBondedDevices();
    }
}

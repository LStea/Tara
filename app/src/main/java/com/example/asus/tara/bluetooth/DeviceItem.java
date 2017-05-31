package com.example.asus.tara.bluetooth;

import android.bluetooth.BluetoothDevice;

/**
 * Created by asus on 30.05.2017.
 */

public class DeviceItem {

    private String name;
    private String address;

    DeviceItem (String deviceName, String deviceAddress) {
        super();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }


}

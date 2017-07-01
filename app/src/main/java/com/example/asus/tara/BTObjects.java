package com.example.asus.tara;

import android.bluetooth.BluetoothSocket;

import com.example.asus.tara.paired_devices.DevicesAdapter;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by asus on 27.06.2017.
 */

public class BTObjects {

    public static volatile DevicesAdapter adapter;
    public static volatile BluetoothSocket btSocket;
    public static volatile OutputStream outStream;
    public static volatile InputStream inStream;
    public static volatile boolean isConnected = false;
}

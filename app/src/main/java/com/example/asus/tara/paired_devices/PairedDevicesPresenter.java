package com.example.asus.tara.paired_devices;

import android.bluetooth.BluetoothDevice;

import com.example.asus.tara.data.BluetoothHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by asus on 08.06.2017.
 */

public class PairedDevicesPresenter {
    private BluetoothHelper bluetoothHelper;
    private PairedDevices view;
    private List<BluetoothDevice> devicesList = new ArrayList();
    public void bind(PairedDevices view) {
        this.view = view;
        Set<BluetoothDevice> devices = bluetoothHelper.getPairedDevices();

        List<DeviceModel> items = new ArrayList<>();
        for (BluetoothDevice device : devices) {
            DeviceModel model = new DeviceModel();
            model.setName(device.getName());
            model.setMac(device.getAddress());
            items.add(model);
            devicesList.add(device);
        }

        view.setData(items);
    }
    public void unBind() {
        view = null;
    }
    public void release() {

    }
    public List<BluetoothDevice> getDeviceList() {
        return this.devicesList;
    }

    public PairedDevicesPresenter(BluetoothHelper bluetoothHelper) {
        this.bluetoothHelper= bluetoothHelper;

    }
}

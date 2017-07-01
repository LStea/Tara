package com.example.asus.tara.paired_devices;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.tara.BTObjects;
import com.example.asus.tara.R;
import com.example.asus.tara.data.BluetoothHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by asus on 08.06.2017.
 */

public class PairedDevices extends Fragment {

    private PairedDevicesPresenter presenter;
    BTObjects bto;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
//    private RecyclerView recycler;

    private ListView recycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PairedDevicesPresenter(new BluetoothHelper());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paired_devices, container, false);
//        recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler = (ListView) view.findViewById(R.id.recycler);
        recycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                view.setSelected(true);
                BluetoothDevice Device2 = presenter.getDeviceList().get(position);
                try {
                    bto.btSocket = Device2.createRfcommSocketToServiceRecord(MY_UUID);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
// Establish the connection. This will block until it connects.
                try {
                    bto.btSocket.connect();
                    bto.isConnected = true;
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        bto.btSocket.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                }
                try {
                    bto.outStream = bto.btSocket.getOutputStream();
                    bto.inStream = bto.btSocket.getInputStream();
                } catch (IOException e) {
                }

            }
        }
        );
        presenter.bind(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        presenter.unBind();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        presenter.release();
        super.onDetach();
    }

    public void setData(List<DeviceModel> items) {
        bto.adapter = new DevicesAdapter(items);
        ArrayList<String> listItems = new ArrayList<>();

        for (DeviceModel d : items)
            listItems.add(d.getName() + '\n' + d.getMac());

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                listItems);
        recycler.setAdapter(adapter1);
        //recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //recycler.setAdapter(adapter);
    }
}

package com.example.asus.tara.paired_devices;

import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.asus.tara.R;
import com.example.asus.tara.data.BluetoothHelper;

import java.util.List;

/**
 * Created by asus on 08.06.2017.
 */

public class PairedDevices extends Fragment {

    private PairedDevicesPresenter presenter;
    private DevicesAdapter adapter;
    private RecyclerView recycler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PairedDevicesPresenter(new BluetoothHelper());

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.paired_devices, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler);
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
        adapter = new DevicesAdapter(items);

        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(adapter);

    }
}

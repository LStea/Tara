package com.example.asus.tara.paired_devices;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.asus.tara.R;

import java.util.List;

/**
 * Created by asus on 08.06.2017.
 */

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceHolder> {

    private List<DeviceModel> dane;

    DevicesAdapter(List<DeviceModel> dane) {
        this.dane = dane;
    }

//    tworzenie wierszy widokow (z xmla)
    @Override
    public DeviceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_device,
                parent, false);
        DeviceHolder deviceHolder = new DeviceHolder(view);
        return deviceHolder;
    }

//    ladowanie danych
    @Override
    public void onBindViewHolder(DeviceHolder holder, int position) {
        DeviceModel model = dane.get(position);
        holder.setMac(model.getMac());
        holder.setName(model.getName());
    }

//    zwraca liczbe elem
    @Override
    public int getItemCount() {
        if (dane != null) {
            return dane.size();
        }
        return 0;
    }

    public static class DeviceHolder extends RecyclerView.ViewHolder {

        private TextView macTextView;
        private TextView nameTextView;

        public DeviceHolder(View itemView) {
            super(itemView);
            macTextView = (TextView) itemView.findViewById(R.id.mac);
            nameTextView = (TextView) itemView.findViewById(R.id.name);
        }

        public void setMac(String macString) {
            macTextView.setText(macString);
        }
        public void setName(String macString) {
            nameTextView.setText(macString);


        }
    }
}

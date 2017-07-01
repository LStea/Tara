package com.example.asus.tara;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by asus on 27.06.2017.
 */

public class GetMessageThread implements Runnable {
    InputStream inStream;
    BluetoothSocket btSocket;
    Activity activity;
    Button display;
    private String msg;

    GetMessageThread(InputStream inputStream, BluetoothSocket bluetoothSocket, Activity cont, Button btn){
        inStream = inputStream;
        btSocket = bluetoothSocket;
        activity = cont;
        display = btn;
    }

    @Override
    public void run() {

        byte[] buffer = new byte[2048];
        int bytes = 0;
        StringBuilder sb = new StringBuilder();
        while(true) {
            // Keep listening to the InputStream while connected
            bytes=0;
            try {
                // Read from the InputStream
               bytes = inStream.read(buffer);
                //bytes = inStream.read(buffer, 0, bytes);

            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
            sb.append(new String(buffer, 0, bytes));

            Log.d("THREAD","MSGGETTING:"+sb.toString());
            if(sb.toString().contains(">>!")){
                msg = sb.toString();
                sb.setLength(0);
                    activity.runOnUiThread(new Runnable() {
                        public void run() {


                            //!<<01_SET_FFST_&330&1&0&0&0*4F96>>!
                            int start = msg.indexOf('&');
                            int end = msg.substring(start+1).indexOf('&');
                            Log.d("THREAD","test:"+start+" "+end);
                            String answer = msg.substring(start+1,start+end+1)+ " KG";
                            display.setText(answer);

                            // Update UI elements
                        }
                    });


            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

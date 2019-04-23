package com.example.myfirstapp2.listener;

import android.view.View;

import com.example.myfirstapp2.SocketClient;

public class SocketClientListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        System.out.println("start socket ..。。。。。。。。。。。。。。。。。。。。。。。.");
        SocketClient socketClient = new SocketClient("10.0.2.2",8888);
        socketClient.request();
        //SocketClient socketClient = new SocketClient("10.0.2.2",8888);
        //socketClient.start();
    }
}

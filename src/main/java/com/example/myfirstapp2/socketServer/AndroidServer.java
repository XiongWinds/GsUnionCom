package com.example.myfirstapp2.socketServer;

public class AndroidServer {

    public static void main(String args[])
    {
        SocketServerMain socketServerMain = SocketServerMain.getInstance();
        socketServerMain.server();
    }
}
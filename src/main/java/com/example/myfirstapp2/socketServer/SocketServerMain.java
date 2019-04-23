package com.example.myfirstapp2.socketServer;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerMain implements  Runnable{

    private  final int port = 8888;
    private static SocketServerMain mainHandle = null;
    private ServerSocket handle = null;
    private Socket socket = null;

    SocketServerMain()
    {
        handle =  getSocketServerInstance();
    }
    public static SocketServerMain getInstance()
    {
        if(  mainHandle == null )
        {
            mainHandle = new SocketServerMain();
        }
        return mainHandle;
    }

    private ServerSocket getSocketServerInstance()
    {
        if( null == handle )
        {
            try {
                InetAddress bindAddr = InetAddress.getByName("127.0.0.1");
                //InetAddress bindAddr = InetAddress.getByName("10.0.2.2");
                handle = new ServerSocket(port,10,bindAddr);
                //handle = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return handle;
    }

    private String readString(InputStream input){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));

        StringBuffer sb = new StringBuffer(2048);

        int i = 0;
        char[] buffer = new char[2048];
        try {
            i = bufferedReader.read(buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j=0; j < i; j++) {
            sb.append((char) buffer[j]);
        }
        return sb.toString();
    }

    public void server(){
        while(true) {
            try {
                System.out.println("server accept ...");
                socket = handle.accept();
                while(true) {
                    String input = readString(socket.getInputStream());
                    System.out.println(input);

                    PrintStream output = new PrintStream(socket.getOutputStream());

                    String outputString = "153.23元";
                    outputString.getBytes();
                    output.write(outputString.getBytes(), 0, outputString.getBytes().length);
                }
                //socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        SocketServerMain socketServerMain = SocketServerMain.getInstance();
        socketServerMain.server();
    }
}
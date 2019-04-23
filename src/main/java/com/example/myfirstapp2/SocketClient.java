package com.example.myfirstapp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClient implements Runnable {

    private  String host = "10.0.2.2";
    //private  String host = "127.0.0.1";
    private  int port = 8888;
    private Socket socket = null;
    private BufferedReader input = null;
    private PrintStream output = null;

    public SocketClient(String hostStr, int portInt)
    {
        if( hostStr != null && hostStr.length() != 0 )
            this.host = hostStr;
        if( portInt != 0 )
            this.port = portInt;
        try {
            socket = new Socket(host,port);
            //input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //output = new PrintStream( socket.getOutputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket(){
        return this.socket;
    }

    void setInput(BufferedReader input){
        this.input = input;
    }

    void setOutput(PrintStream output){
        this.output = output;
    }

    public static void main(String args[])
    {
        SocketClient socketClient = new SocketClient("",0);
        Thread thread = new Thread(socketClient);
        thread.start();
    }

    public void send(String outputString)  {
        System.out.println("send ...............");
        System.out.println(outputString.getBytes());
        System.out.println(outputString.getBytes().length);
        output.write(outputString.getBytes(),0,outputString.getBytes().length);
    }

    public synchronized String read(){
        StringBuffer strBuf = new StringBuffer(2048);
        int i;
        char[] buffer = new char[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }

        for (int j = 0; j < i; j++) {
            strBuf.append((char) buffer[j]);
        }
        return strBuf.toString();
    }

    public void request()
    {
        try {
            send("hello world!");
            String out = read();
            System.out.println(out);
            System.out.println("net connection is ok!");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    @Override
    public void run() {
        try {
            send("hello world!");
            String out = read();
            System.out.println(out);
            System.out.println("net connection is ok!");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }
}

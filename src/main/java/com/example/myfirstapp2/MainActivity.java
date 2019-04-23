package com.example.myfirstapp2;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstapp2.listener.SocketClientListener;
import com.example.myfirstapp2.socketServer.SocketServerMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static android.view.Window.FEATURE_ACTION_BAR;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String CHARGE_SUCCESS = "Charge successful!";

    private Button button1 = null ;
    private Button button2 = null ;
    private Button button3 = null ;
    private Button button4 = null ;
    private Button button5 = null ;
    private Button buttonOtherFee = null ;
    private TextView viewBalanceLeftValue = null;

    private BufferedReader input = null;
    private PrintStream output = null;

    private Handler handler = null;
    private static Object sendTag = new Object();
    private String sendStr = "";

    private ConstraintLayout constraintLayout;
    private ConstraintSet applyCons = new ConstraintSet();
    private ScrollView scrollView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        constraintLayout = (ConstraintLayout)findViewById(R.id.constraintLayout);
        applyCons.clone(constraintLayout);


        scrollView = findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //scrollView.scrollTo(v.getScrollX()-100,v.getScrollY()-100);
                //scrollView.scrollBy(100,100);
                scrollView.setY(event.getY() + 10);
                Log.d("event-X",Float.valueOf(event.getX()).toString());
                Log.d("event-Y",Float.valueOf(event.getY()).toString());
                Log.d("view-X",Float.valueOf(v.getScrollX()).toString());
                Log.d("view-Y",Float.valueOf(v.getScrollY()).toString());
                Log.d("1","点击了一下。。。。");

                return false;
            }
        });



        /*if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/

        //requestWindowFeature(Window.FEATURE_ACTION_BAR);

       /* button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        buttonOtherFee = findViewById(R.id.button6);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        buttonOtherFee.setOnClickListener(this);*/

        /*SocketServerMain socketServerMain = SocketServerMain.getInstance();
        Thread serverThread = new Thread(socketServerMain);
        serverThread.start();*/

        button1 = findViewById(R.id.feepolicy);
        viewBalanceLeftValue = findViewById(R.id.balanceleftvalue);

        int NUMBER_OF_CORES =
                Runtime.getRuntime().availableProcessors();

        button1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Context ct = getApplicationContext();
                CharSequence charSeq = "onclick...";
                Toast.makeText(ct,charSeq, Toast.LENGTH_SHORT).show();

                sendStr = "hello world!";
                Print("onClick" + sendStr);
                synchronized ((Object)sendTag) {
                    Print("notify" + sendStr);
                    ((Object)sendTag).notify();
                }
            }
        });

        handler = new Handler(){

            public void handleMessage(Message msg){
                if(msg.what == 1 )
                {
                    char textBalanceLeftValue[] = ((Map<String,String>)msg.obj).get("key").toCharArray();
                    Print("handler..." + textBalanceLeftValue.toString());
                    viewBalanceLeftValue.setText(textBalanceLeftValue,0,textBalanceLeftValue.length);
                }
            }
        };

        Thread thread = new  Thread(){
            public void run(){
                Print("sub thread  run...");
                SocketClient socketClient = new SocketClient("10.0.2.2",8888);
                //socketClient.request();

                try {
                    input = new BufferedReader(new InputStreamReader(socketClient.getSocket().getInputStream()));
                    output = new PrintStream( socketClient.getSocket().getOutputStream() );
                    socketClient.setInput(input);
                    socketClient.setOutput(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Map<String,String> mp = new HashMap<String,String>();

                 synchronized ((Object)sendTag){
                     while (true) {

                         try {
                             ((Object)sendTag).wait();
                         } catch (InterruptedException e) {
                             e.printStackTrace();
                         }
                         Print("sub thread  run  sendStr ==" + sendStr);
                         output.write(sendStr.getBytes(), 0, sendStr.getBytes().length);
                         String result = socketClient.read();
                         Print("sub thread  run  read result==" + result);
                         mp.put("key", result);
                         Message msg = handler.obtainMessage();
                         msg.what = 1;
                         msg.obj = mp;
                         handler.sendMessage(msg);

                     }
                 }
            }
        };
        thread.start();
    }


    private static void Print(String str){
        System.out.println(str);
    }

    private void chargeSuccess(){
        Intent intent = new Intent(this,DisplayMessageActivity.class);
        startActivity(intent);
    }

    private void chargeOtherFee(){
        Intent intent = new Intent(this,ChargeOtherFeeActivity.class);
        startActivity(intent);
    }


    @Override
    public void onClick(View v) {


        /*if( v == button1 || v == button2
        || v == button3 || v == button4
        || v == button5 )
        {
            chargeSuccess();
        }else if( v == buttonOtherFee ){
            chargeOtherFee();
        }*/
    }
}

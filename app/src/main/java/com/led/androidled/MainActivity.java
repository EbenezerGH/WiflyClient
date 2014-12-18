package com.led.androidled;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends Activity {

    private Socket socket;
    private SeekBar bar;
    private TextView txt;

    private PrintWriter out;

    private int value = 0;
    private boolean m1, m2, m3, s1, s2, s3, s4 = false;  //defining motors/stops

    private static final int SERVERPORT = 2000;
    private static final String SERVER_IP = "1.2.3.4";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        txt = (TextView) findViewById(R.id.voltage);
        bar = (SeekBar) findViewById(R.id.seekBar);
        bar.setMax(100);


        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {

                value = seekBar.getProgress();
                txt.setText("Value: " + value);

            }
        });

        new Thread(new ClientThread()).start();


    }

    public void onClick(View view) {
        try {
            out = new PrintWriter(new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())),
                    true
            );

            switch (view.getId()) {
                case R.id.motor1:
                    if (m1) {
                        out.println("1");
                        m1 = false;
                    } else {
                        out.print("2");
                        m1 = true;
                    }
                    break;
                case R.id.motor2:
                    if (m2) {
                        out.println("4");
                        m2 = false;
                    } else {
                        out.print("5");
                        m2 = true;
                    }
                    break;
                case R.id.motor3:
                    if (m3) {
                        out.println("7");
                        m3 = false;
                    } else {
                        out.print("8");
                        m3 = true;
                    }
                    break;
                case R.id.stop1:
                    out.println("3");
                    break;
                case R.id.stop2:
                    out.println("6");
                    break;
                case R.id.stop3:
                    out.println("9");
                    break;
                case R.id.stopall:
                    out.println("10");
                    break;
            }


        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
                socket = new Socket(serverAddr, SERVERPORT);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}
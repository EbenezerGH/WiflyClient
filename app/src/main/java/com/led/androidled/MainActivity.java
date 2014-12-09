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

    int value = 0;

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
                case R.id.up:
                    out.println("2");
                    break;
                case R.id.down:
                    out.println("1");
                    break;
                case R.id.stop:
                    out.println("3");
                    break;
                case R.id.button:
                    out.println("4");
                    break;
            }

            out.flush();

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
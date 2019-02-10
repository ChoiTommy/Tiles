package com.kaito.tiles;
//https://github.com/stedi-akk/PowerOffClick
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process process = Runtime.getRuntime().exec("su");
                    DataOutputStream os = new DataOutputStream(process.getOutputStream());
                    os.writeBytes("input keyevent 26" + "\n");
                    os.writeBytes("exit\n");
                    os.flush();//https://stackoverflow.com/questions/33529933/how-to-run-root-commands-from-android-application
                    if (process.waitFor() != 0)
                        Toast.makeText(MainActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Toast.makeText(MainActivity.this, "Device does not have root access.", Toast.LENGTH_SHORT).show();
                } catch (InterruptedException ex) {
                    Toast.makeText(MainActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        }).start();
        finish();
    }
}
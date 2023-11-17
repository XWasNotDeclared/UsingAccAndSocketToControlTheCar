package com.example.testactivity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor gyro;
    private Sensor acc;
    private SensorEventListener gyroEventListener;
    private SensorEventListener accEventListener;
    SendDataToPC PC = new SendDataToPC();
    TextView textViewIP;
    TextView textViewPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewIP = findViewById(R.id.textView1);
        textViewPort = findViewById(R.id.textView2);
        String IP = getIntent().getStringExtra("IP");
        if (IP!=null){
            textViewIP.setText("IP = " + IP);
            PC.setIP(IP);
        }
        else textViewIP.setText("IP = Empty");

       String Port = getIntent().getStringExtra("Port");
        if (Port!=null){
            textViewPort.setText("Port = " + Port);
            PC.setPort(Port);
        }
        else textViewPort.setText("Port = Empty");
        //////////////////////////////////////
        sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
        gyro=sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        acc=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acc==null){
            Toast.makeText(this,"Can't find accelerometer!!!",Toast.LENGTH_SHORT).show();
            finish();
        }

        if (gyro==null){
            Toast.makeText(this,"Can't find gyroscope!!!",Toast.LENGTH_SHORT).show();
            finish();
        }
        ///////////////////////
        accEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.values[1] > 1f) {
                    getWindow().getDecorView().setBackgroundColor(Color.RED);

                } else if (event.values[1] < -1f) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                } else {
                    getWindow().getDecorView().setBackgroundColor((Color.WHITE));
                }
                PC.sendDataToPC(event.values[1],event.values[2],0.0,0.0);

            }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }
    ///////////////////////////////
    @Override
    public void onResume(){
        super.onResume();
        //sensorManager.registerListener(gyroEventListener,gyro, SensorManager.SENSOR_DELAY_FASTEST);
        sensorManager.registerListener(accEventListener,acc,2 /*SensorManager.SENSOR_DELAY_NORMAL*/);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //sensorManager.unregisterListener(gyroEventListener);
        sensorManager.unregisterListener(accEventListener);
    }



}

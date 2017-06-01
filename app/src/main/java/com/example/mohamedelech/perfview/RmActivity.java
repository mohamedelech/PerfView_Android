package com.example.mohamedelech.perfview;

/**
 * Created by mohamed.elech on 01.06.2017.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;


public class RmActivity  extends Activity implements SensorEventListener {
    TextView textView;
    StringBuilder builder = new StringBuilder();

    MediaPlayer klaxonSound;
    MediaPlayer telephoneSound;
    MediaPlayer dingdongSound;
    MediaPlayer gokuSound;


    float [] history = new float[2];
    String [] direction = {"NONE","NONE"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView = new TextView(this);
        setContentView(textView);

        klaxonSound = MediaPlayer.create(getApplicationContext(), R.raw.klaxon);
        telephoneSound = MediaPlayer.create(getApplicationContext(), R.raw.telephone);
        dingdongSound = MediaPlayer.create(getApplicationContext(), R.raw.dingdong);
        gokuSound = MediaPlayer.create(getApplicationContext(), R.raw.gokukamehameha);

        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float xChange = history[0] - event.values[0];
        float yChange = history[1] - event.values[1];

        history[0] = event.values[0];
        history[1] = event.values[1];

        /**if (xChange > 2){
            direction[0] = "LEFT";
            telephoneSound.start();
        }
        else if (xChange < -2){
            direction[0] = "RIGHT";
            dingdongSound.start();
        }*/

        if (yChange > 2){
            direction[1] = "DOWN";
            telephoneSound.start();
        }
        else if (yChange < -2){
            direction[1] = "UP";
            klaxonSound.start();
        }

        builder.setLength(0);
        builder.append("x: ");
        builder.append(direction[0]);
        builder.append(" y: ");
        builder.append(direction[1]);

        textView.setText(builder.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // nothing to do here
    }
}
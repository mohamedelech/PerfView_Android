package com.example.mohamedelech.perfview;

/**
 * Created by mohamed.elech on 01.06.2017.
 */

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class ReconnaissanceMouvementActivity  extends Activity {

    private SensorManager mSensorManager;
    private TextView mTxtViewX;
    private TextView mTxtViewY;
    private TextView mTxtViewZ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconnaissancemouvement);

        mSensorManager = (SensorManager)getSystemService(this.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        mTxtViewX = (TextView) findViewById(R.id.textX);
        mTxtViewY = (TextView) findViewById(R.id.textY);
        mTxtViewZ = (TextView) findViewById(R.id.textZ);

    }

    public void Position(float iX, float iY, float iZ)
    {
        mTxtViewZ.setText(" "+iZ);
        mTxtViewX.setText(" "+iX);
        mTxtViewY.setText(" "+iY);
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {

        public void onSensorChanged(SensorEvent se)
        {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            Position(x, y , z);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    protected void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop()
    {
        mSensorManager.unregisterListener(mSensorListener);
        super.onStop();
    }

}

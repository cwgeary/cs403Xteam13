package com.starboardland.pedometer;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CounterActivity extends Activity implements SensorEventListener {

    private static final int NUM_SEGMENTS = 8;

    private SensorManager sensorManager;
    private boolean activityRunning;

    private int currentSegment = 0;
    private float[] segmentSteps = new float[NUM_SEGMENTS];
    private TextView[] stepViews = new TextView[NUM_SEGMENTS];

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        stepViews[0] = (TextView) findViewById(R.id.segmentSteps1);
        stepViews[1] = (TextView) findViewById(R.id.segmentSteps2);
        stepViews[2] = (TextView) findViewById(R.id.segmentSteps3);
        stepViews[3] = (TextView) findViewById(R.id.segmentSteps4);
        stepViews[4] = (TextView) findViewById(R.id.segmentSteps5);
        stepViews[5] = (TextView) findViewById(R.id.segmentSteps6);
        stepViews[6] = (TextView) findViewById(R.id.segmentSteps7);
        stepViews[7] = (TextView) findViewById(R.id.segmentSteps8);

        for( int i = 0; i < NUM_SEGMENTS; i++ ) {
            stepViews[i].setText(String.format("%.1f", segmentSteps[i]));
        }

        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        mMap.setMyLocationEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityRunning = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        activityRunning = false;
        // if you unregister the last listener, the hardware will stop detecting step events
//        sensorManager.unregisterListener(this); 
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (activityRunning) {
            stepViews[currentSegment].setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

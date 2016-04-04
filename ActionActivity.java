package com.example.myposition;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class ActionActivity extends AppCompatActivity {
    private static final String TAG = "Compass";
    private ImageView image;
    private float[] mGravity = new float[3];
    private float[] mGeomagnetic = new float[3];
    private float azimuth = 0f;
    private float currectAzimuth = 0;
    private SensorManager sensorManager;
    private TextView textView;
    private Sensor sensorGravity;
    private Sensor sensorMagnetic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        textView = (TextView) findViewById(R.id.textView);
        image = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(sensorListener, sensorGravity, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(sensorListener, sensorMagnetic, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);//save battery
    }

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent se) {
            final float alpha = 0.97f;

            synchronized (this) {
                if (se.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                    mGravity[0] = alpha * mGravity[0] + (1 - alpha)
                            * se.values[0];
                    mGravity[1] = alpha * mGravity[1] + (1 - alpha)
                            * se.values[1];
                    mGravity[2] = alpha * mGravity[2] + (1 - alpha)
                            * se.values[2];

                }

                if (se.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {


                    mGeomagnetic[0] = alpha * mGeomagnetic[0] + (1 - alpha)
                            * se.values[0];
                    mGeomagnetic[1] = alpha * mGeomagnetic[1] + (1 - alpha)
                            * se.values[1];
                    mGeomagnetic[2] = alpha * mGeomagnetic[2] + (1 - alpha)
                            * se.values[2];

                }

                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity,
                        mGeomagnetic);
                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    azimuth = (float) Math.toDegrees(orientation[0]); // orientation
                    azimuth = (azimuth + 360) % 360;
                    // get the angle around the z-axis rotated
                    textView.setText("Heading: "+Float.toString(currectAzimuth)+" degrees");

                    Log.i(TAG, "will set rotation from " + currectAzimuth + " to "+ azimuth);
                    Animation an = new RotateAnimation(-currectAzimuth, -azimuth,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                            0.5f);
                    currectAzimuth = azimuth;
                    an.setDuration(500);
                    an.setRepeatCount(0);
                    an.setFillAfter(true);
                    image.startAnimation(an);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
}
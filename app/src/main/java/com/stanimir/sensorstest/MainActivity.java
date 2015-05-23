package com.stanimir.sensorstest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SensorManager sensorManager;
    private Sensor lightSensor, gyroSensor;
    private SensorEventListener lightSensorListener;
    private SensorEventListener gyroSensorListener;

    private TextView tvLightSensor, tvLightSensorData;
    private TextView tvGyroSensor, tvGyroSensorData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");

        tvLightSensor = (TextView) findViewById(R.id.tv_light_sensor);
        tvLightSensorData = (TextView) findViewById(R.id.tv_light_sensor_data);

        tvGyroSensor = (TextView) findViewById(R.id.tv_gyro_sensor);
        tvGyroSensorData = (TextView) findViewById(R.id.tv_gyro_sensor_data);

        getSensors();

        getSensorsData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "registered lightSensorListener");
        sensorManager.registerListener(gyroSensorListener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d(TAG, "registered gyroSensorListener");
    }

    private void getSensors() {
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);

        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        gyroSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if(lightSensor != null) {
            //tvLightSensor.setText(lightSensor.getName()+" ("+lightSensor.getVendor()+")");
            tvLightSensor.setText("Light sensor data: ");
        } else {
            tvLightSensor.setText("No Light Sensor ");
        }

        if(gyroSensor != null) {
            //tvGyroSensor.setText(gyroSensor.getName() + " (" + gyroSensor.getVendor() + ")");
            tvGyroSensor.setText("Gyro sensor data: ");
        } else {
            tvGyroSensor.setText("No Gyroscope Sensor ");
        }

    }

    private void getSensorsData() {
        getLightSensorData();

        getGyroSensorData();
    }

    private void getLightSensorData() {
        lightSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.d(TAG, "Light Sensor Data: " + event.values[0]);
                tvLightSensorData.setText(event.values[0]+" "+event.values[1]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if(lightSensor != null) {
            if(!sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)){
                Log.d(TAG, "LightSensorListener not registered.");
            }
        } else {
            tvLightSensorData.setText("--");
        }
    }

    private void getGyroSensorData() {
        gyroSensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Log.d(TAG, "Gyroscope Sensor Data: " + event.values[0]);
                tvGyroSensorData.setText("x: "+event.values[0] + " \ny: " + event.values[1] + " \nz: " + event.values[2]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        if(gyroSensor != null) {
            if(!sensorManager.registerListener(gyroSensorListener, gyroSensor, SensorManager.SENSOR_DELAY_NORMAL)) {
                Log.d(TAG, "GyroSensorListener not registered.");
            }
        } else {
            tvGyroSensorData.setText("--");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        sensorManager.unregisterListener(lightSensorListener);
        Log.d(TAG, "unregistered lightSensorListener");
        sensorManager.unregisterListener(gyroSensorListener);
        Log.d(TAG, "unregistered gyroSensorListener");
    }
}

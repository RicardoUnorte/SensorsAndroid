package com.example.ricardo.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SensorsActivity extends ActionBarActivity implements SensorEventListener{


    public SensorManager Sen;
    public LocationManager LManager;
    public List<Sensor> SensorL;
    public Geocoder geocoder;
    public ArrayList<CharSequence> NameSensor = new ArrayList<>();
    TextView name;
    TextView prov;
    TextView Val;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        name = (TextView) findViewById(R.id.Name);
        prov = (TextView) findViewById(R.id.Prov);
        Val = (TextView) findViewById(R.id.Value);
        Sen = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //GPS
        LManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location lastlocation = LManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        geocoder = new Geocoder(this);

        //GPS
        SensorL = Sen.getSensorList(Sensor.TYPE_ALL);
        for(int i=0; i<= SensorL.size()-1; i++){
            NameSensor.add(i,SensorL.get(i).getName());
        }

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this,android.R.layout.simple_spinner_item,NameSensor);


// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        final SensorsActivity SA = this;
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Sensor s = SensorL.get(position);
                name.setText(s.getName());


                prov.setText(s.getVendor());
                Sen.unregisterListener(SA);
                Sen.registerListener(SA, s, SensorManager.SENSOR_DELAY_NORMAL);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sensors, menu);
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
    public void onSensorChanged(SensorEvent event) {
        Val.setText(Arrays.toString(event.values));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

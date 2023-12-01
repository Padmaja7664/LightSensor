package com.example.lightsensor

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import java.io.IOException


//extend the interface sensoreventListener
class MainActivity : AppCompatActivity(), SensorEventListener{

    //refer the document
    var sensor: Sensor? =null
    var sensorManager: SensorManager? =null
    lateinit var image: ImageView
    lateinit var background: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        image= findViewById(R.id.mainImg)
        background= findViewById(R.id.backgroundMain)
        image.visibility =View.INVISIBLE

        sensorManager= getSystemService(Context.SENSOR_SERVICE)as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)

    }

    override fun onResume() {
        //register the listener for the sensor
        super.onResume()
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)

    }


    override fun onSensorChanged(event: SensorEvent?) {
        //if the sensor sense the changes like if light intensity is changed then what  actions should be performed by the sensor is written here
        try{

            if(event!= null){
                //to see what values are being taken by the first event when the intensity of light is changing
                Log.d(TAG, "OnSensorChanged: "+event.values[0])
            }
            if(event!!.values[0]<30){
                //if light is dim make the image invisible
                image.visibility = View.INVISIBLE
                background.setBackgroundColor(resources.getColor(R.color.black))

            }
            else{
                //show image if light intensity is high
                image.visibility= View.VISIBLE
                background.setBackgroundColor(resources.getColor(R.color.yellow))
            }

        }catch(e: IOException){
            Log.d(TAG,"OnSensorChanged + ${e.message}")
        }


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onPause() {
        //when activity is paused this function make sure to unregister the sensor
        super.onPause()
        sensorManager?.unregisterListener(this)
    }
}
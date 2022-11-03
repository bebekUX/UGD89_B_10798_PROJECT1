package com.UGD.ugd89_b_10798_project1

import android.hardware.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception


class CameraActivity : AppCompatActivity(), SensorEventListener {
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? = null
    private var mSensorManager: SensorManager? = null
    private var mSensor: Sensor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        mSensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mSensor = mSensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)

//        ImageButton imageClose = (ImageButton) findViewById(R.id.imgClose);
////        imageClose.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                System.exit(0);
////            }
////        });
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        val mySensor = sensorEvent.sensor
            if (mySensor.type == Sensor.TYPE_PROXIMITY) {
            if (sensorEvent.values[0] != 0f) {
                Toast.makeText(this@CameraActivity, "Kamera Belakang", Toast.LENGTH_SHORT)
                    .show()
                try {
                    mCamera = Camera.open()
                } catch (e: Exception) {
                    Log.d("Error", "Failed to get camera" + e.message)
                }
                if (mCamera != null) {
                    mCameraView = CameraView(this, mCamera!!)
                    val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
                    camera_view.addView(mCameraView)
                }
                val imageClose = findViewById<View>(R.id.imgClose) as ImageButton
                imageClose.setOnClickListener { System.exit(0) }
            } else {
                Toast.makeText(this@CameraActivity, "Kamera Depan", Toast.LENGTH_SHORT)
                    .show()
                try {
                    mCamera = Camera.open(1)
                } catch (e: Exception) {
                    Log.d("Error", "Failed to get camera" + e.message)
                }
                if (mCamera != null) {
                    mCameraView = CameraView(this, mCamera!!)
//                    mCameraView!!.mCamera.setDisplayOrientation(90)
                    val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
                    camera_view.addView(mCameraView)
                }
                val imageClose = findViewById<View>(R.id.imgClose) as ImageButton
                imageClose.setOnClickListener { System.exit(0) }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}
    override fun onResume() {
        super.onResume()
        mSensorManager!!.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        mSensorManager!!.unregisterListener(this)
    }
}
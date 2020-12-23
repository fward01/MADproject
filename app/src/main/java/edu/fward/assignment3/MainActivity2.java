package edu.fward.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity  {

    private final int BLUE = 1;
    private final int RED = 2;
    private final int YELLOW = 3;
    private final int GREEN = 4;
    private final double NORTH_MOVE_FORWARD = 2.5;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = -2.5;      // lower mag limit
    private final double WEST_MOVE_FORWARD = 2.5;     // upper mag limit
    private final double WEST_MOVE_BACKWARD = -2.5;      // lower mag limit
    boolean highLimit = false;      // detect high limit
    Button bRed, bBlue, bYellow, bGreen, fb;
    int[] gameSequence;
    int[] playerSequence = new int[120];
    int arrayIndex = 0;
    public int score=0;
    boolean redPress=false;
    private SensorManager sensorManager;

    CountDownTimer ct = new CountDownTimer(15000,  1500) {

        public void onTick(long millisUntilFinished) {
                 {
                    bRed.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {

                            playerSequence[arrayIndex++] = RED;
                        }
                    });

                }
            bBlue.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = BLUE;

                }
            });
            bYellow.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = YELLOW;

                }
            });
            bGreen.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    playerSequence[arrayIndex++] = GREEN;

                }
            });
            if (playerSequence.length==gameSequence.length){
                //onFinish();
            }
        }
        public void onFinish() {

            //mTextField.setText("done!");
            // we now have the game sequence

            //for (int i = 0; i< arrayIndex; i++)
             //   Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            // stack overglow https://stackoverflow.com/questions/3848148/sending-arrays-with-intent-putextra
            score+=gameSequence.length;
            if (Arrays.equals(playerSequence, gameSequence)){
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("increase", 2);
                intent.putExtra("Score", score);
                startActivity(intent);
            }
            else {
                String scores = Integer.toString(score);
                Intent i = new Intent(getApplicationContext(), MainActivity3.class);
                i.putExtra("message", scores);
                startActivity(i);
            }
            // start the next activity
            //
            //int[] arrayB = extras.getIntArray("numbers");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bRed = findViewById(R.id.redSubmit);
        bBlue = findViewById(R.id.blueSubmit);
        bYellow = findViewById(R.id.yellowSubmit);
        bGreen = findViewById(R.id.greenSubmit);
        Bundle myBundle = getIntent().getExtras();
        gameSequence= (int[]) myBundle.get("gameSequence");
        Log.d("game sequence", String.valueOf(gameSequence));
        score= (int) myBundle.get("Score");
        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(accelSensorListener, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);

        ct.start();
    }

    public void doPlay(View view) {
       // ct.start();
    }

    private void flashButton(Button button) {
        fb = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                fb.setPressed(true);
                fb.invalidate();
                fb.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        fb.setPressed(false);
                        fb.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }
    private SensorEventListener accelSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            // Can we get a north movement
            // you need to do your own mag calculating

            if ((x > NORTH_MOVE_FORWARD) && (highLimit == false)) {
                highLimit = true;
                bGreen.performClick();
                bGreen.setPressed(true);
                bGreen.invalidate();
                bGreen.postDelayed(new Runnable() {  //delay button
                    public void run() {
                        bGreen.setPressed(false);
                        bGreen.invalidate();
                        //any other associated action
                    }
                }, 800);
                bGreen.invalidate();
            }
            if ((x < NORTH_MOVE_BACKWARD) && (highLimit == true)) {
                // we have a tilt to the north
                highLimit = false;
                bBlue.performClick();
                bBlue.setPressed(true);
                bBlue.invalidate();
                bBlue.postDelayed(new Runnable() {  //delay button
                    public void run() {
                        bBlue.setPressed(false);
                        bBlue.invalidate();
                        //any other associated action
                    }
                }, 800);
                bBlue.invalidate();
            }
            if ((y > WEST_MOVE_FORWARD) && (highLimit == false)) {
                highLimit = true;
                bRed.performClick();
                bRed.setPressed(true);
                bRed.invalidate();
                bRed.postDelayed(new Runnable() {  //delay button
                    public void run() {
                        bRed.setPressed(false);
                        bRed.invalidate();
                        //any other associated action
                    }
                }, 800);
                bRed.invalidate();
            }
            if ((y < WEST_MOVE_BACKWARD) && (highLimit == true)) {
                // we have a tilt to the north
                highLimit = false;
                bYellow.performClick();
                bYellow.setPressed(true);
                bYellow.invalidate();
                bYellow.postDelayed(new Runnable() {  //delay button
                    public void run() {
                        bYellow.setPressed(false);
                        bYellow.invalidate();
                        //any other associated action
                    }
                }, 800);
                bYellow.invalidate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
package edu.fward.assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity3 extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Button playAgain = findViewById(R.id.playAgainButton);
        Button highScore = findViewById(R.id.highScoreButton);
        playAgain.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {



                Intent intent = new Intent(MainActivity3.this, MainActivity.class);

                startActivity(intent);
            }
        });
        highScore.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity4.class);

                startActivity(intent);
            }
        });
        TextView gamescore= findViewById(R.id.GameScore);
        Intent intent= getIntent();
        String score = intent.getStringExtra("message");
        String scoreMessage= "Your Score is : " + score;

        gamescore.setText(scoreMessage);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String datee=date.toString();
        int scoreint = Integer.parseInt(score);
        HiScore NewScore = new HiScore(10,datee, "Playername", scoreint );
        DatabaseHandler dbh=new DatabaseHandler(this);
        Log.d( "DatabseAdd: ", NewScore.toString());
        dbh.addHiScore(NewScore);
    }
}

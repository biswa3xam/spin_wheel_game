package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    final int[] sectors = {100, 20, 15, 5, 50, 20, 100, 50, 20, 50};
    final int[] sectorsDegree = new int[sectors.length];
    int randomSectorIndex = 0;
    ImageView wheel;
    ImageView belt;
    TextView winningText;
    boolean spinning = false;
    Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wheel = findViewById(R.id.wheel);
        belt = findViewById(R.id.belt);
        winningText = findViewById(R.id.winningText);

        generateSectorDegrees();

        belt.setOnClickListener(v -> {
            if (!spinning) {
                spin();
                spinning = true;
            }
        });
    }

    private void spin() {
        randomSectorIndex = random.nextInt(sectors.length);
        int randomDegree = generateRandomDegreeToSpin();
        RotateAnimation rotateAnimation = new RotateAnimation(0, randomDegree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int earnedCoin = sectors[sectors.length - (randomSectorIndex + 1)];
                winningText.setText("you won " + earnedCoin + " points");
                spinning = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        wheel.startAnimation(rotateAnimation);
    }

    private int generateRandomDegreeToSpin() {
        return (360 * sectors.length) + sectorsDegree[randomSectorIndex];
    }

    private void generateSectorDegrees() {
        int sectorDegree = 360 / sectors.length;
        for (int i = 0; i < sectors.length; i++) {
            sectorsDegree[i] = (i + 1) * sectorDegree;

        }
    }
}
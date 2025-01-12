package com.example.kosci;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    List<ImageView> images = new ArrayList<ImageView>();
    int points = 0;
    List<Integer> pointsHistory = new ArrayList<Integer>();
    List<Integer> resultPoints = new ArrayList<Integer>();
    Integer totalPoints = 0;
    Integer rounds = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        assert email != null;
        String nickName = email.split("@")[0];
        TextView nickNameTextView = findViewById(R.id.nick);
        nickNameTextView.setText(nickName + " w grze");

        ImageView image1 = findViewById(R.id.diceImage1);
        ImageView image2 = findViewById(R.id.diceImage2);
        ImageView image3 = findViewById(R.id.diceImage3);
        ImageView image4 = findViewById(R.id.diceImage4);
        ImageView image5 = findViewById(R.id.diceImage5);

        TextView roundsLeft = findViewById(R.id.rollsLeft);

        images.add(image1);
        images.add(image2);
        images.add(image3);
        images.add(image4);
        images.add(image5);

        Button randomButton = findViewById(R.id.randomButton);

        randomButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                Random random = new Random();
                pointsHistory.clear();
                resultPoints.clear();
                rounds++;
                roundsLeft.setText("Pozostało rzutów: " + (5 - rounds));

                for (ImageView imageView : images) {
                    int diceIndex = random.nextInt(6);
                    int diceValue = diceIndex + 1;

                    pointsHistory.add(diceValue);

                    switch (diceIndex) {
                        case 0:
                            imageView.setImageResource(R.drawable.one);
                            break;
                        case 1:
                            imageView.setImageResource(R.drawable.two);
                            break;
                        case 2:
                            imageView.setImageResource(R.drawable.three);
                            break;
                        case 3:
                            imageView.setImageResource(R.drawable.four);
                            break;
                        case 4:
                            imageView.setImageResource(R.drawable.five);
                            break;
                        case 5:
                            imageView.setImageResource(R.drawable.six);
                            break;
                    }
                }

                // Points counting
                Integer resultPoints = 0;
                for (Integer item : pointsHistory) {
                    int count = Collections.frequency(pointsHistory, item);

                    if (count >= 2) {
                        resultPoints += item;
                    }
                }

                totalPoints += resultPoints;

                TextView pointsTextView = findViewById(R.id.points);
                pointsTextView.setText("Liczba punktów: " + resultPoints.toString());
                TextView totalPointsTextView = findViewById(R.id.totalPoints);
                totalPointsTextView.setText("Zgromadzona liczba punktów: " + totalPoints.toString());


                if (rounds == 5) {
                    randomButton.setEnabled(false);
                    roundsLeft.setText("Brak rzutów");
                    Toast.makeText(GameActivity.this, "Nie masz już więcej rzutów! Zapisz grę!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime date = LocalDateTime.now();
                Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);
                resultIntent.putExtra("nickName", nickName);
                resultIntent.putExtra("date", date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                resultIntent.putExtra("points", totalPoints.toString());
                startActivity(resultIntent);
            }
        });
    }
}

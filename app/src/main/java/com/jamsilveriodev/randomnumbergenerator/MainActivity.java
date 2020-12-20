package com.jamsilveriodev.randomnumbergenerator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 112;
    //These are the global variables
    EditText editMinimum, editMaximum;
    Button buttonGenerateResult;
    TextView randomNumberResult;
    TextView clickCount;
    TextView numbers;
    Button buttonClear;

    String minimum;
    String maximum;
    int myRandomNumber = 0;
    int numberOfClicks = 0;
    int numberOfClicks2 = 0;
    String numbersString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(" Classic RNG");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        shortcutIcon();

        editMinimum = (EditText) findViewById(R.id.editMinimum);
        editMaximum = (EditText) findViewById(R.id.editMaximum);
        buttonGenerateResult = (Button) findViewById(R.id.buttonGenerateResult);
        randomNumberResult = (TextView) findViewById(R.id.randomNumberResult);
        clickCount = (TextView) findViewById(R.id.clickCount);
        numbers = (TextView) findViewById(R.id.numbers);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        editMinimum.requestFocus();
        /*
            Generate Result Button
         */

        buttonGenerateResult.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            public void onClick(View v) {
                minimum = editMinimum.getText().toString();
                maximum = editMaximum.getText().toString();
                if (minimum.equals("") || maximum.equals("")) {
                    // If Minimum or Maximum is not entered
                    Toast.makeText(getApplicationContext(), "Both Minimum and Maximum values are required", Toast.LENGTH_LONG).show();
                    Log.i("Input: ", "Both Minimum and Maximum values are required");
                } else {
                    try {
                        numbers.setText("");
                        generateRandomNumber();
                        randomNumberResult.setText(String.valueOf(myRandomNumber));
                        numberOfClicks += 1;
                        numberOfClicks2 += 1;
                        clickCount.setText("Numbers generated: " + numberOfClicks2);

                        if (numberOfClicks == 1) {
                            numbersString = Integer.toString(myRandomNumber);
                        } else {
                            numbersString = numbersString + "   " + Integer.toString(myRandomNumber);
                        }
                        numbers.setText(numbersString);

                        if (numberOfClicks == 6) {
                            numberOfClicks = 0;
                            numbersString = "";
                        }
                        Log.i("Random Number Result: ", String.valueOf(myRandomNumber));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Unable to generate a random number!", Toast.LENGTH_LONG).show();
                        Log.i("Random Number Result: ", String.valueOf(myRandomNumber));
                    }

                }

            }
        });
        /*
            Clear Button
         */


        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMinimum.getText().clear();
                editMaximum.getText().clear();
                randomNumberResult.setText("");
                clickCount.setText("");
                numberOfClicks = 0;
                numberOfClicks2 = 0;
                numbers.setText("");
                numbersString = "";
                editMinimum.requestFocus();
                Toast.makeText(getApplicationContext(), "Cleared!", Toast.LENGTH_LONG).show();
                Log.i("Clear: ", "Cleared!");
            }
        });

    }


    public void generateRandomNumber() {
        /*
            Logic code for generateRandomNumber() method
         */
        int max = Integer.parseInt(maximum);
        int min = Integer.parseInt(minimum);

        Random random = new Random();
        myRandomNumber = random.nextInt(max - min + 1) + min;
    }

    private void shortcutIcon(){

        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Classic Random NumGen");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }

}
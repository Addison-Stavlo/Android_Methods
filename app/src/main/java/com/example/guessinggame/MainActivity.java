package com.example.guessinggame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static final int MIN_GUESS          = 0;
    static final int MAX_GUESS_PLUS_ONE = 101;
    static final int CORRECT            = 1;
    static final int UNDER              = 0;
    static final int OVER               = 2;
    static final int RESET              = -1;
    static final int NO_VAL             = 5;
    static final int OUT_OF_RANGE       = 6;

    EditText  userGuess;
    Button    btnSubmit;
    TextView  resultText, guessPrev;

    int correct_value;
    Random randVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userGuess  = findViewById(R.id.user_guess);
        btnSubmit  = findViewById(R.id.btn_submit);
        resultText = findViewById(R.id.result_text);
        guessPrev  = findViewById(R.id.guess_prev);
        randVal    = new Random();

        btnSubmit.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View view) {

               // btn state: submit - game in play
               if(btnSubmit.getText() == getString(R.string.btn_text_submit)){
                   String inputValue = userGuess.getText().toString();

                   if(!inputValue.equals("")){
                       int guess = Integer.parseInt(inputValue);
                       userGuess.setText("");
                       guessPrev.setText(Integer.toString(guess));

                       if(guess >= MIN_GUESS && guess < MAX_GUESS_PLUS_ONE){
                           render(checkGuess(guess));
                       }
                       else{
                           render(OUT_OF_RANGE);
                       }
                   }
                   else{
                       // no value entered
                       render(NO_VAL);
                   }
               }
               // btn state: Reset
               else {
                   // guessed correct
                   correct_value = randVal.nextInt(MAX_GUESS_PLUS_ONE);
                   render(RESET);
               }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        correct_value = randVal.nextInt(MAX_GUESS_PLUS_ONE);
    }

    private int checkGuess(int guess) {
        if (guess == correct_value) {
            return CORRECT;
        }
        else if (guess < correct_value ){
            return UNDER;
        }
        else {
            return OVER;
        }
    }

    private void render(int ui_state) {
        switch (ui_state) {

            case CORRECT:
                resultText.setText(R.string.congrats);
                btnSubmit.setText(R.string.btn_text_reset);
                break;
            case UNDER:
                resultText.setText(R.string.low_guess);
                break;
            case OVER:
                resultText.setText(R.string.high_guess);
                break;
            case RESET:
                btnSubmit.setText(R.string.btn_text_submit);
                resultText.setText(R.string.prompt);
                guessPrev.setText("n/a");
                break;
            case NO_VAL:
                resultText.setText(R.string.no_guess);
                break;
            case OUT_OF_RANGE:
                resultText.setText(String.format("Guess out of Range: %d to %d", MIN_GUESS, MAX_GUESS_PLUS_ONE-1));
                break;
            default:
                System.out.println("ERR- switch case unknown - updateUI in MainActivity.java");
                break;
        }
    }
}

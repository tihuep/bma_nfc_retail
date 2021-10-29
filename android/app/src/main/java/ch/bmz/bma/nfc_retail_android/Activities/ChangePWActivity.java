package ch.bmz.bma.nfc_retail_android.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.bmz.bma.nfc_retail_android.R;
import ch.bmz.bma.nfc_retail_android.Service.UserService;

public class ChangePWActivity extends AppCompatActivity {

    Button changePWBack;
    TextView changePWTitle;
    TextView changePWCurrentLabel;
    EditText changePWCurrentInput;
    TextView changePWNewLabel;
    EditText changePWNewInput;
    TextView changePWNewConfLabel;
    EditText changePWNewConfInput;
    LinearLayout changePWError;
    TextView changePWErrorLabel;
    Button changePWButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        changePWBack = findViewById(R.id.changePWBack);
        changePWTitle = findViewById(R.id.changePWTitle);
        changePWCurrentLabel = findViewById(R.id.changePWCurrentLabel);
        changePWCurrentInput = findViewById(R.id.changePWCurrentInput);
        changePWNewLabel = findViewById(R.id.changePWNewLabel);
        changePWNewInput = findViewById(R.id.changePWNewInput);
        changePWNewConfLabel = findViewById(R.id.changePWNewConfLabel);
        changePWNewConfInput = findViewById(R.id.changePWNewConfInput);
        changePWError = findViewById(R.id.changePWError);
        changePWErrorLabel = findViewById(R.id.changePWErrorLabel);
        changePWButton = findViewById(R.id.changePWButton);

        changePWError.setVisibility(View.GONE);

        defineButtonHandlers();
    }

    private void defineButtonHandlers() {
        ChangePWActivity that = this;
        changePWBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        changePWButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    UserService.changePW(that, changePWNewConfInput.getText().toString());
                    /*hideError();
                    finish();*/
                }else {
                    //TODO: implement proper error messages
                    displayError("Bitte die Felder korrekt ausfüllen");
                }
            }
        });
    }

    private boolean validateForm() {
        return validateCurrentPW() && validateNewPW();
    }

    private boolean validateCurrentPW() {
        //TODO: implement validation from server
        return changePWCurrentInput.getText().toString().length() > 5 &&
                changePWCurrentInput.getText().toString().length() < 255;
    }

    private boolean validateNewPW() {
        return changePWNewInput.getText().toString().equals(changePWNewConfInput.getText().toString()) &&
                changePWNewInput.getText().toString().length() > 5 &&
                changePWNewInput.getText().toString().length() < 255;
    }

    public void displayError(String message) {
        changePWError.setVisibility(View.VISIBLE);
        changePWErrorLabel.setText(message);
    }

    public void hideError() {
        changePWError.setVisibility(View.GONE);
        changePWErrorLabel.setText("");
    }
}